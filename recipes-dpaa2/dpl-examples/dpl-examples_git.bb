SUMMARY = "Datapath layout examples"
LICENSE = "Freescale-EULA"
LIC_FILES_CHKSUM = "file://LICENSE;md5=386a6287daa6504b7e7e5014ddfb3987"

DEPENDS = "dtc-native"

inherit deploy

SRC_URI = "git://github.com/qoriq-open-source/dpl-examples.git;nobranch=1"
SRCREV = "a6c83759c0d9c02822eec89e86357a0998ef51d4"

S = "${WORKDIR}/git"

REGLEX_ls2088a = "ls2088a"
REGLEX_ls1088a = "ls1088a"

do_install () {
    install -d ${D}/boot
    install -m 644 ${S}/${REGLEX}/RDB/*.dtb ${D}/boot
    install -m 644 ${S}/${REGLEX}/RDB/custom/*.dtb ${D}/boot
}

do_deploy () {
    install -d ${DEPLOYDIR}/dpl-examples
    install -m 644 ${S}/${REGLEX}/RDB/*.dtb ${DEPLOYDIR}/dpl-examples
    install -m 644 ${S}/${REGLEX}/RDB/custom/*.dtb ${DEPLOYDIR}/dpl-examples
}
addtask deploy before do_build after do_install

PACKAGES += "${PN}-image"
FILES_${PN}-image += "/boot"

COMPATIBLE_MACHINE = "(ls2080ardb|ls2088a|ls1088a)"
PACKAGE_ARCH = "${MACHINE_ARCH}"

