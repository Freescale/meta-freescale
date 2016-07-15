# Provides the common settings for QorIQ PPC

UBOOT_ENTRYPOINT = "0x80008000"

SERIAL_CONSOLES ?= "115200;ttyS0 115200;ttyS1 115200;ttyEHV0"
SERIAL_CONSOLES_CHECK ?= "${SERIAL_CONSOLES}"

MACHINEOVERRIDES =. "qoriq-ppc:"

require conf/machine/include/qoriq-base.inc
