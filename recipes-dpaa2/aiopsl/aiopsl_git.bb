DESCRIPTION = "DPAA2 Accelerated I/O Processing service layer"
SECTION = "dpaa2"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=faf479bdc4702d8033049f97e153f876"

BASEDEPENDS = ""

S = "${WORKDIR}/git"

SRC_URI = "git://github.com/nxp-qoriq/aiopsl;nobranch=1"
SRCREV = "87d83d8e99770325cc7ad9e10965c9959e7cb828"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

DEMOS_PATH_ls2088a = "LS2088A"
DEMOS_PATH_ls1088a = "LS1088A"

do_install () {
    install -d ${D}/usr/aiop/bin
    install -d ${D}/usr/aiop/
    cp -rf ${S}/demos/images/*  ${D}/usr/aiop/bin
    cp -rf ${S}/misc/setup/scripts ${D}/usr/aiop/
    cp -rf ${S}/misc/setup/traffic_files/ ${D}/usr/aiop/
}

FILES_${PN} += "/usr/aiop/*"
INSANE_SKIP_${PN} += "arch"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
COMPATIBLE_MACHINE = "(ls2088a|ls1088a)"
