require conf/machine/include/soc-family.inc

# Freescale BSP default providers
PREFERRED_PROVIDER_u-boot_ls102xa ?= "u-boot-ls1"
PREFERRED_PROVIDER_virtual/bootloader_ls102xa ?= "u-boot-ls1"
PREFERRED_PROVIDER_virtual/kernel_ls102xa ?= "linux-ls1"
PREFERRED_PROVIDER_cryptodev_ls102xa = "cryptodev-qoriq"
PREFERRED_PROVIDER_cryptodev-module_ls102xa = "cryptodev-qoriq-module"
PREFERRED_PROVIDER_cryptodev-tests_ls102xa = "cryptodev-qoriq-tests"
PREFERRED_PROVIDER_openssl_ls102xa = "openssl-qoriq"

# Define the default image type
IMAGE_CLASSES ?= "image_types_uboot"
KERNEL_IMAGETYPE ?= "uImage"

UBOOT_ENTRYPOINT_ls102xa = "0x80008000"

MACHINE_FEATURES = "pci ext2 ext3 serial"
MACHINE_EXTRA_RRECOMMENDS = "udev-extraconf"

DEFAULTTUNE_ls102xa ?= "cortexa7hf-neon"
