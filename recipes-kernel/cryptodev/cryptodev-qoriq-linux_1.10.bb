require recipes-kernel/cryptodev/cryptodev-linux_${PV}.bb
require cryptodev-qoriq-${PV}.inc

BBCLASSEXTEND = ""
COMPATIBLE_MACHINE = "(qoriq)"
