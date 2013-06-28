DESCRIPTION = "Boot format utility for booting from eSDHC/eSPI"
LICENSE = "GPLv2"
PR = "r6"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRC_URI = "git://git.freescale.com/ppc/sdk/boot-format.git"
SRCREV = "d9bbfaba0c9316ae33455099c47bae429479e530"

S = "${WORKDIR}/git"
EXTRA_OEMAKE = 'CC="${CC}"'

do_install(){
	oe_runmake DESTDIR=${D} PREFIX=${prefix} install
}

PACKAGES =+ "${PN}-config"
FILES_${PN}-config += "${datadir}/*"

BBCLASSEXTEND = "native nativesdk"
