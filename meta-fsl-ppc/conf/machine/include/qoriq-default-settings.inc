MACHINE_FEATURES = "keyboard pci ext2 ext3 serial"
MACHINE_EXTRA_RRECOMMENDS += "udev-extraconf kernel-modules"

IMAGE_CLASSES += "image_types_uboot"
EXTRA_IMAGEDEPENDS += "u-boot"

KERNEL_IMAGETYPE ?= "uImage"
# disable the images below for now
#       ext2.bz2 ext2.lzma \
#       ext2.gz.u-boot ext2.bz2.u-boot ext2.lzma.u-boot"

MACHINEOVERRIDES .= ":qoriq-ppc"

SERIAL_CONSOLES ?= "115200;ttyS0 115200;ttyS1 115200;ttyEHV0"
SERIAL_CONSOLES_CHECK ?= "${SERIAL_CONSOLES}"

USE_VT = "0"

MKUBIFS_ARGS ??= "-m 512 -e 15360 -c 3600"
UBINIZE_ARGS ??= "-m 512 -p 16KiB -s 512"

