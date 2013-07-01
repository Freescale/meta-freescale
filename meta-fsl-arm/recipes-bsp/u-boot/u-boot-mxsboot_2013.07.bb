DESCRIPTION = "U-boot bootloader mxsboot tool"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=1707d6db1d42237583f50183a5651ecb"
SECTION = "bootloader"

PV = "v2013.07"

SRCREV = "161d75c652f3d72bc7ffbbb1e08d82d014d23bc9"
SRC_URI = "git://github.com/Freescale/u-boot-imx.git"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = 'HOSTCC="${CC}" HOSTLD="${LD}" HOSTSTRIP=true CONFIG_MX28=y'

do_compile () {
    oe_runmake tools
}

do_install () {
    install -d ${D}${bindir}
    install -m 0755 tools/mxsboot ${D}${bindir}/uboot-mxsboot
    ln -sf uboot-mxsboot ${D}${bindir}/mxsboot
}

BBCLASSEXTEND = "native nativesdk"
