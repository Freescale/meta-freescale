SUMMARY = "Reset Configuration Word"
DESCRIPTION = "Reset Configuration Word - hardware boot-time parameters for the QorIQ targets"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=45a017ee5f4cfe64b1cddf2eb06cffc7"

DEPENDS += "change-file-endianess-native tcl-native"

inherit deploy siteinfo

SRC_URI = "git://source.codeaurora.org/external/qoriq/qoriq-components/rcw;nobranch=1"
SRCREV = "b73c4f6e5ad772aca5a8990f0ccc45d387ee7ac6"

S = "${WORKDIR}/git"

export PYTHON = "${USRBINPATH}/python3"

M="${@d.getVar('MACHINE').replace('-64b','').replace('-32b','').replace('-${SITEINFO_ENDIANNESS}','')}"

BOARD_TARGETS="${M}"
BOARD_TARGETS_ls2088ardb="${M} ${M}_rev1.1"
BOARD_TARGETS_ls1088ardb-pb="ls1088ardb"

do_install () {
    for BT in ${BOARD_TARGETS}
    do
        oe_runmake BOARDS=${BT} DESTDIR=${D}/boot/rcw/ install
    done
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
