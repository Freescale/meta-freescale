MACHINEOVERRIDES =. "mx937:"

require conf/machine/include/imx-base.inc
require conf/machine/include/arm/armv8-2a/tune-cortexa55.inc

MACHINE_FEATURES += "pci wifi bluetooth"
MACHINE_FEATURES:append:use-nxp-bsp = " optee nxpiw612-sdio jailhouse"

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
UBOOT_CONFIG[sd] = "${UBOOT_CONFIG_BASENAME}_defconfig"
UBOOT_CONFIG[sd-ecc] = "${UBOOT_CONFIG_BASENAME}_defconfig"

ATF_PLATFORM = "imx937"

OEI_CORE   = "m33"
OEI_SOC    = "mx937"
OEI_BOARD  ?= "mx937lp5"

DDR_TYPE   ?= "lpddr5"

SYSTEM_MANAGER_FIRMWARE_BASENAME = "m33_image"

IMXBOOT_VARIANTS = "alt jailhouse rpmsg"

# Multiple system manager configs by IMXBOOT_VARIANT
SYSTEM_MANAGER_CONFIG = \
    "${@bb.utils.contains('IMXBOOT_VARIANT', 'alt',       'mx937alt', \
        bb.utils.contains('IMXBOOT_VARIANT', 'jailhouse', 'mx937frdmjailhouse', \
        bb.utils.contains('IMXBOOT_VARIANT', 'rpmsg',     'mx937frdmrpmsg', \
                                                          '${SYSTEM_MANAGER_CONFIG_DEFAULT}', d), d), d)}"

# imx-boot (flash.bin) targets based on UBOOT_CONFIG and IMXBOOT_VARIANT
IMXBOOT_TARGETS_SD = " \
    ${@bb.utils.contains('IMXBOOT_VARIANT', 'alt',       '${IMXBOOT_TARGETS_BASENAME}_all', \
       bb.utils.contains('IMXBOOT_VARIANT', 'jailhouse', '${IMXBOOT_TARGETS_BASENAME}_jailhouse', \
       bb.utils.contains('IMXBOOT_VARIANT', 'rpmsg',     '${IMXBOOT_TARGETS_BASENAME}_lpboot_sm_a55', \
                                                         '${IMXBOOT_TARGETS_BASENAME}_all ${IMXBOOT_TARGETS_BASENAME}_a55', d), d), d)} \
"
IMXBOOT_TARGETS = " \
    ${@bb.utils.contains('UBOOT_CONFIG', 'sd-ecc', '${IMXBOOT_TARGETS_BASENAME}_all', \
                                                   '${IMXBOOT_TARGETS_SD}', d)} \
"

IMX_BOOT_SOC_TARGET = "iMX937"
IMX_BOOT_SEEK = "32"

# We have to disable SERIAL_CONSOLE due to auto-serial-console
SERIAL_CONSOLES = "115200;ttyLP0"

IMX_DEFAULT_BSP = "nxp"
