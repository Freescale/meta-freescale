require conf/machine/include/imx-base.inc
require conf/machine/include/arm/armv8a/tune-cortexa35.inc

IMX_DEFAULT_BSP = "nxp"

MACHINE_FEATURES += "pci optee bcm43455 bcm4356 nxp8997-pcie nxp9098-pcie"
MACHINE_FEATURES:append:use-nxp-bsp = " bcm4359"

# Don't include kernels in standard images
RRECOMMENDS:${KERNEL_PACKAGE_NAME}-base = ""

LOADADDR = ""

# We have to disable SERIAL_CONSOLE due to auto-serial-console
SERIAL_CONSOLES = "115200;ttyAMA0"

# we do not want to have getty running on tty1 as we run
# auto-serial-console there
USE_VT = "0"

KERNEL_DEVICETREE = " \
    freescale/${KERNEL_DEVICETREE_BASENAME}.dtb \
"
KERNEL_DEVICETREE:append:use-nxp-bsp = " \
    freescale/${KERNEL_DEVICETREE_BASENAME}-dsi-rm67191.dtb \
    freescale/${KERNEL_DEVICETREE_BASENAME}-dsi-rm67191-rpmsg.dtb \
    freescale/${KERNEL_DEVICETREE_BASENAME}-enet2-tja1100.dtb \
    freescale/${KERNEL_DEVICETREE_BASENAME}-it6263-lvds0-dual-channel.dtb \
    freescale/${KERNEL_DEVICETREE_BASENAME}-it6263-lvds0-dual-channel-rpmsg.dtb \
    freescale/${KERNEL_DEVICETREE_BASENAME}-it6263-lvds1-dual-channel.dtb \
    freescale/${KERNEL_DEVICETREE_BASENAME}-it6263-lvds1-dual-channel-rpmsg.dtb \
    freescale/${KERNEL_DEVICETREE_BASENAME}-jdi-wuxga-lvds0-panel.dtb \
    freescale/${KERNEL_DEVICETREE_BASENAME}-jdi-wuxga-lvds0-panel-rpmsg.dtb \
    freescale/${KERNEL_DEVICETREE_BASENAME}-jdi-wuxga-lvds1-panel.dtb \
    freescale/${KERNEL_DEVICETREE_BASENAME}-jdi-wuxga-lvds1-panel-rpmsg.dtb \
    freescale/${KERNEL_DEVICETREE_BASENAME}-ov5640.dtb \
    freescale/${KERNEL_DEVICETREE_BASENAME}-ov5640-rpmsg.dtb \
    freescale/${KERNEL_DEVICETREE_BASENAME}-rpmsg.dtb \
"

UBOOT_MAKE_TARGET = \
    "${@bb.utils.contains('MACHINE_FEATURES', 'optee', '', \
                                                       'u-boot.bin', d)}"
SPL_BINARY = \
    "${@bb.utils.contains('MACHINE_FEATURES', 'optee', 'spl/u-boot-spl.bin', \
                                                       '', d)}"

UBOOT_CONFIG ??= "sd"
UBOOT_CONFIG[sd]   = "${UBOOT_CONFIG_BASENAME}_defconfig,sdcard"
UBOOT_CONFIG[fspi] = "${UBOOT_CONFIG_BASENAME}_fspi_defconfig"

IMX_BOOT_SEEK = "32"

# This machine is not supported by u-boot-fslc, so we force it to use
# u-boot-imx here.
IMX_DEFAULT_BOOTLOADER = "u-boot-imx"
UBOOT_SUFFIX = "bin"

IMXBOOT_TARGETS_SD = \
    "${@bb.utils.contains('MACHINE_FEATURES', 'optee', 'flash_spl', \
                                                       'flash', d)}"
IMXBOOT_TARGETS_FSPI = \
    "${@bb.utils.contains('MACHINE_FEATURES', 'optee', 'flash_spl_flexspi', \
                                                       'flash_flexspi', d)}"
IMXBOOT_TARGETS = \
    "${@bb.utils.contains('UBOOT_CONFIG',        'sd', '${IMXBOOT_TARGETS_SD}', \
                                                       '${IMXBOOT_TARGETS_FSPI}', d)}"
BOARD_TYPE = "mek"

# Add additional firmware
MACHINE_FIRMWARE:append = " linux-firmware-ath10k"
