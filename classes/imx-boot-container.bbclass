#
# This class provides a support to build the boot container for
# i.MX8M derivatives
#
# imx8m machines require a separate build target to be executed
# due to the fact that final boot image is constructed using flash.bin
# taget. It produces a boot binary image, which is constructed from
# various binary components (u-boot with separate dtb, atf, DDR
# firmware and optional op-tee) into a single image using FIT format.
# This flash.bin file is then parsed and loaded either via
# SPL directly (imx8mm), or using bootrom code (imx8mn and imx8mp).
#
# In order to use flash.bin binary boot image, it is required that
# the U-Boot build is to be invoked for an additional build target.
# This class extendes the U-Boot build targets with the "flash.bin",
# which effectively serves as a boot container. It replaces the boot
# container `imx-boot` provided by NXP.
#
# Class inheritance is performed in u-boot-fslc recipe, and is controlled
# by variable UBOOT_PROVIDES_BOOT_CONTAINER, which is defined in the
# base machine include file (imx-base.inc), and is set to "1" when the
# 'imx-boot-container' is present in MACHINEOVERRIDES.

# Define ATF binary file to be deployed to the U-Boot build folder
ATF_MACHINE_NAME = "bl31-${ATF_PLATFORM}.bin"
ATF_MACHINE_NAME:append = "${@bb.utils.contains('MACHINE_FEATURES', 'optee', '-optee', '', d)}"

# This package aggregates output deployed by other packages, so set the
# appropriate dependencies for populate binaries task
do_resolve_and_populate_binaries[depends] += " \
    ${@' '.join('%s:do_deploy' % r for r in '${IMX_EXTRA_FIRMWARE}'.split() )} \
    imx-atf:do_deploy \
    ${@bb.utils.contains('MACHINE_FEATURES', 'optee', 'optee-os:do_deploy', '', d)} \
"

# Append make flags to include ATF load address
EXTRA_OEMAKE += "ATF_LOAD_ADDR=${ATF_LOAD_ADDR}"

# Define an additional task that collects binary output from dependent packages
# and deploys them into the U-Boot build folder
do_resolve_and_populate_binaries() {
    if [ ! -n "${ATF_LOAD_ADDR}" ]; then
        bberror "ATF_LOAD_ADDR is undefined, result binary would be unusable!"
    fi

    if [ -n "${UBOOT_CONFIG}" ]; then
        for config in ${UBOOT_MACHINE}; do
            i=$(expr $i + 1);
            for type in ${UBOOT_CONFIG}; do
                j=$(expr $j + 1);
                if [ $j -eq $i ]; then
                    for ddr_firmware in ${DDR_FIRMWARE_NAME}; do
                        bbnote "Copy ddr_firmware: ${ddr_firmware} from ${DEPLOY_DIR_IMAGE} -> ${B}/${config}/${ddr_firmware_name}"
                        cp ${DEPLOY_DIR_IMAGE}/${ddr_firmware} ${B}/${config}/
                    done
                    if [ -n "${ATF_MACHINE_NAME}" ]; then
                        cp ${DEPLOY_DIR_IMAGE}/${BOOT_TOOLS}/${ATF_MACHINE_NAME} ${B}/${config}/bl31.bin
                    else
                        bberror "ATF binary is undefined, result binary would be unusable!"
                    fi
                fi
            done
            unset  j
        done
        unset  i
    fi
}
addtask do_resolve_and_populate_binaries before do_compile after do_configure

# Append the u-boot do_deploy task to deploy also the result flash.bin
# boot container as a replacement for the one provided by NXP BSP.
#
# Deploy task renames the target file from flash.bin to imx-boot to match
# the name WKS file requires.
#
# This effectively would allow the usage of the same WKS file for those
# derivatives that are using the boot container from U-Boot and those
# that are not yet have support for it enabled.
do_deploy:append() {
    # Deploy the resulted flash.bin for WIC to pick it up
    if [ -n "${UBOOT_CONFIG}" ]; then
        for config in ${UBOOT_MACHINE}; do
            i=$(expr $i + 1);
            for type in ${UBOOT_CONFIG}; do
                j=$(expr $j + 1);
                if [ $j -eq $i ]
                then
                    install -m 0644 ${B}/${config}/u-boot.itb  ${DEPLOYDIR}/u-boot.itb-${MACHINE}-${UBOOT_CONFIG}
                    install -m 0644 ${B}/${config}/flash.bin  ${DEPLOYDIR}/flash.bin-${MACHINE}-${UBOOT_CONFIG}
                    ln -sf u-boot.itb-${MACHINE}-${UBOOT_CONFIG} u-boot.itb
                    ln -sf flash.bin-${MACHINE}-${UBOOT_CONFIG} flash.bin
                fi
            done
            unset  j
        done
        unset  i
    fi
}
