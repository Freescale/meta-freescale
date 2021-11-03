SUMMARY = "Firmwares and Standalone Applications"
LICENSE = "NXP-Binary-EULA"
LIC_FILES_CHKSUM = "file://EULA.txt;md5=86d76166990962fa552f840ff08e5798"

inherit deploy

SRC_URI = "git://github.com/nxp/qoriq-firmware-cortina.git;nobranch=1;protocol=https"
SRCREV = "9143c2a3adede595966583c00ca4edc99ec698cf"

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
FILES:${PN}-image += "/boot"

COMPATIBLE_MACHINE = "(qoriq)"
PACKAGE_ARCH = "${MACHINE_ARCH}"

