DESCRIPTION = "Data Plane Development Kit"
HOMEPAGE = "http://dpdk.org"

RDEPENDS_${PN} += "python3-core"
DEPENDS = "virtual/kernel openssl"
DEPENDS_append_x86-64 = " numactl"
do_configure[depends] += "virtual/kernel:do_shared_workdir"

inherit module

COMPATIBLE_HOST = '(aarch64|arm|i.86|x86_64).*-linux'
COMPATIBLE_HOST_libc-musl = 'null'
COMPATIBLE_HOST_armv4 = 'null'
COMPATIBLE_HOST_armv5 = 'null'
COMPATIBLE_HOST_armv6 = 'null'

COMPATIBLE_MACHINE = "(imx|qoriq)"

export EXAMPLES_BUILD_DIR = "${RTE_TARGET}"
export PKGS_WITH_OPENSSL_ENABLED = "l2fwd-crypto ipsec-secgw"

DPDK_RTE_TARGET_x86-64 = "x86_64-native-linuxapp-gcc"
DPDK_RTE_TARGET_x86 = "i686-native-linuxapp-gcc"
DPDK_RTE_TARGET_armv7a = "${ARCH}-armv7a-linuxapp-gcc"
DPDK_RTE_TARGET_armv7ve = "${ARCH}-armv7a-linuxapp-gcc"
DPDK_RTE_TARGET ?= "${ARCH}-dpaa-linuxapp-gcc"
export RTE_TARGET = "${DPDK_RTE_TARGET}"

export RTE_OUTPUT = "${S}/${RTE_TARGET}"
export MODULE_DIR = "/lib/modules/${KERNEL_VERSION}/kernel/drivers/net"

S = "${WORKDIR}/git"

EXTRA_OEMAKE += '\
    ETHTOOL_LIB_PATH="${S}/examples/ethtool/lib/${RTE_TARGET}" \
    RTE_SDK="${S}" \
    OPENSSL_PATH="${STAGING_DIR_HOST}" \
    RTE_KERNELDIR="${STAGING_KERNEL_DIR}" \
    RTE_KERNELDIR_OUT="${STAGING_KERNEL_BUILDDIR}" \
'

do_configure () {
    #############################################################
    ### default value for prefix is "usr", unsetting it, so it
    ### will not be concatenated in ${RTE_TARGET}/Makefile
    ### which will cause compilation failure
    #############################################################
    unset prefix
    oe_runmake O=$RTE_TARGET T=$RTE_TARGET config
}

do_compile () {
    unset LDFLAGS TARGET_LDFLAGS BUILD_LDFLAGS

    cd ${S}/${RTE_TARGET}
    oe_runmake \
    CONFIG_RTE_EAL_IGB_UIO=n \
    CONFIG_RTE_KNI_KMOD=y \
    CONFIG_RTE_LIBRTE_PMD_OPENSSL=y \
    EXTRA_LDFLAGS="-L${STAGING_LIBDIR} --hash-style=gnu" \
    EXTRA_CFLAGS="${HOST_CC_ARCH} ${TOOLCHAIN_OPTIONS} -I${STAGING_INCDIR}" \
    CROSS="${TARGET_PREFIX}" \
    prefix="" LDFLAGS="${TUNE_LDARGS}" WERROR_FLAGS="-w" V=1

    cd ${S}/examples/
    oe_runmake \
    EXTRA_LDFLAGS="-L${STAGING_LIBDIR} --hash-style=gnu -fuse-ld=bfd" \
    EXTRA_CFLAGS="${HOST_CC_ARCH} ${TOOLCHAIN_OPTIONS} -O3 -I${STAGING_INCDIR}" \
    CROSS="${TARGET_PREFIX}" O="${S}/examples"

    for APP in ${PKGS_WITH_OPENSSL_ENABLED}; do
        oe_runmake -C ${APP} CONFIG_RTE_LIBRTE_PMD_OPENSSL=y \
        EXTRA_LDFLAGS="-L${STAGING_LIBDIR} --hash-style=gnu -fuse-ld=bfd" \
        EXTRA_CFLAGS="${HOST_CC_ARCH} ${TOOLCHAIN_OPTIONS} -I${STAGING_INCDIR}" \
        CROSS="${TARGET_PREFIX}" O="${S}/examples/${APP}"
    done
}

do_install () {
    oe_runmake O=${RTE_OUTPUT} T= install-runtime DESTDIR=${D}
    oe_runmake O=${RTE_OUTPUT} T= install-kmod DESTDIR=${D} kerneldir=${MODULE_DIR}
    oe_runmake O=${RTE_OUTPUT} T= install-sdk DESTDIR=${D}

    # install examples without openssl enabled
    install -m 0755 -d ${D}/${datadir}/dpdk/examples
    for dirname in ${S}/examples/*
    do
        for appname in `find ${dirname} -regex ".*${EXAMPLES_BUILD_DIR}\/app\/[-0-9a-zA-Z0-9/_]*$"`
        do
            install -m 755 ${appname} ${D}/${datadir}/dpdk/examples/
        done
    done

    # install examples with openssl enabled
    for APP in ${PKGS_WITH_OPENSSL_ENABLED}; do
        install ${S}/examples/${APP}/${APP} ${D}/${datadir}/dpdk/examples/
    done

    # install cmdif header and library
    install -m 0644 -d ${D}/${datadir}/dpdk/cmdif/include
    install ${S}/examples/cmdif/lib/client/fsl_cmdif_client.h \
        ${S}/examples/cmdif/lib/server/fsl_cmdif_server.h \
        ${S}/examples/cmdif/lib/shbp/fsl_shbp.h \
        ${D}/${datadir}/dpdk/cmdif/include/
    install -m 0644 -d ${D}/${datadir}/dpdk/cmdif/lib
    install ${S}/examples/cmdif/lib/${RTE_TARGET}/librte_cmdif.a \
        ${D}/${datadir}/dpdk/cmdif/lib/

    # copy nxp specific cfg and scripts
    cp -rf ${S}/nxp/* ${D}/${datadir}/dpdk/
}

PACKAGES += "${PN}-examples"

FILES_${PN}-dbg += " \
    ${datadir}/dpdk/.debug \
    ${datadir}/dpdk/examples/*/.debug \
    "

FILES_${PN}-staticdev += "${datadir}/dpdk/cmdif/lib/*.a"

FILES_${PN}-dev += " \
    ${datadir}/dpdk/${RTE_TARGET}/.config \
    ${includedir} \
    ${includedir}/exec-env \
    ${datadir}/dpdk/buildtools/ \
    ${datadir}/dpdk/${RTE_TARGET}/include \
    ${datadir}/dpdk/${RTE_TARGET}/lib \
    ${datadir}/dpdk/mk \
    "

FILES_${PN} += " ${datadir}/ \
    ${prefix}/sbin/ \
    ${prefix}/bin/ \
    ${libdir}/ \
         "

FILES_${PN}-examples += " \
    ${datadir}/examples/* \
    "
