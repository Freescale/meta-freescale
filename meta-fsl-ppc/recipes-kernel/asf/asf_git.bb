DESCRIPTION = "Non-DPAA software Application Specific Fast-path"
SECTION = "asf"
LICENSE = "GPLv2 & GPLv2+ & BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=b5881ecf398da8a03a3f4c501e29d287"

SRC_URI = "git://git.freescale.com/ppc/sdk/asf.git;nobranch=1"
SRCREV = "f107bc7dac7fe74d765dc09f66dca84951921d2c"

DEPENDS="virtual/kernel"
RDEPENDS_${PN} += "ipsec-tools"

inherit module

S = "${WORKDIR}/git/asfmodule"

EXTRA_OEMAKE = "CROSS_COMPILE=${TARGET_PREFIX}"
export KERNEL_PATH = "${STAGING_KERNEL_DIR}"

python () {
    ma = d.getVar("DISTRO_FEATURES", True)
    arch = d.getVar("OVERRIDES", True)

    # the : after the arch is to skip the message on 64b
    if not "multiarch" in ma and "e6500:" in arch:
        raise bb.parse.SkipPackage("Building the kernel for this arch requires multiarch to be in DISTRO_FEATURES")

    promote_kernel = d.getVar('BUILD_64BIT_KERNEL')

    if promote_kernel == "1":
        d.setVar('KERNEL_CC_append', ' -m64')
        d.setVar('KERNEL_LD_append', ' -melf64ppc')

    error_qa = d.getVar('ERROR_QA', True)
    if 'arch' in error_qa:
        d.setVar('ERROR_QA', error_qa.replace(' arch', ''))
}

do_install(){
	mkdir -p ${D}/usr/driver/asf
	cp -rf ${S}/bin/full ${D}/usr/driver/asf
	cp -rf ${S}/bin/min  ${D}/usr/driver/asf
	cp -rf ${S}/../scripts ${D}/usr/driver/asf/.
}

FILES_${PN} += "/usr/driver/asf"
INHIBIT_PACKAGE_STRIP = "1"
