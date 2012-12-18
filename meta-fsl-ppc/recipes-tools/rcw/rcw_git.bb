DESCRIPTION = "Reset Control Words (RCW)"
SECTION = "rcw"
LICENSE = "BSD"
PR = "r7"

LIC_FILES_CHKSUM = "file://rcw.py;beginline=8;endline=28;md5=9ba0b28922dd187b06b6c8ebcfdd208e"

# this package is specific to the machine itself
INHIBIT_DEFAULT_DEPS = "1"
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit deploy

SRCREV = "e9529eac512f7dc2ada3c6ff3ef1b934e5afae57"
SRCREV_e6500 = "356a8050298bad4a3c4e1237b5d8946738efe643"
SRCREV_e6500-64b = "356a8050298bad4a3c4e1237b5d8946738efe643"
SRC_URI = "git://git.freescale.com/ppc/sdk/rcw.git"

S = "${WORKDIR}/git"

do_install () {
	make install

	M=`echo ${MACHINE} | sed s/-64b//g`
	install -d ${D}/boot/rcw
	cp -r ${S}/${M}/${M}/* ${D}/boot/rcw
}

do_deploy () {
	M=`echo ${MACHINE} | sed s/-64b//g`
	install -d ${DEPLOYDIR}/rcw
	cp -r ${S}/${M}/${M}/* ${DEPLOYDIR}/rcw
}
addtask deploy after do_install

PACKAGES += "${PN}-image"
FILES_${PN}-image += "/boot"

ALLOW_EMPTY_${PN} = "1"
