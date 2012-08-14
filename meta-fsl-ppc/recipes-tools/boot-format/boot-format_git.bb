DESCRIPTION = "Boot format utility for booting from eSDHC/eSPI"
LICENSE = "GPL"
PR = "r4"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRC_URI = "git://git.freescale.com/ppc/sdk/boot-format.git\
          "
SRCREV = "fsl-sdk-v1.2.1"

S = "${WORKDIR}/git"
EXTRA_OEMAKE = 'CC="${CC}"'

do_install(){
	oe_runmake DESTDIR=${D} PREFIX=${prefix} install
}

PACKAGES =+ "${PN}-config"
FILES_${PN}-config += "${datadir}/*"

BBCLASSEXTEND = "native nativesdk"
