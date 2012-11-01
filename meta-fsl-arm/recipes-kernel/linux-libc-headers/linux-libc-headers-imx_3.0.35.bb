require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

PROVIDES = "linux-libc-headers"
RPROVIDES_${PN}-dev = "linux-libc-headers-dev"
RPROVIDES_${PN}-dbg = "linux-libc-headers-dbg"

COMPATIBLE_MACHINE = "(mx6)"

# Revision of 12.09.01 tag
SRCREV = "eaaf30efdc8dfeb03418bde1499a76c9903bd211"
SRC_URI = "git://git.freescale.com/imx/linux-2.6-imx.git"

S = "${WORKDIR}/git"
