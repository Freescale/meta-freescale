MACHINEOVERRIDES =. "mx8dxl:"

require conf/machine/include/imx-base.inc
require conf/machine/include/arm/armv8a/tune-cortexa35.inc

IMX_DEFAULT_BSP = "nxp"

MACHINE_FEATURES += "pci bcm43455 bcm4356 nxp8997-pcie nxp9098-pcie"
MACHINE_FEATURES:append:use-nxp-bsp = " bcm4359"

# Don't include kernels in standard images
RRECOMMENDS:${KERNEL_PACKAGE_NAME}-base = ""

LOADADDR = ""

# Disable the serial console due to auto-serial-console
SERIAL_CONSOLES = "115200;ttyLP0"

# we do not want to have getty running on tty1 as we run
# auto-serial-console there
USE_VT = "0"

KERNEL_DEVICETREE = " \
    freescale/${KERNEL_DEVICETREE_BASENAME}.dtb \
"
KERNEL_DEVICETREE:append:use-nxp-bsp = " \
    freescale/${KERNEL_DEVICETREE_BASENAME}-rpmsg.dtb \
"

ATF_PLATFORM = "imx8dxl"
IMX_BOOT_SOC_TARGET = "iMX8DXL"

# This machine is not supported by u-boot-fslc, so we force it to use
# u-boot-imx here.
IMX_DEFAULT_BOOTLOADER = "u-boot-imx"

UBOOT_MAKE_TARGET = "all"
SPL_BINARY = "spl/u-boot-spl.bin"
UBOOT_SUFFIX = "bin"

UBOOT_CONFIG ??= "sd"
UBOOT_CONFIG[sd]   = "${UBOOT_CONFIG_BASENAME}_defconfig,sdcard"
UBOOT_CONFIG[fspi] = "${UBOOT_CONFIG_BASENAME}_fspi_defconfig"

IMX_BOOT_SEEK = "32"

IMXBOOT_TARGETS_SD = \
    "${@bb.utils.contains('MACHINE_FEATURES', 'optee', 'flash_spl', \
                                                       'flash', d)}"
IMXBOOT_TARGETS_FSPI = \
    "${@bb.utils.contains('MACHINE_FEATURES', 'optee', 'flash_spl_flexspi', \
                                                       'flash_flexspi', d)}"
IMXBOOT_TARGETS_NAND = \
    "${@bb.utils.contains('MACHINE_FEATURES', 'optee', 'flash_spl_nand', \
                                                       'flash_nand flash_nand_fw', d)}"
IMXBOOT_TARGETS = \
    "${@bb.utils.contains('UBOOT_CONFIG',        'sd', '${IMXBOOT_TARGETS_SD}', \
        bb.utils.contains('UBOOT_CONFIG',      'fspi', '${IMXBOOT_TARGETS_FSPI}', \
        bb.utils.contains('UBOOT_CONFIG',      'nand', '${IMXBOOT_TARGETS_NAND}', \
                                                                       'INVALID', d), d), d)}"
