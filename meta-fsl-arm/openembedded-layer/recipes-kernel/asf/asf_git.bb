DESCRIPTION = "Non-DPAA software Application Specific Fast-path"
SECTION = "asf"
LICENSE = "GPLv2 & GPLv2+ & BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=b5881ecf398da8a03a3f4c501e29d287"

SRC_URI = "git://git.freescale.com/ppc/sdk/asf.git;branch=master"
SRCREV = "62ba10ab6bab8f8f53934e4bb55b5a552bf3fd6b"

inherit module

S = "${WORKDIR}/git/asfmodule"

EXTRA_OEMAKE = "CROSS_COMPILE=${TARGET_PREFIX}"
export KERNEL_PATH = "${STAGING_KERNEL_DIR}"

INHIBIT_PACKAGE_STRIP = "1"

do_configure[depends] += "virtual/kernel:do_shared_workdir"
do_configure_prepend () {
    find ${S} -name Makefile -exec \
        sed -i 's,$(KERNEL_PATH)/.config,$(KBUILD_OUTPUT)/.config,' {} \;
}

do_install(){
	mkdir -p ${D}/${libexecdir} 
	mkdir -p ${D}/lib/modules/${KERNEL_VERSION}/asf
	cp -rf ${S}/bin/full ${D}/lib/modules/${KERNEL_VERSION}/asf 
	cp -rf ${S}/bin/min  ${D}/lib/modules/${KERNEL_VERSION}/asf
	cp -rf ${S}/../scripts ${D}/${libexecdir}/
}

FILES_${PN} += "${libexecdir} /lib/modules/${KERNEL_VERSION}/asf"
RDEPENDS_${PN} += "ipsec-tools"
