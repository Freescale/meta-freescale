require conf/machine/include/qoriq-base.inc

# providers of QorIQ PPC targets
PREFERRED_PROVIDER_virtual/kernel ?= "linux-qoriq"

UBOOT_ENTRYPOINT = "0x80008000"

MKUBIFS_ARGS ??= "-m 512 -e 15360 -c 3600"
UBINIZE_ARGS ??= "-m 512 -p 16KiB -s 512"

SERIAL_CONSOLES ?= "115200;ttyS0 115200;ttyS1 115200;ttyEHV0"
SERIAL_CONSOLES_CHECK ?= "${SERIAL_CONSOLES}"

MACHINEOVERRIDES .= ":qoriq-ppc"
