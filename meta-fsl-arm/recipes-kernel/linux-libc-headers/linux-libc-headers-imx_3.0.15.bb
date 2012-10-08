require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

PROVIDES = "linux-libc-headers"
RPROVIDES_${PN}-dev = "linux-libc-headers-dev"
RPROVIDES_${PN}-dbg = "linux-libc-headers-dbg"

COMPATIBLE_MACHINE = "(mx6)"

SRCREV = "1e54cdcdb6940a58d5e09174410fe0fe28041fa3"
SRC_URI = "git://git.freescale.com/imx/linux-2.6-imx.git"

S = "${WORKDIR}/git"

PR = "r1"
