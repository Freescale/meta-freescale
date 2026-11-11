require conf/machine/include/imx-base.inc
require conf/machine/include/arm/armv8-2a/tune-cortexa55.inc

MACHINE_FEATURES += "pci wifi bluetooth"
MACHINE_FEATURES:append:use-nxp-bsp = " dpdk optee nxpiw610-sdio nxpwifi-all-sdio nxpwifi-all-usb"

KERNEL_DEVICETREE = " \
    freescale/${KERNEL_DEVICETREE_BASENAME}.dtb \
"
UBOOT_DTB_NAME = "${KERNEL_DEVICETREE_BASENAME}.dtb"

IMX_DEFAULT_BOOTLOADER:use-nxp-bsp = "u-boot-imx"
IMX_DEFAULT_BOOTLOADER:use-mainline-bsp = "u-boot-fslc"

LOADADDR = ""
UBOOT_SUFFIX = "bin"
UBOOT_MAKE_TARGET = ""

SPL_BINARY = "spl/u-boot-spl.bin"

UBOOT_CONFIG ??= "sd"
UBOOT_CONFIG[sd]   = "${UBOOT_CONFIG_BASENAME}_defconfig"
UBOOT_CONFIG[ecc] = "${UBOOT_CONFIG_BASENAME}_inline_ecc_defconfig"

# Set ATF platform name
ATF_PLATFORM = "imx91"

IMXBOOT_TARGETS_SD   = "flash_singleboot flash_singleboot_gdet flash_singleboot_gdet_auto"
IMXBOOT_TARGETS_ECC  = "flash_singleboot"
IMXBOOT_TARGETS_NAND = "flash_singleboot_spinand flash_singleboot_spinand_fw"
IMXBOOT_TARGETS_FSPI = "flash_singleboot_flexspi"

IMXBOOT_TARGETS = "${@bb.utils.contains('UBOOT_CONFIG', 'nand', '${IMXBOOT_TARGETS_NAND}', \
                      bb.utils.contains('UBOOT_CONFIG', 'fspi', '${IMXBOOT_TARGETS_FSPI}', \
                      bb.utils.contains('UBOOT_CONFIG', 'ecc',  '${IMXBOOT_TARGETS_ECC}', \
                                                                '${IMXBOOT_TARGETS_SD}', d), d), d)}"

IMX_BOOT_SOC_TARGET = "iMX91"
IMX_BOOT_SEEK = "32"

# We have to disable SERIAL_CONSOLE due to auto-serial-console
SERIAL_CONSOLES = "115200;ttyLP0"

IMX_DEFAULT_BSP = "nxp"
