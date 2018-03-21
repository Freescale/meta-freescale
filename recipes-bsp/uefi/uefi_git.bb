DESCRIPTION = "Unified Extensible Firmware Interface"
SECTION = "bootloaders"
LICENSE = "NXP-Binary-EULA"
LIC_FILES_CHKSUM = "file://NXP-Binary-EULA;md5=343ec8f06efc37467a6de53686fa6315"

inherit deploy

SRC_URI = "git://github.com/NXP/qoriq-uefi-binary.git;nobranch=1"
SRCREV= "100cdac2d760be7aead1dfb24a4fa6317db9d6f7"

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

