DESCRIPTION = "qe microcode binary"
SECTION = "qe-ucode"
LICENSE = "Freescale-EULA"
LIC_FILES_CHKSUM = "file://EULA;md5=60037ccba533a5995e8d1a838d85799c"

inherit deploy

SRC_URI = "git://git.freescale.com/ppc/sdk/qe-ucode.git;nobranch=1"
SRCREV= "10bef345397ba303cca0e900ec1acc09cf05928e"

S = "${WORKDIR}/git"

do_install () {
	case ${MACHINE} in
	    p1025rdb|p1021rdb|p1025twr) QE_UCODE="fsl_qe_ucode_1021_10_A.bin";;
	    t1040rdb|t1040rdb-64b) QE_UCODE="iram_Type_A_T1040_r1.0.bin";;
	    *) QE_UCODE="";;
	esac
	install -d ${D}/
	install -m 644 ${QE_UCODE} ${D}/
}

do_deploy () {
	case ${MACHINE} in
	    p1025rdb|p1021rdb|p1025twr) QE_UCODE="fsl_qe_ucode_1021_10_A.bin";;
	    t1040rdb|t1040rdb-64b) QE_UCODE="iram_Type_A_T1040_r1.0.bin";;
	    *) QE_UCODE="";;
	esac
	install -d ${DEPLOYDIR}/
	install -m 644 ${QE_UCODE} ${DEPLOYDIR}/
}
addtask deploy before do_build after do_install

PACKAGES += "${PN}-image"
FILES_${PN}-image += "/*"
ALLOW_EMPTY_${PN} = "1"
COMPATIBLE_MACHINE = "(p1025rdb|p1021rdb|p1025twr|t1040rdb|t1040rdb-64b)"

