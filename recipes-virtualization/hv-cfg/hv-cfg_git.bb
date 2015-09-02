DESCRIPTION = "Hypervisor Config"
SECTION = "hv-cfg"
LICENSE = "BSD"
PR = "r6"

LIC_FILES_CHKSUM = " \
	file://p2041rdb/LICENSE;md5=96dd72f26e9bb861de5c76c60e35e1bc \
	file://p3041ds/LICENSE;md5=96dd72f26e9bb861de5c76c60e35e1bc \
	file://p4080ds/LICENSE;md5=96dd72f26e9bb861de5c76c60e35e1bc \
	file://p5020ds/LICENSE;md5=96dd72f26e9bb861de5c76c60e35e1bc \
"

DEPENDS += "dtc-native"

# this package is specific to the machine itself
INHIBIT_DEFAULT_DEPS = "1"
PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit deploy

SRC_URI = "git://git.freescale.com/ppc/sdk/hv-cfg.git;nobranch=1"
SRCREV = "b9287b07390d17bfba936a806a72b91b89507c22"

S = "${WORKDIR}/git"

python () {
    if not d.getVar("HV_CFG_M", True):
        raise bb.parse.SkipPackage("HV_CFG_M is not defined, please \
check ${MACHINE}.conf file.")
}

do_install () {
	make install

	install -d ${D}/boot/hv-cfg
	cp -r ${S}/${HV_CFG_M}/${HV_CFG_M}/* ${D}/boot/hv-cfg
}

do_deploy () {
	install -d ${DEPLOYDIR}/hv-cfg
	cp -r ${S}/${HV_CFG_M}/${HV_CFG_M}/* ${DEPLOYDIR}/hv-cfg
}
addtask deploy after do_install

PACKAGES += "${PN}-image"
FILES_${PN}-image += "/boot"

COMPATIBLE_HOST_qoriq-ppc = ".*"
COMPATIBLE_HOST ?= "(none)"
ALLOW_EMPTY_${PN} = "1"
