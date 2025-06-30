DESCRIPTION = "Boot format utility for booting from eSDHC/eSPI"
LICENSE = "GPL-2.0-only"
PR = "r6"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRC_URI = "git://github.com/nxp-qoriq-yocto-sdk/boot-format;protocol=https;nobranch=1 \
           file://flags.patch"
SRCREV = "4eb81a6797ef4e58bf7d9b2d58afb37a21c1f550"

EXTRA_OEMAKE = 'CC="${CC}" CFLAGS="${CFLAGS}" LDFLAGS="${LDFLAGS}"'

do_install(){
	oe_runmake DESTDIR=${D} PREFIX=${prefix} install
}

PACKAGES =+ "${PN}-config"
FILES:${PN}-config += "${datadir}/*"

BBCLASSEXTEND = "native nativesdk"
