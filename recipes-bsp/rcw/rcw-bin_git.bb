SUMMARY = "Reset Configuration Word"
DESCRIPTION = "Reset Configuration Word - hardware boot-time parameters for the QorIQ targets"
LICENSE = "NXP-Binary-EULA"
LIC_FILES_CHKSUM = "file://NXP-Binary-EULA;md5=ba5ff3a3faf52a38970d0a536ef42df5"

inherit deploy siteinfo

SRC_URI = "git://github.com/qoriq-open-source/rcw-bin.git;nobranch=1"
SRCREV = "bf3ae3cb15829876007c553509501cdaa16745e7"

S = "${WORKDIR}/git"

M="${@d.getVar('MACHINE', True).replace('-64b','').replace('-32b','').replace('-${SITEINFO_ENDIANNESS}','')}"

do_install () {
    install -d ${D}/boot/rcw
    cp -rf ${S}/${M}* ${D}/boot/rcw/
    chown -R root:root ${D}
}

do_deploy () {
    install -d ${DEPLOYDIR}/rcw
    cp -rf ${S}/${M}* ${DEPLOYDIR}/rcw/
}
addtask deploy before do_build after do_install

PACKAGES += "${PN}-image"
FILES_${PN}-image += "/boot"

COMPATIBLE_MACHINE = "(qoriq)"
PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(ls1012a|ls2088a|ls1088a)"
