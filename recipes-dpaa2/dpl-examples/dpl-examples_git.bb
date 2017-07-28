SUMMARY = "Datapath layout examples"
LICENSE = "Freescale-EULA"
LIC_FILES_CHKSUM = "file://LICENSE;md5=386a6287daa6504b7e7e5014ddfb3987"

DEPENDS = "dtc-native"

inherit deploy

SRC_URI = "git://github.com/qoriq-open-source/dpl-examples.git;nobranch=1"
SRCREV = "592fc2fd1f13107c8a4ae8070fed11a5f8d10471"

S = "${WORKDIR}/git"

REGLEX_ls2088a = "ls2088a"
REGLEX_ls1088a = "ls1088a"

do_install () {
    install -d ${D}/boot
    install -m 644 ${S}/${REGLEX}/RDB/*.dtb ${D}/boot
}

do_deploy () {
    install -d ${DEPLOYDIR}/dpl-examples
    install -m 644 ${S}/${REGLEX}/RDB/*.dtb ${DEPLOYDIR}/dpl-examples
}
addtask deploy before do_build after do_install

PACKAGES += "${PN}-image"
FILES_${PN}-image += "/boot"

COMPATIBLE_MACHINE = "(ls2080ardb|ls2088a|ls1088a)"
PACKAGE_ARCH = "${MACHINE_ARCH}"

