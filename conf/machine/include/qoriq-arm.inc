require conf/machine/include/qoriq-base.inc

DEFAULTTUNE ?= "cortexa7hf-neon"

# providers of QorIQ ARM targets
PREFERRED_PROVIDER_virtual/kernel ?= "linux-ls1"

UBOOT_ENTRYPOINT = "0x80008000"

MKUBIFS_ARGS ??= "-m 512 -e 15360 -c 3600"
UBINIZE_ARGS ??= "-m 512 -p 16KiB -s 512"

SERIAL_CONSOLES ?= "115200;ttyS0 115200;ttyS1 115200;ttyEHV0 115200;ttyLP0 115200;ttyAMA0"
SERIAL_CONSOLES_CHECK ?= "${SERIAL_CONSOLES}"

MACHINEOVERRIDES .= ":qoriq-arm"
