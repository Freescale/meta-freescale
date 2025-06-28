DESCRIPTION = "Foundation Library"
SECTION = "flib"
LICENSE = "BSD-3-Clause & GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=9f6d1afdf6b0f6b3ba65c25ba589ee53"

SRC_URI = "git://github.com/nxp-qoriq/flib;protocol=https;nobranch=1"
SRCREV = "cbb31427466649c07d2ac2739a41bb42f5f6be7c"

do_install(){
    oe_runmake install DESTDIR=${D}
}

ALLOW_EMPTY:${PN} = "1"
