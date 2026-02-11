MACHINEOVERRIDES =. "mx943:"

require conf/machine/include/imx-base.inc
require conf/machine/include/arm/armv8-2a/tune-cortexa55.inc

MACHINE_FEATURES += "pci wifi bluetooth"
MACHINE_FEATURES:append:use-nxp-bsp = " crrm optee dpdk nxpiw612-sdio nxp9098-pcie nxpaw693-pcie jailhouse rvgpu-emu"

KERNEL_DEVICETREE_BASENAME = "imx943-evk"
KERNEL_DEVICETREE = " \
    freescale/${KERNEL_DEVICETREE_BASENAME}.dtb \
    freescale/${KERNEL_DEVICETREE_BASENAME}-crrm.dtb \
    freescale/${KERNEL_DEVICETREE_BASENAME}-inmate.dtb \
    freescale/${KERNEL_DEVICETREE_BASENAME}-netc-rpmsg.dtb \
    freescale/${KERNEL_DEVICETREE_BASENAME}-mqs.dtb \
    freescale/${KERNEL_DEVICETREE_BASENAME}-pcie1-ep.dtb \
    freescale/${KERNEL_DEVICETREE_BASENAME}-pcie1-ep.dtbo \
    freescale/${KERNEL_DEVICETREE_BASENAME}-rpmsg.dtb \
    freescale/${KERNEL_DEVICETREE_BASENAME}-root.dtb \
    freescale/${KERNEL_DEVICETREE_BASENAME}-sdwifi.dtb \
    freescale/${KERNEL_DEVICETREE_BASENAME}-sgmii.dtb \
"
UBOOT_DTB_NAME = "${KERNEL_DEVICETREE_BASENAME}.dtb"

IMX_DEFAULT_BOOTLOADER:use-nxp-bsp = "u-boot-imx"
IMX_DEFAULT_BOOTLOADER:use-mainline-bsp = "u-boot-fslc"

LOADADDR = ""
UBOOT_SUFFIX = "bin"
UBOOT_MAKE_TARGET = ""

SPL_BINARY = "spl/u-boot-spl.bin"

UBOOT_CONFIG ??= "${@bb.utils.contains('COMBINED_FEATURES', 'crrm', 'crrm', 'sd', d)}"
UBOOT_CONFIG[sd]     = "${UBOOT_CONFIG_BASENAME}_defconfig"
UBOOT_CONFIG[sd-ecc] = "${UBOOT_CONFIG_BASENAME}_defconfig"
UBOOT_CONFIG[crrm]   = "${UBOOT_CONFIG_BASENAME}_xspi_crrm_defconfig"
UBOOT_CONFIG[xspi]   = "${UBOOT_CONFIG_BASENAME}_xspi_defconfig"
UBOOT_CONFIG_IMAGE_FSTYPES[sd]     = "sdcard"
UBOOT_CONFIG_IMAGE_FSTYPES[sd-ecc] = "sdcard"

ATF_PLATFORM = "imx94"
OEI_CORE = "m33"

IMXBOOT_VARIANTS = "alt jailhouse rpmsg netc netc_reset netc_standalone"
# imx943 netc have different mcore demo for different bootloader
#| |Standard | netc | netc_reset | netc_standalone
#|--|--|--|--|--|
#| M33S | imx943evk_cm33_core1_TCM_power_mode_switch.bin | imx943evk_cm33_core1_TCM_netc_share.bin | imx943evk_cm33_core1_TCM_all_reset_but_netc_switch.bin | imx943evk_cm33_core1_TCM_netc_switch_standalone.bin |
#| M70 | imx943evk_cm7_core0_TCM_power_mode_switch.bin | imx943evk_cm7_core0_TCM_power_mode_switch.bin | imx943evk_cm7_core0_TCM_power_mode_switch.bin | imx943evk_cm7_core0_TCM_power_mode_switch.bin |
#| M71 | imx943evk_cm7_core1_TCM_power_mode_switch.bin | imx943evk_cm7_core1_TCM_power_mode_switch.bin | imx943evk_cm7_core1_TCM_all_reset_but_netc_trigger.bin | imx943evk_cm7_core1_TCM_power_mode_switch.bin |

M33_IMAGE = "${@bb.utils.contains('IMXBOOT_VARIANT', 'netc',            'imx943evk_cm33_core1_TCM_netc_share.bin', \
                bb.utils.contains('IMXBOOT_VARIANT', 'netc_reset',      'imx943evk_cm33_core1_TCM_all_reset_but_netc_switch.bin', \
                bb.utils.contains('IMXBOOT_VARIANT', 'netc_standalone', 'imx943evk_cm33_core1_TCM_netc_switch_standalone.bin', \
                                                                        'imx943evk_cm33_core1_TCM_power_mode_switch.bin', d), d), d)}"
M70_IMAGE = "imx943evk_cm7_core0_TCM_power_mode_switch.bin"
M71_IMAGE = "${@bb.utils.contains('IMXBOOT_VARIANT', 'netc_reset', 'imx943evk_cm7_core1_TCM_all_reset_but_netc_trigger.bin', \
                                                                   'imx943evk_cm7_core1_TCM_power_mode_switch.bin', d)}"

