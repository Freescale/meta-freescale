DESCRIPTION = "Foundation Library"
SECTION = "flib"
LICENSE = "BSD & GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=75d2f6a74299640c05ae6c69ed7a4ad6"

SRC_URI = "git://github.com/qoriq-open-source/flib.git;nobranch=1"
SRCREV = "2c9b9dc22699f7c00f24a2134c42901932a6a0b9"

S = "${WORKDIR}/git"

do_install(){
    oe_runmake install DESTDIR=${D}
}

ALLOW_EMPTY_${PN} = "1"
