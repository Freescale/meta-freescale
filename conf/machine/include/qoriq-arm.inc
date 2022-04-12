# Provides the common settings for QorIQ ARM

# default assignment to override defaults from kernel.bbclass
# should be assigned by machine if other type needed
KERNEL_IMAGETYPE ?= "uImage"

SERIAL_CONSOLES ?= "115200;ttyS0 115200;ttyS1 115200;ttyEHV0 115200;ttyLP0 115200;ttyAMA0"
SERIAL_CONSOLES_CHECK ?= "${SERIAL_CONSOLES}"

MACHINEOVERRIDES =. "qoriq-arm:"

require conf/machine/include/qoriq-base.inc
