DESCRIPTION = "qe microcode binary"
SECTION = "qe-ucode"
LICENSE = "Freescale-EULA"
LIC_FILES_CHKSUM = "file://EULA;md5=60037ccba533a5995e8d1a838d85799c"

python () {
    if not d.getVar("QE_UCODE", True):
        machine = d.getVar("MACHINE", True)
        raise bb.parse.SkipPackage("QE_UCODE not set in \
            meta-fsl-ppc/conf/machine/%s.conf" % machine)
}

inherit deploy

SRC_URI = "git://git.freescale.com/ppc/sdk/qe-ucode.git;nobranch=1"
SRCREV= "49efc94b553de5c2a9bd28093592eff0068e161c"

S = "${WORKDIR}/git"

do_install () {
    install -d ${D}/boot
    install -m 644 ${QE_UCODE} ${D}/boot/
}

do_deploy () {
    install -d ${DEPLOYDIR}/boot
    install -m 644 ${QE_UCODE} ${DEPLOYDIR}/boot/
}
addtask deploy before do_build after do_install

PACKAGES += "${PN}-image"
FILES_${PN}-image += "/boot/*"
ALLOW_EMPTY_${PN} = "1"
COMPATIBLE_MACHINE = "(p1021rdb|p1025twr|t1)"

