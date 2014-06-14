DESCRIPTION = "Web System Monitor Files"
SECTION = "web-sysmon"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=12f884d2ae1ff87c09e5b7ccc2c4ca7e"

SRC_URI = "git://git.freescale.com/ppc/sdk/web-sysmon-dev.git;nobranch=1"
SRCREV = "d8fafc6f223054a4129d5623e89d3dcf42ac8147"

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
