DESCRIPTION = "Fman microcode binary"
SECTION = "fm-ucode"
LICENSE = "Freescale EULA"
LIC_FILES_CHKSUM = "file://EULA;md5=60037ccba533a5995e8d1a838d85799c"

FMAN_UCODE_INSTALL_FILE = "fsl_fman_ucode"
FMAN_UCODE_INSTALL_FILE_append_p2041rdb = "_P2041"
FMAN_UCODE_INSTALL_FILE_append_p4080ds= "_P4080"
FMAN_UCODE_INSTALL_FILE_append_p1023rds = "_*1023"
FMAN_UCODE_INSTALL_FILE_append_p5020ds = "_P5020"
FMAN_UCODE_INSTALL_FILE_append_p5020ds-64b = "_P5020"
FMAN_UCODE_INSTALL_FILE_append_p5040ds = "_P5040"
FMAN_UCODE_INSTALL_FILE_append_p5040ds-64b = "_P5040"
FMAN_UCODE_INSTALL_FILE_append_p3041ds = "_P3041"
FMAN_UCODE_INSTALL_FILE_append_b4420qds = "_B4860"
FMAN_UCODE_INSTALL_FILE_append_b4420qds-64b = "_B4860"
FMAN_UCODE_INSTALL_FILE_append_b4860qds = "_B4860"
FMAN_UCODE_INSTALL_FILE_append_b4860qds-64b = "_B4860"
FMAN_UCODE_INSTALL_FILE_append_t4160qds = "_T4240"
FMAN_UCODE_INSTALL_FILE_append_t4160qds-64b = "_T4240"
FMAN_UCODE_INSTALL_FILE_append_t4240qds = "_T4240"
FMAN_UCODE_INSTALL_FILE_append_t4240qds-64b = "_T4240"

COMPATIBLE_MACHINE = "(p1023rds|p4080ds|p5020ds|p5040ds|p5020ds-64b|p5040ds-64b|p3041ds|p2041rdb|b4420qds|b4420qds-64b|b4860qds|b4860qds-64b|t4160qds|t4160qds-64b|t4240qds|t4240qds-64b)"
inherit deploy

SRC_URI = "git://git.freescale.com/ppc/sdk/fm-ucode.git"
SRCREV = "f4a43e8ad6a6e5fdedff6e1484386f963bccfdff"

S = "${WORKDIR}/git"

ALLOW_EMPTY_${PN} = "1"
do_install () {
	install -d ${D}/boot
	install -m 644 ${FMAN_UCODE_INSTALL_FILE}*.bin ${D}/boot/
}

do_deploy () {
	install -d ${DEPLOYDIR}/
	install -m 644 ${FMAN_UCODE_INSTALL_FILE}*.bin ${DEPLOYDIR}/
}
addtask deploy before do_build after do_install

PACKAGES += "${PN}-image"
FILES_${PN}-image += "/boot"

