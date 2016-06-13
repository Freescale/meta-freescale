DESCRIPTION = "qe microcode binary"
SECTION = "qe-ucode"
LICENSE = "Freescale-EULA"
LIC_FILES_CHKSUM = "file://EULA;md5=c9ae442cf1f9dd6c13dfad64b0ffe73f"

inherit deploy

SRC_URI = "git://git.freescale.com/ppc/sdk/qe-ucode.git;branch=sdk-v2.0.x"
SRCREV= "a95f42aef152fc613f11099d7e7bc25b44c09836"

S = "${WORKDIR}/git"

python () {
	if not d.getVar("QE_UCODE", True):
		PN = d.getVar("PN", True)
		FILE = os.path.basename(d.getVar("FILE", True))
		bb.debug(1, "To build %s, see %s for instructions on \
			     setting up your qe-ucode" % (PN, FILE))
		raise bb.parse.SkipPackage("because QE_UCODE is not set")
}

do_install () {
       install -d ${D}/boot
       install -m 644 ${QE_UCODE} ${D}/boot
}

do_deploy () {
       install -d ${DEPLOYDIR}/boot
       install -m 644 ${QE_UCODE} ${DEPLOYDIR}/boot
}
addtask deploy before do_build after do_install

PACKAGES += "${PN}-image"
FILES_${PN}-image += "/boot/*"
COMPATIBLE_MACHINE = "(ls1021a|ls1043a|t1)"
