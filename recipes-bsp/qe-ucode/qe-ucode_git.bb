DESCRIPTION = "qe microcode binary"
SECTION = "qe-ucode"
LICENSE = "Freescale-Binary-EULA"
LIC_FILES_CHKSUM = "file://Freescale-Binary-EULA;md5=f4719d59382b5ee2a2ebe4db784843a6"

inherit deploy

SRC_URI = "git://git.freescale.com/ppc/sdk/qe-ucode.git;branch=sdk-v2.0.x"
SRCREV= "adb1560b76090a11f61a46b7a6b3b33b1436ed4d"

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
       install -m 644 ${B}/${QE_UCODE} ${D}/boot
}

do_deploy () {
       install -d ${DEPLOYDIR}/boot
       install -m 644 ${B}/${QE_UCODE} ${DEPLOYDIR}/boot
}
addtask deploy before do_build after do_install

PACKAGES += "${PN}-image"
FILES_${PN}-image += "/boot/*"

COMPATIBLE_MACHINE = "(ls1021a|ls1043a|t1)"
PACKAGE_ARCH = "${MACHINE_SOCARCH}"

