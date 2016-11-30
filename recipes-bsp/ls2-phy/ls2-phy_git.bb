SUMMARY = "Firmwares and Standalone Applications"
LICENSE = "Freescale-EULA"
LIC_FILES_CHKSUM = "file://EULA;md5=c9ae442cf1f9dd6c13dfad64b0ffe73f"

inherit deploy

SRC_URI = "git://git.freescale.com/ppc/sdk/ls2-phy.git;branch=sdk-v2.0.x"
SRCREV = "554f2648c6a4c6f974827ab326e8c1908dead6f1"

S = "${WORKDIR}/git"

do_install () {
    install -d ${D}/boot
    cp -fr ${S}/AQR405 ${D}/boot
    cp -fr ${S}/CS4340 ${D}/boot
}

do_deploy () {
    install -d ${DEPLOYDIR}/ls2-phy
    cp -fr ${S}/AQR405 ${DEPLOYDIR}/ls2-phy
    cp -fr ${S}/CS4340 ${DEPLOYDIR}/ls2-phy
}
addtask deploy before do_build after do_install

PACKAGES += "${PN}-image"
FILES_${PN}-image += "/boot"

COMPATIBLE_MACHINE = "(ls2080ardb)"
PACKAGE_ARCH = "${MACHINE_ARCH}"

