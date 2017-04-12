SUMMARY = "Reset Configuration Word"
DESCRIPTION = "Reset Configuration Word - hardware boot-time parameters for the QorIQ targets"
LICENSE = "Freescale-Binary-EULA"
LIC_FILES_CHKSUM = "file://Freescale-Binary-EULA;md5=8835a59e50213e1b21243dd00c933e47"

inherit deploy siteinfo

INHIBIT_DEFAULT_DEPS = "1"
PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = "git://git.freescale.com/ppc/sdk/ls2-rcw.git;nobranch=1"
SRCREV = "5d4624f74ad2744160060a006b7b5a6359d69142"

S = "${WORKDIR}/git"

M="${@d.getVar('MACHINE', True).replace('-64b','').replace('-32b','').replace('-${SITEINFO_ENDIANNESS}','')}"

do_install () {
    install -d ${D}/boot/rcw
    cp -a ${S}/${M} ${D}/boot/rcw/
    chown -R root:root ${D}
}

do_deploy () {
    install -d ${DEPLOYDIR}/rcw
    cp -a ${S}/${M} ${DEPLOYDIR}/rcw/
}
addtask deploy before do_build after do_install

PACKAGES += "${PN}-image"
FILES_${PN}-image += "/boot"
COMPATIBLE_MACHINE = "(ls1012a|ls2080a|ls2088a)"
