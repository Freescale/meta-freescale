DESCRIPTION = "qe microcode binary"
SECTION = "qe-ucode"
LICENSE = "NXP-Binary-EULA"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c62f8109b4df15ca37ceeb5e4943626c"

inherit deploy

SRC_URI = "git://github.com/NXP/qoriq-qe-ucode.git;nobranch=1;protocol=https"
SRCREV= "c89d7843943fd3bd52aa5f1ff7a91200a2f7f63b"

S = "${WORKDIR}/git"

do_install () {
       install -d ${D}/boot
       install -m 644 ${B}/*.bin ${D}/boot
}

do_deploy () {
       install -d ${DEPLOYDIR}/boot
       install -m 644 ${B}/*.bin ${DEPLOYDIR}/boot
}
addtask deploy before do_build after do_install

PACKAGES += "${PN}-image"
FILES:${PN}-image += "/boot/*"

COMPATIBLE_MACHINE = "(qoriq)"
PACKAGE_ARCH = "${MACHINE_SOCARCH}"

