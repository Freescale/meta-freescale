DESCRIPTION = "Data Plane Development Kit Extended utilities"
HOMEPAGE = "http://dpdk.org"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=d65993a77975d92d8125471e44ab0b21"

RDEPENDS_${PN} = "dpdk"

SRC_URI = "git://git.freescale.com/ppc/sdk/dpdk-extras.git;nobranch=1"
SRCREV = "47ee260f878d83154aeb69d9f84819ce76cc1567"

S = "${WORKDIR}/git"

DPAA_VER ?= "dpaa2"
DPAA_VER_fsl-lsch2 = "dpaa"

do_install() {
    install -d ${D}/${bindir}/dpdk-example/extras

    for file_suffix in xml sh; do
        if [ "`ls ${S}/${DPAA_VER}/*.${file_suffix}`" != "" ]; then
            install -m 755 ${S}/${DPAA_VER}/*.${file_suffix} ${D}/${bindir}/dpdk-example/extras
        fi
    done
}

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(ls2080ardb|ls2084ardb|ls2088a|ls1043a|ls1046a)"
