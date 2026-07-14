SUMMARY = "DDR firmware repository"
DESCRIPTION = "DDR PHY training firmware binaries for NXP LX2160A/LX2162A SoCs"
HOMEPAGE = "https://github.com/nxp/ddr-phy-binary"
SECTION = "firmware"
LICENSE = "NXP-Binary-EULA"
LIC_FILES_CHKSUM = "file://NXP-Binary-EULA.txt;md5=89cc852481956e861228286ac7430d74"

inherit deploy

DEPENDS += "qoriq-atf-tools-native"

SRC_URI = "git://github.com/nxp/ddr-phy-binary.git;nobranch=1;protocol=https"
SRCREV = "fbc036b88acb6c06ffed02c898cbae9856ec75ba"

REGLEX = "lx2160a"

do_compile() {
    cd ${S}/${REGLEX}
    fiptool create --ddr-immem-udimm-1d ddr4_pmu_train_imem.bin \
    --ddr-immem-udimm-2d ddr4_2d_pmu_train_imem.bin \
    --ddr-dmmem-udimm-1d ddr4_pmu_train_dmem.bin \
    --ddr-dmmem-udimm-2d ddr4_2d_pmu_train_dmem.bin \
    --ddr-immem-rdimm-1d ddr4_rdimm_pmu_train_imem.bin \
    --ddr-immem-rdimm-2d ddr4_rdimm2d_pmu_train_imem.bin \
    --ddr-dmmem-rdimm-1d ddr4_rdimm_pmu_train_dmem.bin \
    --ddr-dmmem-rdimm-2d ddr4_rdimm2d_pmu_train_dmem.bin \
    fip_ddr_all.bin
}

do_install () {
    install -d ${D}/boot
    install -m 755 ${S}/${REGLEX}/*.bin ${D}/boot
}

do_deploy () {
    install -d ${DEPLOYDIR}/ddr-phy
    install -m 755 ${S}/${REGLEX}/*.bin ${DEPLOYDIR}/ddr-phy
}
addtask deploy before do_populate_sysroot after do_install

PACKAGE_ARCH = "${MACHINE_ARCH}"

PACKAGES += "${PN}-image"
FILES:${PN}-image += "/boot"

COMPATIBLE_MACHINE = "(lx2160a|lx2162a)"
