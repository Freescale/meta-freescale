SUMMARY = "Reset Configuration Word"
DESCRIPTION = "Reset Configuration Word - hardware boot-time parameters for the QorIQ targets"
LICENSE = "Freescale-EULA"
LIC_FILES_CHKSUM = "file://EULA;md5=c9ae442cf1f9dd6c13dfad64b0ffe73f"

inherit deploy

INHIBIT_DEFAULT_DEPS = "1"
PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = "git://git.freescale.com/ppc/sdk/ls2-rcw.git;branch=sdk-v2.0.x"
SRCREV = "74ec3bcdf33fbbedf417b800e0d486d1e8031115"

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
