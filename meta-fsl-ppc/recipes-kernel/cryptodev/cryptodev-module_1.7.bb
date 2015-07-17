require cryptodev_${PV}.inc

SUMMARY = "A /dev/crypto device driver kernel module"

inherit module qoriq_build_64bit_kernel

# Header file provided by a separate package
DEPENDS += "cryptodev-linux"

EXTRA_OEMAKE='KERNEL_DIR="${STAGING_KERNEL_DIR}" PREFIX="${D}"'
