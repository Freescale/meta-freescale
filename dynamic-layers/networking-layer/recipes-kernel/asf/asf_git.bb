DESCRIPTION = "Non-DPAA software Application Specific Fast-path"
SECTION = "asf"
LICENSE = "GPLv2 & GPLv2+ & BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=b5881ecf398da8a03a3f4c501e29d287"

SRC_URI = "git://git.freescale.com/ppc/sdk/asf.git;branch=sdk-v2.0.x"
SRCREV = "a8bbba6c2fd23bd79446c0ee62e352bfed528fb4"

RDEPENDS_${PN} += "ipsec-tools"

inherit module qoriq_build_64bit_kernel

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
    install -d ${D}/${libexecdir}
    install -d ${D}/lib/modules/${KERNEL_VERSION}/asf
    cp -rf ${S}/bin/full ${D}/lib/modules/${KERNEL_VERSION}/asf
    cp -rf ${S}/bin/min  ${D}/lib/modules/${KERNEL_VERSION}/asf
    cp -rf ${S}/../scripts ${D}/${libexecdir}/
    find ${D}/lib -depth -type d -exec rmdir --ignore-fail-on-non-empty {} \;
}

FILES_${PN} += "${libexecdir}"

COMPATIBLE_MACHINE = "(qoriq)"
PACKAGE_ARCH = "${MACHINE_SOCARCH}"

