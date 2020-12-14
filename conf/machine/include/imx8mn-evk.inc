MACHINEOVERRIDES =. "mx8:mx8m:mx8mn:"

require conf/machine/include/imx-base.inc
require conf/machine/include/tune-cortexa53.inc

MACHINE_FEATURES += "wifi bluetooth optee bcm43455 bcm4356"

# NXP BSP can consume proprietary jailhouse and Broadcom drivers
MACHINE_FEATURES_append_use-nxp-bsp = " jailhouse bcm4359"

KERNEL_DEVICETREE = " \
	freescale/${KERNEL_DEVICETREE_BASENAME}.dtb \
"
KERNEL_DEVICETREE_append_use-nxp-bsp = " \
	freescale/${KERNEL_DEVICETREE_BASENAME}-ak5558.dtb \
	freescale/${KERNEL_DEVICETREE_BASENAME}-inmate.dtb \
	freescale/${KERNEL_DEVICETREE_BASENAME}-rm67191.dtb \
	freescale/${KERNEL_DEVICETREE_BASENAME}-root.dtb \
	freescale/${KERNEL_DEVICETREE_BASENAME}-rpmsg.dtb \
"
UBOOT_DTB_NAME = "${KERNEL_DEVICETREE_BASENAME}.dtb"

UBOOT_CONFIG ??= "sd"
UBOOT_CONFIG[sd]      = "${UBOOT_CONFIG_BASENAME}_defconfig,sdcard"
UBOOT_CONFIG[fspi]    = "${UBOOT_CONFIG_BASENAME}_defconfig"
UBOOT_CONFIG[mfgtool] = "${UBOOT_CONFIG_BASENAME}_defconfig"

SPL_BINARY = "spl/u-boot-spl.bin"

ATF_PLATFORM = "imx8mn"

IMXBOOT_TARGETS = "${@bb.utils.contains('UBOOT_CONFIG', 'fspi', '${IMXBOOT_TARGETS_BASENAME}_flexspi', '${IMXBOOT_TARGETS_BASENAME}', d)}"

IMX_BOOT_SOC_TARGET = "iMX8MN"

SERIAL_CONSOLES = "115200;ttymxc1"

BOOT_SPACE = "65536"
LOADADDR = ""
UBOOT_SUFFIX = "bin"
UBOOT_MAKE_TARGET = ""

# Image boot offset as defined in section 6.1.6.1 "Primary image offset and IVT offset" of
# i.MX 8M Nano Applications Processor Reference Manual, Rev. 0, 12/2019
# Doc ID: IMX8MNRM
IMX_BOOT_SEEK = "32"

OPTEE_BIN_EXT = "8mn"

# Add additional firmware
MACHINE_FIRMWARE_append = " linux-firmware-ath10k"
