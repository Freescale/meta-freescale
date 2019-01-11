SUMMARY = "DDR firmware repository"
LICENSE = "NXP-Binary-EULA"
LIC_FILES_CHKSUM = "file://NXP-Binary-EULA.txt;md5=89cc852481956e861228286ac7430d74"

inherit deploy fsl-eula-unpack

SRC_URI = "git://github.com/nxp/ddr-phy-binary.git;fsl-eula=true;nobranch=1"
SRCREV = "14d03e6e748ed5ebb9440f264bb374f1280b061c"

S = "${WORKDIR}/git"

REGLEX_lx2160a = "lx2160a"

do_install () {
    install -d ${D}/boot
    install -m 755 ${S}/${REGLEX}/* ${D}/boot
}

do_deploy () {
    install -d ${DEPLOYDIR}/ddr-phy
    install -m 755 ${S}/${REGLEX}/* ${DEPLOYDIR}/ddr-phy
}
addtask deploy before do_populate_sysroot after do_install

PACKAGES += "${PN}-image"
FILES_${PN}-image += "/boot"

COMPATIBLE_MACHINE = "(lx2160a)"
PACKAGE_ARCH = "${MACHINE_ARCH}"
