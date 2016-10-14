SUMMARY = "Reset Configuration Word"
DESCRIPTION = "Reset Configuration Word - hardware boot-time parameters for the QorIQ targets"
LICENSE = "Freescale-Binary-EULA"
LIC_FILES_CHKSUM = "file://Freescale-Binary-EULA;md5=8835a59e50213e1b21243dd00c933e47"

inherit deploy

INHIBIT_DEFAULT_DEPS = "1"
PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = "git://git.freescale.com/ppc/sdk/ls2-rcw.git;nobranch=1"
SRCREV = "8a050e0779a9d03ea488d01cc1a31d1e2b1efdba"

S = "${WORKDIR}/git"

do_install () {
    install -d ${D}/boot/rcw
    cp -r ${S}/ls2080a/RDB/* ${D}/boot/rcw
}

do_deploy () {
    install -d ${DEPLOYDIR}/rcw
    cp -r ${S}/ls2080a/RDB/* ${DEPLOYDIR}/rcw
}
addtask deploy before do_build after do_install

PACKAGES += "${PN}-image"
FILES_${PN}-image += "/boot"
COMPATIBLE_MACHINE = "(ls2080ardb)"
