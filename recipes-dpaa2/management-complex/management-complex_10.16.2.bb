SUMMARY = "DPAA2 Management Complex Firmware"
LICENSE = "NXP-Binary-EULA"
LIC_FILES_CHKSUM = "file://NXP-Binary-EULA.txt;md5=73d4b970c18882e229e4bf1ada5bb8d6"

inherit deploy fsl-eula-unpack

INHIBIT_DEFAULT_DEPS = "1"

SRC_URI = "git://github.com/nxp/qoriq-mc-binary;fsl-eula=true;nobranch=1"
SRCREV = "f72b978873af6c7cd0fd92b793363506411c102c"

S = "${WORKDIR}/git"

REGLEX_ls2088a = "ls2088a"
REGLEX_ls1088a = "ls1088a"
REGLEX_lx2160a = "lx2160a"

do_install () {
    install -d ${D}/boot
    install -m 755 ${S}/${REGLEX}/*.itb ${D}/boot
}

do_deploy () {
    install -d ${DEPLOYDIR}/mc_app
    install -m 755 ${S}/${REGLEX}/*.itb ${DEPLOYDIR}/mc_app
    # make a symlink to the latest binary
    mc_binary="$(ls ${DEPLOYDIR}/mc_app | grep -v ^mc.itb$ | sort | tail -1)"
    ln -sfT "$mc_binary" ${DEPLOYDIR}/mc_app/mc.itb
}
addtask deploy before do_build after do_install

PACKAGES += "${PN}-image"
FILES_${PN}-image += "/boot"

INHIBIT_PACKAGE_STRIP = "1"

COMPATIBLE_MACHINE = "(qoriq-arm64)"
PACKAGE_ARCH = "${MACHINE_ARCH}"

