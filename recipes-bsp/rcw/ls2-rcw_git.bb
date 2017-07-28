SUMMARY = "Reset Configuration Word"
DESCRIPTION = "Reset Configuration Word - hardware boot-time parameters for the QorIQ targets"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://BSD-LICENSE;md5=627727dce58484c4bd5f9b19665d81b3"

inherit deploy siteinfo

INHIBIT_DEFAULT_DEPS = "1"
PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = "git://github.com/qoriq-open-source/rcw.git;nobranch=1"
SRCREV = "bfe8c5eb72cbae387af1c8662957caa801c2309f"

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
