DESCRIPTION = "Foundation Library"
SECTION = "flib"
LICENSE = "BSD & GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=75d2f6a74299640c05ae6c69ed7a4ad6"

SRC_URI = "git://git.freescale.com/ppc/sdk/flib.git;nobranch=1"
SRCREV = "91bbb134df1cd8b65c7c19db9bec9f00d1435377"

S = "${WORKDIR}/git"

do_install(){
    oe_runmake install DESTDIR=${D}
}

ALLOW_EMPTY_${PN} = "1"
