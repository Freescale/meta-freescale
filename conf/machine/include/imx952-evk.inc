MACHINEOVERRIDES =. "mx952:"

require conf/machine/include/imx-base.inc
require conf/machine/include/arm/armv8-2a/tune-cortexa55.inc

MACHINE_FEATURES += "pci wifi bluetooth"
MACHINE_FEATURES:append:use-nxp-bsp = " crrm jailhouse optee xen"

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

UBOOT_CONFIG ??= "${@bb.utils.contains('COMBINED_FEATURES', 'crrm', 'crrm', 'sd', d)}"
UBOOT_CONFIG[sd] = "${UBOOT_CONFIG_BASENAME}_defconfig"
UBOOT_CONFIG[sd-ecc] = "${UBOOT_CONFIG_BASENAME}_defconfig"
UBOOT_CONFIG[crrm] = "${UBOOT_CONFIG_BASENAME}_xspi_crrm_defconfig"
UBOOT_CONFIG[xspi] = "${UBOOT_CONFIG_BASENAME}_xspi_defconfig"

ATF_PLATFORM = "imx952"

OEI_CORE   = "m33"
OEI_SOC    = "mx952"
OEI_BOARD  ?= "mx95lp5"

DDR_TYPE   ?= "lpddr5"

IMXBOOT_VARIANTS = "alt jailhouse netc rpmsg"

# Multiple system manager configs by IMXBOOT_VARIANT
SYSTEM_MANAGER_CONFIG = \
    "${@bb.utils.contains('IMXBOOT_VARIANT', 'alt',       'mx952alt', \
        bb.utils.contains('IMXBOOT_VARIANT', 'jailhouse', 'mx952evkjailhouse', \
        bb.utils.contains('IMXBOOT_VARIANT', 'netc',      'mx952evknetc', \
        bb.utils.contains('IMXBOOT_VARIANT', 'rpmsg',     'mx952evkrpmsg', \
                                                          'mx952evk', d), d), d), d)}"

# imx-boot (flash.bin) targets based on UBOOT_CONFIG and IMXBOOT_VARIANT
IMXBOOT_TARGETS_SD = " \
    ${@bb.utils.contains('IMXBOOT_VARIANT', 'alt',       '${IMXBOOT_TARGETS_BASENAME}_all', \
       bb.utils.contains('IMXBOOT_VARIANT', 'jailhouse', '${IMXBOOT_TARGETS_BASENAME}_jailhouse', \
       bb.utils.contains('IMXBOOT_VARIANT', 'netc',      '${IMXBOOT_TARGETS_BASENAME}_all', \
       bb.utils.contains('IMXBOOT_VARIANT', 'rpmsg',     '${IMXBOOT_TARGETS_BASENAME}_lpboot_sm_a55', \
                                                         '${IMXBOOT_TARGETS_BASENAME}_all ${IMXBOOT_TARGETS_BASENAME}_a55', d), d), d), d)} \
"
IMXBOOT_TARGETS = " \
    ${@bb.utils.contains('UBOOT_CONFIG', 'crrm',   '${IMXBOOT_TARGETS_BASENAME}_a55_xspi_crrm', \
       bb.utils.contains('UBOOT_CONFIG', 'sd-ecc', '${IMXBOOT_TARGETS_BASENAME}_all', \
       bb.utils.contains('UBOOT_CONFIG', 'xspi',   '${IMXBOOT_TARGETS_BASENAME}_a55_xspi', \
                                                   '${IMXBOOT_TARGETS_SD}', d), d), d)} \
"

M4_DEFAULT_IMAGE_MX952 = "${@bb.utils.contains('IMXBOOT_VARIANT', 'netc', 'imx952evk_m7_TCM_netc_share.bin', \
                                                                          'imx952evk_m7_TCM_power_mode_switch.bin', d)}"

IMX_BOOT_SOC_TARGET = "iMX952"
IMX_BOOT_SEEK = "32"

# We have to disable SERIAL_CONSOLE due to auto-serial-console
SERIAL_CONSOLES = "115200;ttyLP0"

IMX_DEFAULT_BSP = "nxp"

IMAGE_BOOT_FILES:append:imx-nxp-bsp = " \
    ${@bb.utils.contains('COMBINED_FEATURES', 'xen', 'xen', '', d)} \
"
