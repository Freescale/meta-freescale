SUMMARY = "Reset Configuration Word"
DESCRIPTION = "Reset Configuration Word - hardware boot-time parameters for the QorIQ targets"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=44a0d0fad189770cc022af4ac6262cbe"

DEPENDS += "tcl-native"

inherit deploy siteinfo

SRC_URI = "git://source.codeaurora.org/external/qoriq/qoriq-components/rcw;nobranch=1"
SRCREV = "1f431891c7f0e7d4f52327f1fe19504a284491c4"

S = "${WORKDIR}/git"

export PYTHON = "${USRBINPATH}/python3"

M="${@d.getVar('MACHINE').replace('-64b','').replace('-32b','').replace('-${SITEINFO_ENDIANNESS}','')}"

BOARD_TARGETS="${M}"
BOARD_TARGETS:ls2088ardb="${M} ${M}_rev1.1"
BOARD_TARGETS:ls1088ardb-pb="ls1088ardb"
BOARD_TARGETS:lx2160ardb = "${M} ${M}_rev2"

do_compile () {
    oe_runmake BOARDS="${BOARD_TARGETS}" DESTDIR=${D}/boot/rcw/
}

do_install () {
    oe_runmake BOARDS="${BOARD_TARGETS}" DESTDIR=${D}/boot/rcw/ install
}

do_deploy () {
    install -d ${DEPLOYDIR}/rcw
    cp -r ${D}/boot/rcw/* ${DEPLOYDIR}/rcw/
}
addtask deploy after do_install

PACKAGES += "${PN}-image"
FILES:${PN}-image += "/boot"

COMPATIBLE_MACHINE = "(qoriq)"
PACKAGE_ARCH = "${MACHINE_ARCH}"
