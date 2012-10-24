require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

PROVIDES = "linux-libc-headers"
RPROVIDES_${PN}-dev = "linux-libc-headers-dev"
RPROVIDES_${PN}-dbg = "linux-libc-headers-dbg"

# Revision of imx_2.6.35_10.12.01 branch
SRCREV_mxs = "0ea8cb9453379388f870f9b8d13269fb9dc0761c"

# Revision of imx_2.6.35_11.09.01 branch
SRCREV_mx5 = "bc8159027063aded47316b669b1293998337b2ee"
SRC_URI = "git://git.freescale.com/imx/linux-2.6-imx.git"

S = "${WORKDIR}/git"

PR = "r6"
