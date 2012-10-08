require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

PROVIDES = "linux-libc-headers"
RPROVIDES_${PN}-dev = "linux-libc-headers-dev"
RPROVIDES_${PN}-dbg = "linux-libc-headers-dbg"

SRCREV = "012a4b8a404f5c89c31e2d428d5e4c9eb3a70ec7"
SRC_URI = "git://git.freescale.com/imx/linux-2.6-imx.git"

S = "${WORKDIR}/git"

PR = "r4"
