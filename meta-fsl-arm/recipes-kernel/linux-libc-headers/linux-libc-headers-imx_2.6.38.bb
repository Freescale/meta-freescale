require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

PROVIDES = "linux-libc-headers"
RPROVIDES_${PN}-dev = "linux-libc-headers-dev"
RPROVIDES_${PN}-dbg = "linux-libc-headers-dbg"

DEFAULT_PREFERENCE_imx53qsb = "-1"
DEFAULT_PREFERENCE_imx53ard = "-1"
COMPATIBLE_MACHINE = "(imx53qsb|imx53ard|mx6)"

SRC_URI = "git://git.freescale.com/imx/linux-2.6-imx.git;tag=rel_imx_2.6.38_12.01.01"
S = "${WORKDIR}/git"
