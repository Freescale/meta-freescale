MACHINEOVERRIDES =. "mx8mm:"

require conf/machine/include/imx-base.inc
require conf/machine/include/arm/armv8a/tune-cortexa53.inc

# Mainline BSP defaults to "generic" cortexa53 configuration,
# adjust it here to include crypto extension which enables
# inline NEON and FPU code generation
DEFAULTTUNE:use-mainline-bsp = "cortexa53-crypto"

MACHINE_FEATURES += "pci wifi bluetooth bcm43455 bcm4356 nxp8987-sdio"

# NXP BSP can consume proprietary jailhouse and BCM4359 firmware
# Since the firmware is not available publicly, and rather distributed
# under "Proprietary" license - we opt-out from using it in all BSPs
# and pin it to NXP BSP only
# OP-TEE is also applicable to NXP BSP, mainline BSP seems not to have
# a full support for it yet.
MACHINE_FEATURES:append:use-nxp-bsp = " optee jailhouse bcm4359"

KERNEL_DEVICETREE = " \
    freescale/${KERNEL_DEVICETREE_BASENAME}.dtb \
"
KERNEL_DEVICETREE:append:use-nxp-bsp = " \
    freescale/${KERNEL_DEVICETREE_BASENAME}-pcie-ep.dtb \
    freescale/${KERNEL_DEVICETREE_BASENAME}-rm67191.dtb \
    freescale/${KERNEL_DEVICETREE_BASENAME}-rm67191-cmd-ram.dtb \
    freescale/${KERNEL_DEVICETREE_BASENAME}-rm67199.dtb \
    freescale/${KERNEL_DEVICETREE_BASENAME}-rm67199-cmd-ram.dtb \
"
UBOOT_DTB_NAME = "${KERNEL_DEVICETREE_BASENAME}.dtb"

UBOOT_SUFFIX = "bin"

UBOOT_CONFIG ??= "sd"
UBOOT_CONFIG[sd]       = "${UBOOT_CONFIG_BASENAME}_defconfig,sdcard"

SPL_BINARY = "spl/u-boot-spl.bin"

ATF_PLATFORM = "imx8mm"

IMXBOOT_TARGETS = "${@bb.utils.contains('UBOOT_CONFIG', 'fspi', '${IMXBOOT_TARGETS_BASENAME}_flexspi', '${IMXBOOT_TARGETS_BASENAME}', d)}"

IMX_BOOT_SOC_TARGET = "iMX8MM"

SERIAL_CONSOLES = "115200;ttymxc1"

# Add support for Syslinux to mainline BSP.
# U-Boot has the Distro Boot mode enabled by default, which
# require that either Syslinux to be enabled, or a boot script
# to be used to define the boot process.
# We opt-in for Syslinux, since it is designated as a preferred
# distro boot mode according to the U-Boot documentation.
UBOOT_EXTLINUX:use-mainline-bsp = "1"
UBOOT_EXTLINUX_LABELS:use-mainline-bsp = "default"
UBOOT_EXTLINUX_DEFAULT_LABEL:use-mainline-bsp = "i.MX8M Mini EVK"

UBOOT_EXTLINUX_MENU_DESCRIPTION:default:use-mainline-bsp = "i.MX8M Mini EVK"
UBOOT_EXTLINUX_FDT:default:use-mainline-bsp     = "../${KERNEL_DEVICETREE_BASENAME}.dtb"
UBOOT_EXTLINUX_CONSOLE:default:use-mainline-bsp = "console=${console}"
UBOOT_EXTLINUX_ROOT:default:use-mainline-bsp    = "root=/dev/mmcblk1p2"

# Add extlinux.conf to the lis of files, which are deployed onto the
# boot partition
IMAGE_BOOT_FILES:append:use-mainline-bsp = " extlinux.conf;extlinux/extlinux.conf"

LOADADDR = ""
UBOOT_MAKE_TARGET = "all"
IMX_BOOT_SEEK = "33"

OPTEE_BIN_EXT = "8mm"
TEE_LOAD_ADDR = "0xbe000000"

# Add additional firmware
MACHINE_FIRMWARE:append = " linux-firmware-ath10k"
