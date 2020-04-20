DESCRIPTION = "Data Plane Development Kit"
HOMEPAGE = "http://dpdk.org"

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

DPDK_RTE_TARGET_x86-64 = "x86_64-native-linuxapp-gcc"
DPDK_RTE_TARGET_x86 = "i686-native-linuxapp-gcc"
DPDK_RTE_TARGET_armv7a = "${ARCH}-armv7a-linuxapp-gcc"
DPDK_RTE_TARGET_armv7ve = "${ARCH}-armv7a-linuxapp-gcc"
DPDK_RTE_TARGET ?= "${ARCH}-dpaa-linuxapp-gcc"

export RTE_TARGET = "${DPDK_RTE_TARGET}"
export RTE_OUTPUT = "${S}/${RTE_TARGET}"
export  MODULE_DIR = "/lib/modules/${KERNEL_VERSION}/kernel/drivers/net"

S = "${WORKDIR}/git"

EXTRA_OEMAKE += 'ETHTOOL_LIB_PATH="${S}/examples/ethtool/lib/${RTE_TARGET}" RTE_SDK="${S}" \
    OPENSSL_PATH="${STAGING_DIR_HOST}" RTE_KERNELDIR="${STAGING_KERNEL_DIR}" \
    RTE_KERNELDIR_OUT="${STAGING_KERNEL_BUILDDIR}" EXAMPLES_BUILD_DIR="${RTE_TARGET}" \
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
	oe_runmake  CONFIG_RTE_EAL_IGB_UIO=n CONFIG_RTE_KNI_KMOD=y \
	            CONFIG_RTE_LIBRTE_PMD_OPENSSL=y \
                   EXTRA_LDFLAGS="-L${STAGING_LIBDIR} --hash-style=gnu" \
		   EXTRA_CFLAGS="${HOST_CC_ARCH} ${TOOLCHAIN_OPTIONS} -I${STAGING_INCDIR}" \
		   CROSS="${TARGET_PREFIX}" \
		   prefix=""  LDFLAGS="${TUNE_LDARGS}"  WERROR_FLAGS="-w" V=1

	cd ${S}/examples/
        for APP in l2fwd l3fwd cmdif l2fwd-qdma l2fwd-crypto ipsec-secgw vhost kni ip_fragmentation ip_reassembly; do
            temp=`basename ${APP}`
            if [ ${temp} = "ipsec-secgw" ] || [ ${temp} = "l2fwd-crypto" ]; then
	        oe_runmake EXTRA_LDFLAGS="-L${STAGING_LIBDIR} --hash-style=gnu -fuse-ld=bfd" \
		       EXTRA_CFLAGS="${HOST_CC_ARCH} ${TOOLCHAIN_OPTIONS} -I${STAGING_INCDIR}" \
		       CROSS="${TARGET_PREFIX}" -C ${APP} CONFIG_RTE_LIBRTE_PMD_OPENSSL=y O="${S}/examples/${temp}"
            else
                oe_runmake EXTRA_LDFLAGS="-L${STAGING_LIBDIR} --hash-style=gnu -fuse-ld=bfd" \
                       EXTRA_CFLAGS="${HOST_CC_ARCH} ${TOOLCHAIN_OPTIONS} -I${STAGING_INCDIR}" \
                       CROSS="${TARGET_PREFIX}" -C ${APP} CONFIG_RTE_LIBRTE_PMD_OPENSSL=y O="${S}/examples/${temp}/"
            fi
        done

}

do_install () {
	oe_runmake O=${RTE_OUTPUT} T= install-runtime DESTDIR=${D}
	oe_runmake O=${RTE_OUTPUT} T= install-kmod DESTDIR=${D} kerneldir=${MODULE_DIR}
	oe_runmake O=${RTE_OUTPUT} T= install-sdk DESTDIR=${D}

	# Install examples
        install -d 0644 ${D}/${datadir}/dpdk/cmdif/include
        install -d 0644 ${D}/${datadir}/dpdk/cmdif/lib
        install -d 0644 ${D}/${datadir}/dpdk/examples
        cp examples/cmdif/lib/client/fsl_cmdif_client.h examples/cmdif/lib/server/fsl_cmdif_server.h \
            examples/cmdif/lib/shbp/fsl_shbp.h      ${D}/${datadir}/dpdk/cmdif/include
        cp examples/cmdif/lib/${RTE_TARGET}/librte_cmdif.a ${D}/${datadir}/dpdk/cmdif/lib
        install -d 0644 ${D}/${datadir}/dpdk/examples/ipsec_secgw
	cp -r ${S}/examples/ipsec-secgw/*.cfg  ${D}/${datadir}/dpdk/examples/ipsec_secgw
        cp -rf ${S}/nxp/* ${D}/${datadir}/dpdk
        cp -r ${S}/examples/cmdif/cmdif_demo/${DPDK_RTE_TARGET}/cmdif_demo ${D}/${datadir}/dpdk/examples
        cp -r ${S}/examples/vhost/vhost-switch ${D}/${datadir}/dpdk/examples
        for APP in l2fwd l3fwd l2fwd-qdma l2fwd-crypto ipsec-secgw  kni ip_fragmentation ip_reassembly; do
            cp -r ${S}/examples/${APP}/${APP}  ${D}/${datadir}/dpdk/examples
        done
}

PACKAGES += "${PN}-examples"

FILES_${PN}-dbg += " \
	${datadir}/dpdk/.debug \
	${datadir}/dpdk/examples/*/.debug \
	"
FILES_${PN}-staticdev += "${datadir}/dpdk/cmdif/lib/*.a \
"
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
