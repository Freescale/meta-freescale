SUMMARY = "Firmwares and Standalone Applications"
LICENSE = "NXP-Binary-EULA"
LIC_FILES_CHKSUM = "file://NXP-EULA;md5=d969f2c93b3905d4b628787ce5f8df4b"

inherit deploy

SRC_URI = "git://github.com/nxp/qoriq-firmware-cortina.git;nobranch=1"
SRCREV = "d5b5263eb42f8897eb679a6a30793f4df6df038d"

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

COMPATIBLE_MACHINE = "(ls2080ardb|ls2088ardb|t2080|t4240)"
PACKAGE_ARCH = "${MACHINE_ARCH}"

