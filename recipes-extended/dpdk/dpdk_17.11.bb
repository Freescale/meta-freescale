DESCRIPTION = "Data Plane Development Kit"
HOMEPAGE = "http://dpdk.org"
LICENSE = "BSD-3-Clause & LGPLv2 & GPLv2"
LIC_FILES_CHKSUM = "file://license/README;md5=3383def2d4c82237df281174e981a492"

DEPENDS += "virtual/kernel openssl"
RDEPENDS_${PN} = "bash python"
RDEPENDS_${PN}-examples = "bash python-core"

inherit module

SRC_URI = "git://source.codeaurora.org/external/qoriq/qoriq-components/dpdk;nobranch=1 \
    file://add-RTE_KERNELDIR_OUT-to-split-kernel-bu.patch \
    file://0001-add-Wno-cast-function-type.patch \
"
SRCREV = "c0fe1b99b562a4015423e8ff748bfb0f55a68c05"

S = "${WORKDIR}/git"

DPAA_VER ?= "dpaa"
export RTE_TARGET = "${ARCH}-${DPAA_VER}-linuxapp-gcc"
export ETHTOOL_LIB_PATH = "${S}/examples/ethtool/lib/${RTE_TARGET}/"

EXTRA_OEMAKE += 'ARCH="${ARCH}" CROSS="${TARGET_PREFIX}" \
    CPU_CFLAGS="-fPIC --sysroot=${STAGING_DIR_HOST}" RTE_SDK="${S}" \
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

    oe_runmake EXTRA_LDFLAGS="-L${STAGING_LIBDIR} --hash-style=gnu"  WERROR_FLAGS="-w" V=1  T="${RTE_TARGET}" DESTDIR="${D}" install CONFIG_RTE_EAL_IGB_UIO=n CONFIG_RTE_KNI_KMOD=y CONFIG_RTE_LIBRTE_PMD_OPENSSL=y 

    # Build and install the DPDK examples
    for APP in examples/l2fwd examples/l3fwd  examples/l2fwd-qdma examples/l2fwd-crypto examples/ipsec-secgw examples/kni examples/ip_fragmentation examples/ip_reassembly; do
        temp=`basename ${APP}` 
        if [ ${temp} = "ipsec-secgw" ] || [ ${temp} = "l2fwd-crypto" ]; then 
            oe_runmake EXTRA_LDFLAGS="-L${STAGING_LIBDIR} --hash-style=gnu"  -C ${APP} CONFIG_RTE_LIBRTE_PMD_OPENSSL=y
        else 
            oe_runmake EXTRA_LDFLAGS="-L${STAGING_LIBDIR} --hash-style=gnu" EXTRA_CFLAGS="--sysroot=${STAGING_DIR_HOST} -I${STAGING_INCDIR}" -C ${APP}
        fi

        [ ! -d ${D}/${bindir}/dpdk-example ] && install -d 0644 ${D}/${bindir}/dpdk-example
        install -m 0755 ${S}/examples/`basename ${APP}`/build/`basename ${APP}` \
            ${D}/${bindir}/dpdk-example/
    done
    oe_runmake EXTRA_LDFLAGS="-L${STAGING_LIBDIR} --hash-style=gnu"  -C examples/vhost
    install -m 0755 ${S}/examples/vhost/build/vhost-switch ${D}/${bindir}/dpdk-example/
    oe_runmake EXTRA_LDFLAGS="-L${STAGING_LIBDIR} --hash-style=gnu"  -C examples/cmdif
    
    install -d 0644 ${D}/usr/share/dpdk/cmdif/include
    install -d 0644 ${D}/usr/share/dpdk/cmdif/lib
    cp examples/cmdif/lib/client/fsl_cmdif_client.h examples/cmdif/lib/server/fsl_cmdif_server.h \
        examples/cmdif/lib/shbp/fsl_shbp.h      ${D}/usr/share/dpdk/cmdif/include 
    cp examples/cmdif/lib/${RTE_TARGET}/librte_cmdif.a ${D}/usr/share/dpdk/cmdif/lib

    install -m 0755 ${S}/${RTE_TARGET}/app/testpmd ${D}/${bindir}/dpdk-example/
    rm -fr ${D}/lib/modules/*
    install -d ${D}/lib/modules/${KERNEL_VERSION}/dpdk
    install -m 0755 ${S}/${RTE_TARGET}/kmod/rte_kni.ko ${D}/lib/modules/${KERNEL_VERSION}/dpdk/
    install -d ${D}/${bindir}/dpdk-example/extras
    cp -rf  ${S}/nxp/* ${D}/${bindir}/dpdk-example/extras/
    rm ${D}/${datadir}/${RTE_TARGET}/app/dpdk-pmdinfogen

    chown root:root -R ${D}
}

PACKAGE_ARCH = "${MACHINE_ARCH}"

PACKAGES += "${PN}-examples"

FILES_${PN} += "${datadir}/* ${bindir}/* ${sbindir}/* /usr/share/dpdk/cmdif/include/*"
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

FILES_${PN}-staticdev += "/usr/share/dpdk/cmdif/lib/*.a \ 
    /usr/share/examples/cmdif/lib/arm64-dpaa-linuxapp-gcc/*.a \
    /usr/share/examples/cmdif/lib/arm64-dpaa-linuxapp-gcc/lib/*.a \
"
COMPATIBLE_MACHINE = "(qoriq)"
