DESCRIPTION = "Firmware image for the Cortina CS4315 PHY"
LICENSE = "Cortina"
LIC_FILES_CHKSUM = "file://Cortina-EULA;md5=ef3a0b9eaf40547d263a4f67040dc56e"

inherit deploy

SRC_URI = "git://git.freescale.com/ppc/sdk/firmware.git;nobranch=1"
SRCREV = "ad5a3108f9ede39ea41fde18d4ac0cc7680cf650"

S = "${WORKDIR}/git"

do_install () {
    install -d ${D}/boot
    install -m 644 ${S}/cs4315-cs4340-PHY-ucode.txt ${D}/boot/
}

do_deploy () {
    install -d ${DEPLOYDIR}/
    install -m 644 ${S}/cs4315-cs4340-PHY-ucode.txt ${DEPLOYDIR}/
}
addtask deploy before do_build after do_install

PACKAGES += "${PN}-image"
FILES_${PN}-image += "/boot"
COMPATIBLE_MACHINE = "(t2080rdb|t2080rdb-64b|t4240rdb|t4240rdb-64b)"
ALLOW_EMPTY_${PN} = "1"
