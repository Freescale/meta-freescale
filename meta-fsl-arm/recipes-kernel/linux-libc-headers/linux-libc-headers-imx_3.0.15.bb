require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

PROVIDES = "linux-libc-headers"
RPROVIDES_${PN}-dev = "linux-libc-headers-dev"
RPROVIDES_${PN}-dbg = "linux-libc-headers-dbg"

COMPATIBLE_MACHINE = "(imx6qsabrelite)"

SRC_URI = "git://git.freescale.com/imx/linux-2.6-imx.git;tag=rel_imx_3.0.15_12.02.01"
S = "${WORKDIR}/git"
