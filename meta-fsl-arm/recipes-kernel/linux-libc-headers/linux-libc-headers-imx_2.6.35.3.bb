require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

PROVIDES = "linux-libc-headers"
RPROVIDES_${PN}-dev = "linux-libc-headers-dev"
RPROVIDES_${PN}-dbg = "linux-libc-headers-dbg"

SRC_URI = "git://git.freescale.com/imx/linux-2.6-imx.git;tag=rel_imx_2.6.35_11.09.01"
S = "${WORKDIR}/git"

PR = "r2"
