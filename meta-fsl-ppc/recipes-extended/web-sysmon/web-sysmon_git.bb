DESCRIPTION = "Web System Monitor Files"
SECTION = "web-sysmon"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=12f884d2ae1ff87c09e5b7ccc2c4ca7e"

SRCBRANCH = "sdk-v1.4.x"
SRC_URI = "git://git.freescale.com/ppc/sdk/web-sysmon.git;branch=${SRCBRANCH}"
SRCREV = "40b47611378ef5c07d98f0f691bb146ae52dcdc1"

S = "${WORKDIR}/git"

FILES_${PN} += "/"

RDEPENDS_${PN} = "lighttpd"

do_install() {
	install -d ${D}/etc
	install -m 644 ${S}/lighttpd.conf ${D}/etc
	install -d ${D}/usr/local/bin
	install -m 755 ${S}/rrd/sens_update_rrd ${D}/usr/local/bin
	cp -r ${S}/rrd ${D}/usr
}
