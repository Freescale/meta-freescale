SUMMARY = "Reset Configuration Word"
DESCRIPTION = "Reset Configuration Word - hardware boot-time parameters for the QorIQ targets"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=45a017ee5f4cfe64b1cddf2eb06cffc7"

DEPENDS += "change-file-endianess-native tcl-native"

inherit deploy siteinfo

SRC_URI = "git://source.codeaurora.org/external/qoriq/qoriq-components/rcw;nobranch=1"
SRCREV = "f1377876cc06a87ec8afa8a3412ca7c8455861f2"

S = "${WORKDIR}/git"

export PYTHON = "${USRBINPATH}/python2"

M="${@d.getVar('MACHINE').replace('-64b','').replace('-32b','').replace('-${SITEINFO_ENDIANNESS}','')}"

do_install () {
    if [ ${M} = ls2088ardb ]; then
        oe_runmake BOARDS=${M} DESTDIR=${D}/boot/rcw/ install
        oe_runmake BOARDS=${M}_rev1.1  DESTDIR=${D}/boot/rcw/ install
    elif [ ${M} = ls1088ardb-pb ]; then
        oe_runmake BOARDS=ls1088ardb DESTDIR=${D}/boot/rcw/ install
    else
        oe_runmake BOARDS=${M} DESTDIR=${D}/boot/rcw/ install
    fi
}

do_deploy () {
    install -d ${DEPLOYDIR}/rcw
    cp -r ${D}/boot/rcw/* ${DEPLOYDIR}/rcw/
}
addtask deploy after do_install

PACKAGES += "${PN}-image"
FILES_${PN}-image += "/boot"

COMPATIBLE_MACHINE = "(qoriq)"
PACKAGE_ARCH = "${MACHINE_ARCH}"
