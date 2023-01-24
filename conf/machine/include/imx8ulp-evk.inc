MACHINEOVERRIDES =. "mx8:mx8ulp:"

require conf/machine/include/imx-base.inc
require conf/machine/include/arm/armv8a/tune-cortexa35.inc

MACHINE_FEATURES += "pci wifi bluetooth optee jailhouse"

KERNEL_DEVICETREE = " \
    freescale/${KERNEL_DEVICETREE_BASENAME}.dtb \
"

IMX_DEFAULT_BOOTLOADER:use-nxp-bsp = "u-boot-imx"
IMX_DEFAULT_BOOTLOADER:use-mainline-bsp = "u-boot-fslc"

LOADADDR = ""
UBOOT_SUFFIX = "bin"
UBOOT_MAKE_TARGET = ""

SPL_BINARY = "spl/u-boot-spl.bin"

UBOOT_CONFIG ??= "sd"
UBOOT_CONFIG[sd]   = "${UBOOT_CONFIG_BASENAME}_defconfig,sdcard"
UBOOT_CONFIG[fspi] = "${UBOOT_CONFIG_BASENAME}_defconfig"
UBOOT_CONFIG[nd]   = "${UBOOT_CONFIG_BASENAME}_nd_defconfig"

# Set ATF platform name
ATF_PLATFORM = "imx8ulp"

IMXBOOT_TARGETS_SD   = "flash_singleboot flash_dualboot"
IMXBOOT_TARGETS_FSPI = "flash_dualboot_flexspi"
IMXBOOT_TARGETS_ND   = ""

IMXBOOT_TARGETS = "${@bb.utils.contains('UBOOT_CONFIG',   'sd', '${IMXBOOT_TARGETS_SD}', \
                      bb.utils.contains('UBOOT_CONFIG', 'fspi', '${IMXBOOT_TARGETS_FSPI}', \
                                                                '${IMXBOOT_TARGETS_ND}', d), d)}"

IMX_BOOT_SOC_TARGET = "iMX8ULP"
IMX_BOOT_SEEK = "32"

# We have to disable SERIAL_CONSOLE due to auto-serial-console
SERIAL_CONSOLES = "115200;ttyLP1"

# Add additional firmware
MACHINE_FIRMWARE:append = " firmware-imx-epdc"
