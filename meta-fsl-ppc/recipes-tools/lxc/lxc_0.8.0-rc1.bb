DESCRIPTION = "lxc aims to use these new functionnalities to provide an userspace container object"
SECTION = "console/utils"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=fbc093901857fcd118f065f900982c24"
PRIORITY = "optional"
PR = "r1"
DEPENDS = "libxml2 libcap"
RDEPENDS_${PN} = " \
		rsync \
		gzip \
		libcap-bin \
		perl-module-strict \
		perl-module-getopt-long \
		perl-module-vars \
		perl-module-warnings-register \
		perl-module-exporter \
		perl-module-constant \
		perl-module-overload \
		perl-module-exporter-heavy \
"

SRC_URI = "http://lxc.sourceforge.net/download/lxc/${PN}-${PV}.tar.gz \
	file://noldconfig.patch \
	file://libmounts.patch \
	"

SRC_URI[md5sum] ="06ceecf4dbe1be988fc903ad8dd34d29"
SRC_URI[sha256sum] = "32bf83902c07387646c55de440d6d12cf61bd54c97417109c2d1ac47d17cb911"

S = "${WORKDIR}/${PN}-${PV}"

EXTRA_OECONF = "--disable-doc --disable-rpath"

inherit autotools

FILES_${PN}-dbg += "${libexecdir}/lxc/.debug"
