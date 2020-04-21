DESCRIPTION = "Unified Extensible Firmware Interface"
SECTION = "bootloaders"
LICENSE = "NXP-Binary-EULA"
LIC_FILES_CHKSUM = "file://NXP-Binary-EULA;md5=343ec8f06efc37467a6de53686fa6315"

inherit deploy

SRC_URI = "git://github.com/NXP/qoriq-uefi-binary.git;nobranch=1"
SRCREV= "e95ed52322f15437f98dee2b27de45a7495d648c"

S = "${WORKDIR}/git"

do_install () {
       if [ -d ${B}/${MACHINE} ]; then
           install -d ${D}/uefi
           cp -r  ${B}/grub ${D}/uefi
           cp -r  ${B}/${MACHINE} ${D}/uefi
       fi
}

do_deploy () {
       if [ -d ${B}/${MACHINE} ]; then
           install -d ${DEPLOYDIR}/uefi
           cp -r  ${B}/grub   ${DEPLOYDIR}/uefi
           cp -r  ${B}/${MACHINE} ${DEPLOYDIR}/uefi
       fi
}
addtask deploy before do_build after do_install

PACKAGES += "${PN}-image"
FILES_${PN}-image += "/uefi/*"

PACKAGE_ARCH = "${MACHINE_SOCARCH}"

COMPATIBLE_MACHINE = "(qoriq)"
