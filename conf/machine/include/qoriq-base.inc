# common providers of QorIQ targets
PREFERRED_PROVIDER_u-boot ??= "u-boot-qoriq"
PREFERRED_PROVIDER_virtual/bootloader ??= "${PREFERRED_PROVIDER_u-boot}"
PREFERRED_PROVIDER_virtual/kernel ??= "linux-qoriq"
PREFERRED_PROVIDER_u-boot-tools-native ??= "u-boot-tools-native"
PREFERRED_PROVIDER_u-boot-mkimage-native ??= "u-boot-tools-native"
PREFERRED_PROVIDER_u-boot-mkimage ??= "u-boot-tools"
PREFERRED_PROVIDER_nativesdk-u-boot-mkimage ??= "nativesdk-u-boot-tools"
PREFERRED_VERSION_testfloat = "2a"

SOC_DEFAULT_IMAGE_FSTYPES = "tar.gz"
SOC_DEFAULT_IMAGE_FSTYPES:append:ls1043a = " wic.bz2"
SOC_DEFAULT_IMAGE_FSTYPES:append:ls1046a = " wic.bz2"
IMAGE_FSTYPES ?= "${SOC_DEFAULT_IMAGE_FSTYPES}"

# Composite Image Layout (in KBytes)
IMAGE_OFFSET_PBL ?= "4"
IMAGE_OFFSET_FIP ?= "1024"
IMAGE_OFFSET_FMAN_UCODE ?= "9216"

# Composite Image Filenames
IMAGE_FILE_PBL_SD ?= "atf/bl2_sd.pbl"
IMAGE_FILE_FIP_UBOOT ?= "atf/fip_uboot.bin"

# Do not update fstab file when using wic images
WIC_CREATE_EXTRA_ARGS ?= "--no-fstab-update"

IMAGE_BOOT_FILES ?= " \
    ${KERNEL_IMAGETYPE} \
    *.dtb \
"

SOC_DEFAULT_WKS_FILE ?= ""
SOC_DEFAULT_WKS_FILE:ls1043a ?= "ls104x-uboot-bootpart.wks.in"
SOC_DEFAULT_WKS_FILE:ls1046a ?= "ls104x-uboot-bootpart.wks.in"

WKS_FILE ?= "${SOC_DEFAULT_WKS_FILE}"

MACHINE_FEATURES ?= "pci ext2 ext3 serial"
MACHINE_EXTRA_RRECOMMENDS += "udev-extraconf udev-rules-qoriq kernel-modules"

EXTRA_IMAGEDEPENDS += "u-boot qoriq-cst-native"

MACHINEOVERRIDES =. "qoriq:"

# Machines or distros can define which BSP it should use by default. We are
# intending to default for nxp BSP by default and specific machines or
# DISTROs might change it if need.
#
# Two values are considered valid: mainline, nxp
QORIQ_DEFAULT_BSP ?= "nxp"

MACHINEOVERRIDES =. "use-${QORIQ_DEFAULT_BSP}-bsp:"

# Sub-architecture support
MACHINE_SOCARCH_SUFFIX ?= ""
MACHINE_SOCARCH_SUFFIX:qoriq = "-qoriq"

MACHINE_ARCH_FILTER = "virtual/kernel"

INHERIT += "fsl-dynamic-packagearch"
