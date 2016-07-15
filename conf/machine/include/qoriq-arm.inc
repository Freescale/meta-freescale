# Provides the common settings for QorIQ ARM

KERNEL_IMAGETYPE ?= "uImage"

SERIAL_CONSOLES ?= "115200;ttyS0 115200;ttyS1 115200;ttyEHV0 115200;ttyLP0 115200;ttyAMA0"
SERIAL_CONSOLES_CHECK ?= "${SERIAL_CONSOLES}"

MACHINEOVERRIDES =. "qoriq-arm:"

require conf/machine/include/qoriq-base.inc
