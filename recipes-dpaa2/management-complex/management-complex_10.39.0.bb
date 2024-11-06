SUMMARY = "DPAA2 Management Complex Firmware"
LICENSE = "NXP-Binary-EULA"
LIC_FILES_CHKSUM = "file://LICENSE;md5=0701845051a61f6012009d7d6d11b32b"

inherit deploy

INHIBIT_DEFAULT_DEPS = "1"

SRC_URI = "git://github.com/nxp/qoriq-mc-binary;protocol=https;nobranch=1"
SRCREV = "7d82686272f8a60b803818bbc7c5396819ee3b06"

S = "${WORKDIR}/git"

REGLEX:ls2088a = "ls2088a"
REGLEX:ls2080a = "ls2080a"
REGLEX:ls1088a = "ls1088a"
REGLEX:lx2160a = "lx216xa"
REGLEX:lx2162a = "lx216xa"

do_install () {
    install -d ${D}/boot
    install -m 755 ${S}/${REGLEX}/*.itb ${D}/boot
}

do_deploy () {
    install -d ${DEPLOYDIR}/mc_app
    install -m 755 ${S}/${REGLEX}/*.itb ${DEPLOYDIR}/mc_app
    # make a symlink to the latest binary
    for mc_binary in `find ${DEPLOYDIR}/mc_app -type f -printf "%f\n" |sort`;do
        ln -sfT ${mc_binary} ${DEPLOYDIR}/mc_app/mc.itb
    done
}
addtask deploy before do_build after do_install

PACKAGES += "${PN}-image"
FILES:${PN}-image += "/boot"

INHIBIT_PACKAGE_STRIP = "1"

COMPATIBLE_MACHINE = "(qoriq-arm64)"
PACKAGE_ARCH = "${MACHINE_ARCH}"
