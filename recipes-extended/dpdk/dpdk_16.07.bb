DESCRIPTION = "Data Plane Development Kit"
HOMEPAGE = "http://dpdk.org"
LICENSE = "BSD & LGPLv2 & GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE.GPL;md5=751419260aa954499f7abaabaa882bbe"

DEPENDS += "virtual/kernel openssl"
RDEPENDS_${PN} = "bash python"
RDEPENDS_${PN}-examples = "bash python-core"

inherit module

SRC_URI = "git://source.codeaurora.org/external/qoriq/qoriq-components/dpdk;nobranch=1 \
    file://add-RTE_KERNELDIR_OUT-to-split-kernel-bu.patch \
    file://0001-include-sys-sysmacros.h-for-major-minor-defintions.patch \
"
SRCREV = "3d7a6ae1745a2f60f76afd3ad3ca57329388168c"

S = "${WORKDIR}/git"

DPAA_VER ?= "dpaa2"
DPAA_VER_fsl-lsch2 = "dpaa"
export RTE_TARGET = "${ARCH}-${DPAA_VER}-linuxapp-gcc"
export ETHTOOL_LIB_PATH = "${S}/examples/ethtool/lib/${RTE_TARGET}/"

EXTRA_OEMAKE += 'ARCH="${ARCH}" CROSS="${TARGET_PREFIX}" \
    CPU_CFLAGS="--sysroot=${STAGING_DIR_HOST}" RTE_SDK="${S}" \
    OPENSSL_PATH="${STAGING_DIR_HOST}" RTE_KERNELDIR="${STAGING_KERNEL_DIR}" \
    RTE_KERNELDIR_OUT="${STAGING_KERNEL_BUILDDIR}" \
'

do_configure[noexec] = "1"

do_compile[depends] += "virtual/kernel:do_shared_workdir"
do_compile() {
    oe_runmake O="${RTE_TARGET}" T="${RTE_TARGET}" config
}

do_install() {
    unset LDFLAGS TARGET_LDFLAGS BUILD_LDFLAGS

    oe_runmake EXTRA_LDFLAGS="-L${STAGING_LIBDIR} --hash-style=gnu"  WERROR_FLAGS="-w" V=1  T="${RTE_TARGET}" DESTDIR="${D}" install

    # Build and install the DPDK examples
    for APP in examples/l2fwd examples/l3fwd examples/l2fwd-crypto examples/ipsec-secgw examples/kni examples/ip_fragmentation examples/ip_reassembly; do
        oe_runmake EXTRA_LDFLAGS="-L${STAGING_LIBDIR} --hash-style=gnu"  -C ${APP}

        [ ! -d ${D}/${bindir}/dpdk-example ] && install -d 0644 ${D}/${bindir}/dpdk-example
        install -m 0755 ${S}/examples/`basename ${APP}`/build/`basename ${APP}` \
            ${D}/${bindir}/dpdk-example/
    done
    install -m 0755 ${S}/${RTE_TARGET}/app/testpmd ${D}/${bindir}/dpdk-example/
    rm -fr ${D}/lib/modules/*
    install -d ${D}/lib/modules/${KERNEL_VERSION}/dpdk
    install -m 0755 ${S}/${RTE_TARGET}/kmod/rte_kni.ko ${D}/lib/modules/${KERNEL_VERSION}/dpdk/

    rm ${D}/${datadir}/${RTE_TARGET}/app/dpdk-pmdinfogen

    chown root:root -R ${D}
}

PACKAGE_ARCH = "${MACHINE_ARCH}"

PACKAGES += "${PN}-examples"

FILES_${PN} += "${datadir}/* ${bindir}/* ${sbindir}/*"
FILES_${PN}-dbg += "${bindir}/dpdk-example/.debug \
    ${datadir}/examples/kni/build/.debug \
    ${datadir}/examples/kni/build/app/.debug \
    ${datadir}/examples/l2fwd/build/.debug \
    ${datadir}/examples/l2fwd/build/app/.debug \
    ${datadir}/examples/l2fwd-crypto/build/.debug \
    ${datadir}/examples/l2fwd-crypto/build/app/.debug \
    ${datadir}/examples/l3fwd/build/.debug \
    ${datadir}/examples/l3fwd/build/app/.debug \
    ${datadir}/examples/ipsec-secgw/build/.debug \
    ${datadir}/examples/ipsec-secgw/build/app/.debug \
    ${datadir}/examples/ip_fragmentation/build/.debug \
    ${datadir}/examples/ip_fragmentation/build/app/.debug \
    ${datadir}/examples/ip_reassembly/build/.debug \
    ${datadir}/examples/ip_reassembly/build/app/.debug \
"
FILES_${PN}-dev += "${datadir}/mk ${datadir}/scripts \
    ${datadir}/${RTE_TARGET} \
    ${includedir} \
"
FILES_${PN}-examples += "${datadir}/examples"

COMPATIBLE_MACHINE = "(ls2080ardb|ls2084ardb|ls2088a|ls1043a|ls1046a|ls1088a)"
