DESCRIPTION = "OVS DPDK"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=17b2c9d4c70853a09c0e143137754b35"

DEPENDS = "dpdk python-six-native"
RDEPENDS_${PN} = "bash libcrypto libssl python"

inherit pythonnative

SRC_URI = "git://git.freescale.com/ppc/sdk/ovs-dpdk.git;nobranch=1"
SRCREV = "84599fad4a10597fb4377174abdeb84b871cb4b0"

S = "${WORKDIR}/git"

DPAA_VER ?= "dpaa2"
DPAA_VER_fsl-lsch2 = "dpaa"
export RTE_TARGET = "${ARCH}-${DPAA_VER}-linuxapp-gcc"

EXTRA_OEMAKE += 'ARCH="${ARCH}" CROSS="${TARGET_PREFIX}" \
    CPU_CFLAGS="--sysroot=${STAGING_DIR_HOST}" RTE_SDK="${S}" \
    OPENSSL_PATH="${STAGING_DIR_HOST}" RTE_KERNELDIR="${STAGING_KERNEL_DIR}" \
    RTE_KERNELDIR_OUT="${STAGING_KERNEL_BUILDDIR}" \
'

do_configure() {
	export SYSROOT_DPDK=${PKG_CONFIG_SYSROOT_DIR}
	${S}/boot.sh
	${S}/configure --host aarch64-fsl-linux --with-dpdk=${SYSROOT_DPDK}/usr/share/${RTE_TARGET} --with-openssl=${SYSROOT_DPDK}/usr CFLAGS="-g -Wno-cast-align -Ofast" 
}

do_compile() {
	oe_runmake O="${RTE_TARGET}" T="${RTE_TARGET}" 
}

do_install() {
	install -d ${D}${bindir}/ovs-dpdk
	cp -rf  ${S}/ovsdb/ovsdb-tool ${D}${bindir}/ovs-dpdk
	cp -rf  ${S}/ovsdb/ovsdb-server ${D}${bindir}/ovs-dpdk
	cp -rf  ${S}/vswitchd/vswitch.ovsschema ${D}${bindir}/ovs-dpdk
	cp -rf  ${S}/vswitchd/ovs-vswitchd ${D}${bindir}/ovs-dpdk
	cp -rf  ${S}/utilities/ovs-vsctl ${D}${bindir}/ovs-dpdk
	cp -rf  ${S}/utilities/ovs-ofctl ${D}${bindir}/ovs-dpdk
	chmod 777 -R ${D}${bindir}/ovs-dpdk/*
}

ALLOW_EMPTY_${PN} = "1"
INHIBIT_PACKAGE_STRIP = "1"
PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(ls2080ardb|ls2084ardb|ls2088a|ls1043a|ls1046a)"
