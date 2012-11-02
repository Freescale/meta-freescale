DESCRIPTION = "Boot format utility for booting from eSDHC/eSPI"
LICENSE = "GPLv2"
PR = "r6"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRC_URI = "git://git.freescale.com/ppc/sdk/boot-format.git"
SRCREV = "7a837a487eb42f30cf3ada07ca44251ee11a2940"

S = "${WORKDIR}/git"
EXTRA_OEMAKE = 'CC="${CC}"'

do_install(){
	oe_runmake DESTDIR=${D} PREFIX=${prefix} install
}

PACKAGES =+ "${PN}-config"
FILES_${PN}-config += "${datadir}/*"

BBCLASSEXTEND = "native nativesdk"
