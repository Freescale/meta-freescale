DESCRIPTION = "Data Plane Development Kit Extended utilities"
HOMEPAGE = "http://dpdk.org"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE.GPL;md5=751419260aa954499f7abaabaa882bbe"

RDEPENDS_${PN} = "dpdk"

SRC_URI = "git://git.freescale.com/ppc/sdk/dpdk-extras.git;nobranch=1"
SRCREV = "ebc515aad0605ad90d8ade5c1fde60d8da86973f"

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
