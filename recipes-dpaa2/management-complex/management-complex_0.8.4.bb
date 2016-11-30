SUMMARY = "DPAA2 Management Complex Firmware"
LICENSE = "Freescale-Binary-EULA"
LIC_FILES_CHKSUM = "file://Freescale-Binary-EULA;md5=da89ad557c4a497fb005e76c2600cda3"

inherit deploy

INHIBIT_DEFAULT_DEPS = "1"

SRC_URI = "git://git.freescale.com/ppc/sdk/mc-binary.git;branch=sdk-v2.0.x"
SRCREV = "40e7cc7bfc9b35488394450605d649cb7073051a"

S = "${WORKDIR}/git"

do_install () {
    install -d ${D}/boot
    install -m 755 ${S}/ls2080a/*.itb ${D}/boot
}

do_deploy () {
    install -d ${DEPLOYDIR}/mc_app
    install -m 755 ${S}/ls2080a/*.itb ${DEPLOYDIR}/mc_app
    # make a symlink to the latest binary
    for mc_binary in `ls ${DEPLOYDIR}/mc_app |sort`;do
        ln -sfT ${mc_binary} ${DEPLOYDIR}/mc_app/mc.itb
    done
}
addtask deploy before do_build after do_install

PACKAGES += "${PN}-image"
FILES_${PN}-image += "/boot"

INHIBIT_PACKAGE_STRIP = "1"

COMPATIBLE_MACHINE = "(ls2080ardb)"
PACKAGE_ARCH = "${MACHINE_ARCH}"

