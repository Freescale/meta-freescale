DESCRIPTION = "Fman microcode binary"
SECTION = "fm-ucode"
LICENSE = "NXP-Binary-EULA"
LIC_FILES_CHKSUM = "file://LICENSE;md5=12e248d404ce1ea8bed0148fcf127e69"

PR = "r1"

inherit deploy

SRC_URI = "git://github.com/NXP/qoriq-fm-ucode.git;nobranch=1;protocol=https"
SRCREV = "41d603a1ad78e0bb61365500828d9f484bf9bf10"

S = "${WORKDIR}/git"

REGLEX ?= "${MACHINE}"
REGLEX:t1023 = "t1024"
REGLEX:t1040 = "t1040"
REGLEX:t1042 = "t1040"
REGLEX_b4420 = "b4860"
REGLEX:t4160 = "t4240"
REGLEX:ls1043a = "ls1043"
REGLEX:ls1046a = "ls1046"

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
FILES:${PN}-image += "/boot"
ALLOW_EMPTY:${PN} = "1"

COMPATIBLE_MACHINE = "(e500mc|e5500|e5500-64b|e6500|e6500-64b|fsl-lsch2)"
PACKAGE_ARCH = "${MACHINE_SOCARCH}"

