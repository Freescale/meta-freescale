DESCRIPTION = "Foundation Library"
SECTION = "flib"
LICENSE = "BSD & GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=75d2f6a74299640c05ae6c69ed7a4ad6"

SRC_URI = "git://git.freescale.com/ppc/sdk/flib.git;nobranch=1"
SRCREV = "4bd48d4d6dbb1bd57c3c608fe66e97f4eb8e05b9"

S = "${WORKDIR}/git"

do_install(){
    oe_runmake install DESTDIR=${D}
}

ALLOW_EMPTY_${PN} = "1"
