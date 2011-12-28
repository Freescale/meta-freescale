DESCRIPTION = "Boot format utility for booting from eSDHC/eSPI"
LICENSE = "GPL"
PR = "r2"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRC_URI = "git://git.freescale.com/ppc/sdk/boot-format.git\
          "
SRCREV = "aca17b98516ef1869e7f2d98e865bce9226e8bba"

S = "${WORKDIR}/git"
EXTRA_OEMAKE = 'CC="${CC}"'

do_install(){
	oe_runmake DESTDIR=${D} PREFIX=${prefix} install
}

PACKAGES =+ "${PN}-config"
FILES_${PN}-config += "${datadir}/*"

BBCLASSEXTEND = "native nativesdk"
