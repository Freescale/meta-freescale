# Provides the QorIQ common settings for ARM64

KERNEL_IMAGETYPE ?= "Image"

SERIAL_CONSOLES ?= "115200;ttyS0 115200;ttyS1 115200;ttyAMA0"
SERIAL_CONSOLES_CHECK ?= "${SERIAL_CONSOLES}"

MACHINEOVERRIDES =. "qoriq-arm64:"

require conf/machine/include/qoriq-base.inc
