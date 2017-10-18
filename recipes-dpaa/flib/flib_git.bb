DESCRIPTION = "Foundation Library"
SECTION = "flib"
LICENSE = "BSD & GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=75d2f6a74299640c05ae6c69ed7a4ad6"

SRC_URI = "git://github.com/qoriq-open-source/flib.git;nobranch=1"
SRCREV = "14489ee2207650ddc89890df4b9f600da9ad665c"

S = "${WORKDIR}/git"

do_install(){
    oe_runmake install DESTDIR=${D}
}

ALLOW_EMPTY_${PN} = "1"
