SUMMARY = "Firmwares and Standalone Applications"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit deploy

SRC_URI = "git://github.com/qoriq-open-source/firmware-cortina.git;nobranch=1"
SRCREV = "6036a20ddbf796ac494da172f2588e5de4d833fc"

S = "${WORKDIR}/git"

do_install () {
    install -d ${D}/boot
    cp -fr ${S}/* ${D}/boot
}

do_deploy () {
    install -d ${DEPLOYDIR}/ls2-phy
    cp -fr ${S}/* ${DEPLOYDIR}/ls2-phy
}
addtask deploy before do_build after do_install

PACKAGES += "${PN}-image"
FILES_${PN}-image += "/boot"

COMPATIBLE_MACHINE = "(ls2080ardb|ls2088ardb)"
PACKAGE_ARCH = "${MACHINE_ARCH}"

