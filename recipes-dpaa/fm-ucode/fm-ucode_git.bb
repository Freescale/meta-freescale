DESCRIPTION = "Fman microcode binary"
SECTION = "fm-ucode"
LICENSE = "Freescale-Binary-EULA"
LIC_FILES_CHKSUM = "file://Freescale-Binary-EULA;md5=b784c031868ba1bd5ebc5de372c823fa"

PR = "r1"

inherit deploy

SRC_URI = "git://git.freescale.com/ppc/sdk/fm-ucode.git;nobranch=1"
SRCREV = "c275e91392e2adab1ed22f3867b8269ca3c54014"

S = "${WORKDIR}/git"

REGLEX ?= "${MACHINE}"
REGLEX_t1023 = "t1024"
REGLEX_t1040 = "t1040"
REGLEX_t1042 = "t1040"
REGLEX_b4420 = "b4860"
REGLEX_t4160 = "t4240"
REGLEX_ls1043a = "ls1043"
REGLEX_ls1046a = "ls1046"

do_install () {
    UCODE=`echo ${REGLEX} | sed -e 's,-.*$,,' -e 's,[a-zA-Z]*$,,'`
    install -d ${D}/boot
    install -m 644 ${B}/fsl_fman_ucode_${UCODE}*.bin ${D}/boot/
}

do_deploy () {
    UCODE=`echo ${REGLEX} | sed -e 's,-.*$,,' -e 's,[a-zA-Z]*$,,'`
    install -d ${DEPLOYDIR}/
    install -m 644 ${B}/fsl_fman_ucode_${UCODE}*.bin ${DEPLOYDIR}
}
addtask deploy before do_build after do_install

PACKAGES += "${PN}-image"
FILES_${PN}-image += "/boot"
ALLOW_EMPTY_${PN} = "1"

COMPATIBLE_MACHINE = "(e500mc|e5500|e5500-64b|e6500|e6500-64b|fsl-lsch2)"
PACKAGE_ARCH = "${MACHINE_SOCARCH}"

