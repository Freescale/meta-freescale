DESCRIPTION = "Unified Extensible Firmware Interface"
SECTION = "bootloaders"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690"

inherit deploy

SRC_URI = "git://github.com/qoriq-open-source/uefi-binary.git;nobranch=1"
SRCREV= "d0974b734d0c50c99baebf54957ce32433905e40"

S = "${WORKDIR}/git"

do_install () {
       install -d ${D}/uefi
       cp -r  ${B}/grub ${D}/uefi
       cp -r  ${B}/${MACHINE} ${D}/uefi
}

do_deploy () {
       install -d ${DEPLOYDIR}/uefi
       cp -r  ${B}/grub   ${DEPLOYDIR}/uefi
       cp -r  ${B}/${MACHINE} ${DEPLOYDIR}/uefi
}
addtask deploy before do_build after do_install

PACKAGES += "${PN}-image"
FILES_${PN}-image += "/uefi/*"

PACKAGE_ARCH = "${MACHINE_SOCARCH}"

