require recipes-bsp/u-boot/u-boot.inc
require u-boot-fslc-common_${PV}.inc

DESCRIPTION = "U-Boot based on mainline U-Boot used by FSL Community BSP in \
order to provide support for some backported features and fixes, or because it \
was submitted for revision and it takes some time to become part of a stable \
version, or because it is not applicable for upstreaming."

DEPENDS_append = " bc-native dtc-native lzop-native"

# Location known to imx-boot component, where U-Boot artifacts
# should be additionally deployed.
# See below note above do_deploy_append_mx8m for the purpose of
# this delopyment location
BOOT_TOOLS = "imx-boot-tools"

PROVIDES += "u-boot"

B = "${WORKDIR}/build"

# FIXME: Allow linking of 'tools' binaries with native libraries
#        used for generating the boot logo and other tools used
#        during the build process.
EXTRA_OEMAKE += 'HOSTCC="${BUILD_CC} ${BUILD_CPPFLAGS}" \
                 HOSTLDFLAGS="${BUILD_LDFLAGS}" \
                 HOSTSTRIP=true'

#
# imx8m machines require additional deployment tasks to be
# carried out due to the fact that final boot image is constructed
# using imx-boot recipe. It produces a boot binary image, which is
# constructed from various binary components (u-boot with separate
# dtb, atf, DDR firmware and optional op-tee) into a single image
# using FIT format. This image is then parsed and loaded either via
# SPL directly (imx8mm), or using bootrom code (imx8mn and imx8mp).
#
# In order for imx-boot to construct the final binary boot image,
# it is required that the U-Boot dtb files are to be deployed into
# a location known by imx-boot so they could be picked up and
# inserted into the boot container.
do_deploy_append_mx8m() {
    # Deploy the mkimage, u-boot-nodtb.bin and fsl-imx8m*-XX.dtb for mkimage to generate boot binary
    if [ -n "${UBOOT_CONFIG}" ]; then
        for config in ${UBOOT_MACHINE}; do
            i=$(expr $i + 1);
            for type in ${UBOOT_CONFIG}; do
                j=$(expr $j + 1);
                if [ $j -eq $i ]
                then
                    install -d ${DEPLOYDIR}/${BOOT_TOOLS}
                    install -m 0777 ${B}/${config}/arch/arm/dts/${UBOOT_DTB_NAME}  ${DEPLOYDIR}/${BOOT_TOOLS}
                    install -m 0777 ${B}/${config}/u-boot-nodtb.bin  ${DEPLOYDIR}/${BOOT_TOOLS}/u-boot-nodtb.bin-${MACHINE}-${UBOOT_CONFIG}
                fi
            done
            unset  j
        done
        unset  i
    fi
}


PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mxs|mx5|mx6|mx7|vf|use-mainline-bsp)"
