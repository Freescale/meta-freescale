DESCRIPTION = "Fman microcode binary"
SECTION = "fm-ucode"
LICENSE = "Freescale-EULA"
LIC_FILES_CHKSUM = "file://EULA;md5=60037ccba533a5995e8d1a838d85799c"

PR = "r1"

inherit deploy

SRC_URI = "git://git.freescale.com/ppc/sdk/fm-ucode.git;branch=sdk-v1.9.x"
SRCREV = "33c6c7a4ba1d057afd739bd0106471ffb3299e0e"

S = "${WORKDIR}/git"

REGLEX ?= "${MACHINE}"
REGLEX_t1023 = "t1024"
REGLEX_t1040 = "t1040"
REGLEX_t1042 = "t1040"
REGLEX_b4420 = "b4860"
REGLEX_t4160 = "t4240"

do_install () {
    UCODE=`echo ${REGLEX} | sed -e 's,-.*$,,' -e 's,[a-zA-Z]*$,,'`
    install -d ${D}/boot
    install -m 644 fsl_fman_ucode_${UCODE}*.bin ${D}/boot/
}

do_deploy () {
    UCODE=`echo ${REGLEX} | sed -e 's,-.*$,,' -e 's,[a-zA-Z]*$,,'`
    install -d ${DEPLOYDIR}/
    install -m 644 fsl_fman_ucode_${UCODE}*.bin ${DEPLOYDIR}/
}
addtask deploy before do_build after do_install

PACKAGES += "${PN}-image"
FILES_${PN}-image += "/boot"
ALLOW_EMPTY_${PN} = "1"
COMPATIBLE_MACHINE = "(p1023rdb|e500mc|e5500|e5500-64b|e6500|e6500-64b)"
