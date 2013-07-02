DESCRIPTION = "Fman microcode binary"
SECTION = "fm-ucode"
LICENSE = "Freescale-EULA"
LIC_FILES_CHKSUM = "file://EULA;md5=60037ccba533a5995e8d1a838d85799c"

PR = "r1"

COMPATIBLE_MACHINE = "(p1023rdb|p2041rdb|p3041ds|p4080ds|p5020ds|p5040ds|p5020ds-64b|p5040ds-64b|b4420qds|b4420qds-64b|b4860qds|b4860qds-64b|t4160qds|t4160qds-64b|t4240qds|t4240qds-64b)"
inherit deploy

SRC_URI = "git://git.freescale.com/ppc/sdk/fm-ucode.git"
SRCREV = "4ca0e41de225b34962e1517c2c75bbb31a381e1a"

S = "${WORKDIR}/git"

ALLOW_EMPTY_${PN} = "1"
do_install () {
    case ${MACHINE} in
        b4420qds|b4420qds-64b|b4860qds|b4860qds-64b) UCODE=b4860qds;;
        t4240qds|t4240qds-64b|t4160qds|t4160qds-64b) UCODE=t4240qds;;
        p5020ds|p5020ds-64b) UCODE=p5020ds;;
        p5040ds|p5040ds-64b) UCODE=p5040ds;;
        *) UCODE=${MACHINE};;
    esac
    UCODE=`echo $UCODE | sed -e 's,[a-zA-Z]*$,,'`
    install -d ${D}/boot
    install -m 644 fsl_fman_ucode_${UCODE}*.bin ${D}/boot/
}

do_deploy () {
    case ${MACHINE} in
        b4420qds|b4420qds-64b|b4860qds|b4860qds-64b) UCODE=b4860qds;;
        t4240qds|t4240qds-64b|t4160qds|t4160qds-64b) UCODE=t4240qds;;
        p5020ds|p5020ds-64b) UCODE=p5020ds;;
        p5040ds|p5040ds-64b) UCODE=p5040ds;;
        *) UCODE=${MACHINE};;
    esac
    UCODE=`echo $UCODE | sed -e 's,[a-zA-Z]*$,,'`
    install -d ${DEPLOYDIR}/
    install -m 644 fsl_fman_ucode_${UCODE}*.bin ${DEPLOYDIR}/
}
addtask deploy before do_build after do_install

PACKAGES += "${PN}-image"
FILES_${PN}-image += "/boot"

