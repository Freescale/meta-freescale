DESCRIPTION = "Non-DPAA software Application Specific Fast-path"
SECTION = "asf"
LICENSE = "GPLv2 & GPLv2+ & BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=b5881ecf398da8a03a3f4c501e29d287"

SRC_URI = "git://git.freescale.com/ppc/sdk/asf.git;branch=sdk-v1.7.x"
SRCREV = "16eb472d6b2b34c8b605a86c469611bc8ddec1c9"

inherit module

S = "${WORKDIR}/git/asfmodule"

EXTRA_OEMAKE = "CROSS_COMPILE=${TARGET_PREFIX}"
export KERNEL_PATH = "${STAGING_KERNEL_DIR}"

INHIBIT_PACKAGE_STRIP = "1"

do_install(){
	mkdir -p ${D}/${libexecdir} 
	mkdir -p ${D}/lib/modules/${KERNEL_VERSION}/asf
	cp -rf ${S}/bin/full ${D}/lib/modules/${KERNEL_VERSION}/asf 
	cp -rf ${S}/bin/min  ${D}/lib/modules/${KERNEL_VERSION}/asf
	cp -rf ${S}/../scripts ${D}/${libexecdir}/
}

FILES_${PN} += "${libexecdir} /lib/modules/${KERNEL_VERSION}/asf"
RDEPENDS_${PN} += "ipsec-tools"
