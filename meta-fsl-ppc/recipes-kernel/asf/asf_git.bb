DESCRIPTION = "Non-DPAA software Application Specific Fast-path"
SECTION = "asf"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=9960b017720861b8c50c8c08723e57aa"

SRC_URI = "git://git.freescale.com/ppc/sdk/asf.git"
SRCREV = "0b80c1df6a7490486d5106b28103598906793da0"

DEPENDS="virtual/kernel"

inherit module

S = "${WORKDIR}/git/asfmodule"

EXTRA_OEMAKE = "CROSS_COMPILE=${TARGET_PREFIX} KERNEL_PATH=${STAGING_KERNEL_DIR}"

do_install(){
	mkdir -p ${D}/usr/driver/asf
	cp -rf ${S}/bin/full ${D}/usr/driver/asf
	cp -rf ${S}/bin/min  ${D}/usr/driver/asf
	cp -rf ${S}/../scripts ${D}/usr/driver/asf/.
}

FILES_${PN} += "/usr/driver/asf"
INHIBIT_PACKAGE_STRIP = "1"
