DESCRIPTION = "The Management Complex (MC) is a key component of DPAA"
SECTION = "mc-utils"
LICENSE = "BSD"

LIC_FILES_CHKSUM = "file://LICENSE;md5=386a6287daa6504b7e7e5014ddfb3987 \
"

DEPENDS += "dtc-native"

inherit deploy

SRC_URI = "git://github.com/nxp-qoriq/qoriq-components/mc-utils;nobranch=1"
SRCREV = "8e0b863693fc2ccbc62a62c79b4e3db6da88c16e"

S = "${WORKDIR}/git"

MC_CFG ?= ""
MC_CFG_ls1088a = "ls1088a"
MC_CFG_ls2088a = "ls2088a"
MC_CFG_lx2160a = "lx2160a"
MC_CFG_lx2162aqds = "lx2162aqds"

MC_FLAVOUR ?= "RDB"
MC_FLAVOUR_lx2162a = ""

do_compile () {
	oe_runmake -C config 
}

do_install () {
	install -d ${D}/boot/mc-utils
	cp -r ${S}/config/${MC_CFG}/${MC_FLAVOUR}/*.dtb ${D}/boot/mc-utils
	if [ -d ${S}/config/${MC_CFG}/${MC_FLAVOUR}/custom/ ]; then
		install -d ${D}/boot/mc-utils/custom
		cp -r ${S}/config/${MC_CFG}/${MC_FLAVOUR}/custom/*.dtb ${D}/boot/mc-utils/custom
	fi
}

do_deploy () {
	install -d ${DEPLOYDIR}/mc-utils
	cp -r ${S}/config/${MC_CFG}/${MC_FLAVOUR}/*.dtb ${DEPLOYDIR}/mc-utils
	if [ -d ${S}/config/${MC_CFG}/${MC_FLAVOUR}/custom/ ]; then
		install -d ${DEPLOYDIR}/mc-utils/custom
		cp -r ${S}/config/${MC_CFG}/${MC_FLAVOUR}/custom/*.dtb ${DEPLOYDIR}/mc-utils/custom
	fi
}
addtask deploy after do_install

PACKAGES += "${PN}-image"
FILES_${PN}-image += "/boot"
COMPATIBLE_MACHINE = "(qoriq-arm64)"
