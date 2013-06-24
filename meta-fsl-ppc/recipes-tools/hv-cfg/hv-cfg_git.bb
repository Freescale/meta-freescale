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
COMPATIBLE_HOST_fslmachine = ".*"
COMPATIBLE_HOST ?= "(none)"

inherit deploy

SRCREV = "deb55b9564ef8fb9e1fda1112233f11ae2a12d4a"
SRC_URI = "git://git.freescale.com/ppc/sdk/hv-cfg.git"

S = "${WORKDIR}/git"

do_install () {
	make install

	M=`echo ${MACHINE} | sed s/-64b//g`
	install -d ${D}/boot/hv-cfg
	cp -r ${S}/${M}/${M}/* ${D}/boot/hv-cfg
}

do_deploy () {
	M=`echo ${MACHINE} | sed s/-64b//g`
	install -d ${DEPLOYDIR}/hv-cfg
	cp -r ${S}/${M}/${M}/* ${DEPLOYDIR}/hv-cfg
}
addtask deploy after do_install

PACKAGES += "${PN}-image"
FILES_${PN}-image += "/boot"

ALLOW_EMPTY_${PN} = "1"
