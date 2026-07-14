SUMMARY = "Firmwares and Standalone Applications"
DESCRIPTION = "Inphi IN112525 PHY microcode firmware for NXP QorIQ platforms"
HOMEPAGE = "https://github.com/nxp/qoriq-firmware-inphi"
SECTION = "firmware"
LICENSE = "NXP-Binary-EULA"
LIC_FILES_CHKSUM = "file://EULA.txt;md5=86d76166990962fa552f840ff08e5798"

inherit deploy

SRC_URI = "git://github.com/nxp/qoriq-firmware-inphi.git;nobranch=1;protocol=https"
SRCREV = "f22e9ff3bfed8342da6efb699e473b11fbad5695"

do_install () {
    install -d ${D}/boot
    install -m 0644 ${S}/in112525-phy-ucode.txt ${D}/boot
}

do_deploy () {
    install -d ${DEPLOYDIR}/inphi
    cp -fr ${S}/in112525-phy-ucode.txt ${DEPLOYDIR}/inphi
}
addtask deploy before do_build after do_install

PACKAGE_ARCH = "${MACHINE_ARCH}"

PACKAGES += "${PN}-image"
FILES:${PN}-image += "/boot"

COMPATIBLE_MACHINE = "(qoriq-arm64)"
