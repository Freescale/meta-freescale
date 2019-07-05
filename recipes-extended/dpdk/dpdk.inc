DESCRIPTION = "Data Plane Development Kit"
HOMEPAGE = "http://dpdk.org"
LICENSE = "BSD-3-Clause & LGPLv2 & GPLv2"
LIC_FILES_CHKSUM = "file://license/README;md5=3383def2d4c82237df281174e981a492"

SRC_URI = "git://source.codeaurora.org/external/qoriq/qoriq-components/dpdk;nobranch=1 \
           file://add-RTE_KERNELDIR_OUT-to-split-kernel-bu.patch \
           file://0001-add-Wno-cast-function-type.patch \
           file://0001-Add-RTE_KERNELDIR_OUT.patch \
           file://0004-update-WERROR_FLAGS.patch \
"
SRCREV = "0123ced10233e7de8a72f743e2ae7e9791124f07"

# Don't use the default value of datadir and let the dpdk set it to a dpdk
# specific directory
datadir[unexport] = "1"

RDEPENDS_${PN} += "python-subprocess"
DEPENDS = "virtual/kernel openssl"
do_configure[depends] += "virtual/kernel:do_shared_workdir"

inherit module

export MODULE_DIR="/lib/modules/${KERNEL_VERSION}/kernel/drivers/net"
export RTE_SDK = "${S}"
export RTE_TARGET = "${ARCH}-dpaa-linuxapp-gcc"
export ICP_ROOT = "${PKG_CONFIG_SYSROOT_DIR}/usr/include"
export ICP_LIB_ROOT= "${PKG_CONFIG_SYSROOT_DIR}/usr/lib"
export RTE_KERNELDIR = "${STAGING_KERNEL_DIR}"
export RTE_KERNELDIR_OUT = "${STAGING_KERNEL_BUILDDIR}"
export INSTALL_PATH = "${prefix}/share/dpdk"
export RTE_OUTPUT = "${S}/${RTE_TARGET}"
export ETHTOOL_LIB_PATH = "${S}/examples/ethtool/lib/${RTE_TARGET}/"
export SYSROOTPATH = "--sysroot=${STAGING_DIR_HOST}"
export ICP_LAC_API_DIR = "${STAGING_DIR_TARGET}${includedir}/lac"
export OPENSSL_PATH = "${STAGING_DIR_HOST}"
export EXAMPLES_BUILD_DIR = "${RTE_TARGET}"

S = "${WORKDIR}/git"

do_configure () {
	#############################################################
	### default value for prefix is "usr", unsetting it, so it
	### will not be concatenated in ${RTE_TARGET}/Makefile
	### which will cause compilation failure
	#############################################################
	unset prefix

	make O=$RTE_TARGET T=$RTE_TARGET config
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
        install -d 0644 ${D}/usr/share/dpdk/cmdif/include
        install -d 0644 ${D}/usr/share/dpdk/cmdif/lib
        cp examples/cmdif/lib/client/fsl_cmdif_client.h examples/cmdif/lib/server/fsl_cmdif_server.h \
            examples/cmdif/lib/shbp/fsl_shbp.h      ${D}/usr/share/dpdk/cmdif/include
        cp examples/cmdif/lib/${RTE_TARGET}/librte_cmdif.a ${D}/usr/share/dpdk/cmdif/lib
        install -d 0644 ${D}/usr/share/dpdk/examples/ipsec_secgw
	cp -r ${S}/examples/ipsec-secgw/*.cfg  ${D}/usr/share/dpdk/examples/ipsec_secgw
        cp -rf ${S}/nxp/* ${D}/usr/share/dpdk
}

PACKAGES += "${PN}-examples"

FILES_${PN}-dbg += " \
	${INSTALL_PATH}/.debug \
	${INSTALL_PATH}/examples/*/.debug \
	"
FILES_${PN}-staticdev += "/usr/share/dpdk/cmdif/lib/*.a \
"
FILES_${PN}-dev += " \
	${INSTALL_PATH}/${RTE_TARGET}/.config \
	${includedir} \
	${includedir}/${ARCHDIR} \
	${includedir}/exec-env \
	${INSTALL_PATH}/buildtools/ \
	${INSTALL_PATH}/${RTE_TARGET}/include \
	${INSTALL_PATH}/${RTE_TARGET}/lib \
	${INSTALL_PATH}/mk \
	"

FILES_${PN} += " ${INSTALL_PATH}/ \
		 ${prefix}/sbin/ \
		 ${prefix}/bin/ \
		 ${libdir}/ \
		 "
FILES_${PN}-examples += " \
	${INSTALL_PATH}/examples/* \
	"
