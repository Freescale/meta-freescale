DESCRIPTION = "Foundation Library"
SECTION = "flib"
LICENSE = "BSD & GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=3f16fa8e677e45af3127c5c4bafc3c00"

SRC_URI = "git://git.freescale.com/ppc/sdk/flib.git;nobranch=1"
SRCREV = "6918b5d4ff24a5e7ab4803c849570d45f359a8d7"

S = "${WORKDIR}/git"

do_install(){
    oe_runmake install DESTDIR=${D}
}

ALLOW_EMPTY_${PN} = "1"
