DESCRIPTION = "DPAA2 Accelerated I/O Processing service layer"
SECTION = "dpaa2"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=faf479bdc4702d8033049f97e153f876"

BASEDEPENDS = ""

S = "${WORKDIR}/git"

SRC_URI = "git://git.freescale.com/ppc/sdk/aiopsl.git;nobranch=1"
SRCREV = "80f3d450896d17a3d8e10f3c6d1c224e78d32757"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install () {
    install -d ${D}/usr/aiop/bin
    install -d ${D}/usr/aiop/scripts
    install -d ${D}/usr/aiop/traffic_files
    install -m 755 ${S}/demos/images/LS2088A/*.elf ${D}/usr/aiop/bin
    install -m 755 ${S}/misc/setup/scripts/dynamic_aiop_*.sh ${D}/usr/aiop/scripts
    install -m 644 ${S}/misc/setup/traffic_files/classifier.pcap ${D}/usr/aiop/traffic_files
}

FILES_${PN} += "/usr/aiop/*"
INSANE_SKIP_${PN} += "arch"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
COMPATIBLE_MACHINE = "(ls2088a)"