# Multiple system manager configs by IMXBOOT_VARIANT
SYSTEM_MANAGER_CONFIG = "${@bb.utils.contains('IMXBOOT_VARIANT', 'alt',            'mx94alt', \
                           bb.utils.contains('IMXBOOT_VARIANT', 'jailhouse',       'mx94evkjailhouse', \
                           bb.utils.contains('IMXBOOT_VARIANT', 'netc',            'mx94evknetc', \
                           bb.utils.contains('IMXBOOT_VARIANT', 'netc_standalone', 'mx94evknetc', \
                           bb.utils.contains('IMXBOOT_VARIANT', 'netc_reset',      'mx94evknetc', \
                           bb.utils.contains('IMXBOOT_VARIANT', 'rpmsg',           'mx94evkrpmsg', \
                                                                                   'mx94evk', d), d), d), d), d),d) }"

MSEL_TYPE = "${@bb.utils.contains('IMXBOOT_VARIANT', 'alt',       '1', \
                                                                  '0', d)}"

SYSTEM_MANAGER_FIRMWARE_BASENAME = "m33_image"

IMXBOOT_TARGETS_BASENAME = "flash"

# imx-boot (flash.bin) targets based on UBOOT_CONFIG and IMXBOOT_VARIANT
IMXBOOT_TARGETS_SD = "${@bb.utils.contains('IMXBOOT_VARIANT', 'alt',             '${IMXBOOT_TARGETS_BASENAME}_a55', \
                         bb.utils.contains('IMXBOOT_VARIANT', 'jailhouse',       '${IMXBOOT_TARGETS_BASENAME}_jailhouse', \
                         bb.utils.contains('IMXBOOT_VARIANT', 'netc',            '${IMXBOOT_TARGETS_BASENAME}_all', \
                         bb.utils.contains('IMXBOOT_VARIANT', 'netc_standalone', '${IMXBOOT_TARGETS_BASENAME}_all', \
                         bb.utils.contains('IMXBOOT_VARIANT', 'netc_reset',      '${IMXBOOT_TARGETS_BASENAME}_all', \
                         bb.utils.contains('IMXBOOT_VARIANT', 'rpmsg',           '${IMXBOOT_TARGETS_BASENAME}_a55', \
                                                                                 '${IMXBOOT_TARGETS_BASENAME}_all ${IMXBOOT_TARGETS_BASENAME}_a55', d), d), d), d), d), d)} \
"

IMXBOOT_TARGETS = " \
    ${@bb.utils.contains('UBOOT_CONFIG', 'crrm',   'flash_a55_xspi_crrm', \
       bb.utils.contains('UBOOT_CONFIG', 'xspi',   'flash_a55_xspi', \
       bb.utils.contains('UBOOT_CONFIG', 'sd-ecc', '${IMXBOOT_TARGETS_BASENAME}_all', \
                                                   '${IMXBOOT_TARGETS_SD}', d), d), d)}"

IMX_BOOT_SOC_TARGET = "iMX94"
IMX_BOOT_SEEK = "32"

# We have to disable SERIAL_CONSOLE due to auto-serial-console
SERIAL_CONSOLES = "115200;ttyLP0"

IMX_DEFAULT_BSP = "nxp"

WKS_FILE_DEPENDS:append:imx-nxp-bsp = " imx-mcore-demos"
IMAGE_BOOT_FILES:append:imx-nxp-bsp = " \
    mcore-demos/imx943evk_cm33_core1_TCM_all_reset_but_netc_switch.bin \
    mcore-demos/imx943evk_cm33_core1_TCM_hello_world.bin \
    mcore-demos/imx943evk_cm33_core1_TCM_netc_share.bin \
    mcore-demos/imx943evk_cm33_core1_TCM_netc_switch_standalone.bin \
    mcore-demos/imx943evk_cm33_core1_TCM_power_mode_switch.bin \
    mcore-demos/imx943evk_cm33_core1_TCM_rpmsg_lite_pingpong_rtos_linux_remote.bin \
    mcore-demos/imx943evk_cm33_core1_TCM_rpmsg_lite_str_echo_rtos.bin \
    mcore-demos/imx943evk_cm7_core0_TCM_hello_world.bin \
    mcore-demos/imx943evk_cm7_core0_TCM_power_mode_switch.bin \
    mcore-demos/imx943evk_cm7_core0_TCM_rpmsg_lite_pingpong_rtos_linux_remote.bin \
    mcore-demos/imx943evk_cm7_core0_TCM_rpmsg_lite_str_echo_rtos.bin \
    mcore-demos/imx943evk_cm7_core0_TCM_sai_low_power_audio.bin \
    mcore-demos/imx943evk_cm7_core1_TCM_all_reset_but_netc_trigger.bin \
    mcore-demos/imx943evk_cm7_core1_TCM_hello_world.bin \
    mcore-demos/imx943evk_cm7_core1_TCM_power_mode_switch.bin \
    mcore-demos/imx943evk_cm7_core1_TCM_rpmsg_lite_pingpong_rtos_linux_remote.bin \
    mcore-demos/imx943evk_cm7_core1_TCM_rpmsg_lite_str_echo_rtos.bin \
"
