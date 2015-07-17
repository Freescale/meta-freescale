# Provides the QorIQ common settings
require conf/machine/include/soc-family.inc

# providers
PREFERRED_PROVIDER_virtual/kernel ?= "linux-qoriq"
PREFERRED_PROVIDER_u-boot ?= "u-boot-qoriq"

# versions
PREFERRED_VERSION_qemu = "2.2.0+fsl"
PREFERRED_VERSION_openssl = "1.0.1i"

# settings
MACHINE_FEATURES = "keyboard pci ext2 ext3 serial"
MACHINE_EXTRA_RRECOMMENDS += "udev-rules-qoriq kernel-modules"
MACHINEOVERRIDES .= ":qoriq-ppc"

IMAGE_CLASSES += "image_types_uboot"
EXTRA_IMAGEDEPENDS += "u-boot"

KERNEL_IMAGETYPE ?= "uImage"

SERIAL_CONSOLES ?= "115200;ttyS0 115200;ttyS1 115200;ttyEHV0"
SERIAL_CONSOLES_CHECK ?= "${SERIAL_CONSOLES}"

USE_VT = "0"

