DESCRIPTION = "The Management Complex (MC) is a key component of DPAA"
SECTION = "mc-utils"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=386a6287daa6504b7e7e5014ddfb3987"

DEPENDS += "dtc-native"

inherit deploy

SRC_URI = "git://source.codeaurora.org/external/qoriq/qoriq-components/mc-utils;nobranch=1"
SRCREV = "12ffee82d210cb6b5c8fa30fbc6f5d29e4be2e6f"

S = "${WORKDIR}/git"

MC_CFG ?= ""
MC_CFG:ls1088a = "ls1088a"
MC_CFG:ls2088a = "ls2088a"
MC_CFG:lx2160a = "lx2160a"
MC_CFG:lx2162a = "lx2162a"

MC_FLAVOUR ?= "${@oe.utils.ifelse(d.getVar('MACHINE').endswith('qds'), 'QDS', 'RDB')}"
MC_FOLDER ?= "${@d.getVar('MC_CFG').upper() + '-' + d.getVar('MC_FLAVOUR')}"

do_compile () {
	oe_runmake -C config 
}

do_install () {
	install -d ${D}/boot/mc-utils
	if [ -e ${S}/config/${MC_CFG}/${MC_FOLDER} ]; then
		cp -r ${S}/config/${MC_CFG}/${MC_FOLDER}/* ${D}/boot/mc-utils/
	fi
	find ${D}/boot/mc-utils/ ! -name "*.dtb" ! -type d -exec rm {} \;
}

do_deploy () {
	install -d ${DEPLOYDIR}/mc-utils
	cp -r ${D}/boot/mc-utils/* ${DEPLOYDIR}/mc-utils/
}
addtask deploy after do_install

PACKAGES += "${PN}-image"
FILES:${PN}-image += "/boot"
PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(qoriq-arm64)"
