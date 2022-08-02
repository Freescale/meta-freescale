MACHINEOVERRIDES =. "mx8mn:"

# FIXME: u-boot-imx should be converted to `binman` and then we can
# avoid this specific overrides and handle it in a generic way.
MACHINEOVERRIDES =. "${@bb.utils.contains('IMX_DEFAULT_BOOTLOADER', 'u-boot-imx', '', 'imx-boot-container:', d)}"

require conf/machine/include/imx-base.inc
require conf/machine/include/arm/armv8a/tune-cortexa53.inc

# Mainline BSP defaults to "generic" cortexa53 configuration,
# adjust it here to include crypto extension which enables
# inline NEON and FPU code generation
DEFAULTTUNE:use-mainline-bsp = "cortexa53-crypto"

MACHINE_FEATURES += "wifi bluetooth bcm43455 bcm4356"

# NXP BSP can consume proprietary jailhouse and Broadcom drivers
# OP-TEE is also applicable to NXP BSP, mainline BSP seems not to have
# a full support for it yet.
MACHINE_FEATURES:append:use-nxp-bsp = " optee jailhouse bcm4359"

KERNEL_DEVICETREE = " \
	freescale/${KERNEL_DEVICETREE_BASENAME}.dtb \
"
KERNEL_DEVICETREE:append:use-nxp-bsp = " \
	freescale/${KERNEL_DEVICETREE_BASENAME}-ak5558.dtb \
	freescale/${KERNEL_DEVICETREE_BASENAME}-inmate.dtb \
	freescale/${KERNEL_DEVICETREE_BASENAME}-rm67191.dtb \
	freescale/${KERNEL_DEVICETREE_BASENAME}-root.dtb \
	freescale/${KERNEL_DEVICETREE_BASENAME}-rpmsg.dtb \
"
UBOOT_DTB_NAME = "${KERNEL_DEVICETREE_BASENAME}.dtb"

IMX_DEFAULT_BOOTLOADER:use-nxp-bsp = "u-boot-imx"
IMX_DEFAULT_BOOTLOADER:use-mainline-bsp = "u-boot-fslc"

UBOOT_SUFFIX = "bin"

UBOOT_CONFIG ??= "sd"
UBOOT_CONFIG[sd]      = "${UBOOT_CONFIG_BASENAME}_defconfig,sdcard"
UBOOT_CONFIG[fspi]    = "${UBOOT_CONFIG_BASENAME}_defconfig"
UBOOT_CONFIG[ld]      = "${UBOOT_CONFIG_BASENAME}_ld_defconfig"
UBOOT_CONFIG[mfgtool] = "${UBOOT_CONFIG_BASENAME}_defconfig"

SPL_BINARY = "spl/u-boot-spl.bin"

ATF_PLATFORM = "imx8mn"

IMXBOOT_TARGETS = "${@bb.utils.contains('UBOOT_CONFIG', 'fspi', '${IMXBOOT_TARGETS_BASENAME}_flexspi', '${IMXBOOT_TARGETS_BASENAME}', d)}"

IMX_BOOT_SOC_TARGET = "iMX8MN"

SERIAL_CONSOLES = "115200;ttymxc1"

# Add support for Syslinux to mainline BSP.
# U-Boot has the Distro Boot mode enabled by default, which
# require that either Syslinux to be enabled, or a boot script
# to be used to define the boot process.
# We opt-in for Syslinux, since it is designated as a preferred
# distro boot mode according to the U-Boot documentation.
UBOOT_EXTLINUX:use-mainline-bsp = "1"
UBOOT_EXTLINUX_LABELS:use-mainline-bsp = "default"
UBOOT_EXTLINUX_DEFAULT_LABEL:use-mainline-bsp = "i.MX8M Nano EVK"

UBOOT_EXTLINUX_MENU_DESCRIPTION:default:use-mainline-bsp = "i.MX8M Nano EVK"
UBOOT_EXTLINUX_FDT:default:use-mainline-bsp     = "../${KERNEL_DEVICETREE_BASENAME}.dtb"
UBOOT_EXTLINUX_CONSOLE:default:use-mainline-bsp = "console=${console}"
UBOOT_EXTLINUX_ROOT:default:use-mainline-bsp    = "root=/dev/mmcblk1p2"

# Add extlinux.conf to the lis of files, which are deployed onto the
# boot partition
IMAGE_BOOT_FILES:append:use-mainline-bsp = " extlinux.conf;extlinux/extlinux.conf"

BOOT_SPACE = "65536"
LOADADDR = ""
UBOOT_MAKE_TARGET = "all"

# Image boot offset as defined in section 6.1.6.1 "Primary image offset and IVT offset" of
# i.MX 8M Nano Applications Processor Reference Manual, Rev. 0, 12/2019
# Doc ID: IMX8MNRM
IMX_BOOT_SEEK = "32"

OPTEE_BIN_EXT = "8mn"
TEE_LOAD_ADDR = "0x56000000"

# Add additional firmware
MACHINE_FIRMWARE:append = " linux-firmware-ath10k"
