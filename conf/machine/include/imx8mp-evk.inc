MACHINEOVERRIDES =. "imx-boot-container:mx8:mx8m:mx8mp:"

require conf/machine/include/imx-base.inc
require conf/machine/include/tune-cortexa53.inc

MACHINE_FEATURES += "pci wifi bluetooth"

# NXP BSP can consume proprietary jailhouse and Marvell drivers
# OP-TEE is also applicable to NXP BSP, mainline BSP seems not to have
# a full support for it yet.
MACHINE_FEATURES_append_use-nxp-bsp = " optee jailhouse mrvl8997"

# Mainline kernel contains only one DTB file for
# imx8mpevk machine
KERNEL_DEVICETREE = " \
    freescale/${KERNEL_DEVICETREE_BASENAME}.dtb \
"

UBOOT_DTB_NAME = "${KERNEL_DEVICETREE_BASENAME}.dtb"

IMX_DEFAULT_BOOTLOADER_use-nxp-bsp = "u-boot-imx"
IMX_DEFAULT_BOOTLOADER_use-mainline-bsp = "u-boot-fslc"

UBOOT_SUFFIX = "bin"

UBOOT_CONFIG ??= "sd"
UBOOT_CONFIG[sd]      = "${UBOOT_CONFIG_BASENAME}_defconfig,sdcard"
UBOOT_CONFIG[ecc]     = "${UBOOT_CONFIG_BASENAME}_inline_ecc_defconfig"
UBOOT_CONFIG[mfgtool] = "${UBOOT_CONFIG_BASENAME}_defconfig"

SPL_BINARY = "spl/u-boot-spl.bin"

ATF_PLATFORM = "imx8mp"
ATF_LOAD_ADDR = "0x970000"

# Extra firmware package name, that is required to build boot container for fslc bsp
IMX_EXTRA_FIRMWARE = "firmware-imx-8m"

IMXBOOT_TARGETS = \
    "${@bb.utils.contains('UBOOT_CONFIG', 'fspi', '${IMXBOOT_TARGETS_BASENAME}_flexspi', \
                                                  '${IMXBOOT_TARGETS_BASENAME}', d)}"

IMX_BOOT_SOC_TARGET = "iMX8MP"

SERIAL_CONSOLES = "115200;ttymxc1"

LOADADDR = ""
UBOOT_SUFFIX = "bin"
UBOOT_MAKE_TARGET = "all"
IMX_BOOT_SEEK = "32"

OPTEE_BIN_EXT = "8mp"
TEE_LOAD_ADDR = "0x56000000"

# Add additional firmware
MACHINE_FIRMWARE_append = " linux-firmware-ath10k"
