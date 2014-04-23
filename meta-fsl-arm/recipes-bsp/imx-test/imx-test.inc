SUMMARY = "Test programs for IMX BSP"
DESCRIPTION = "Unit tests for the IMX BSP"
SECTION = "base"
DEPENDS_mx5   = "virtual/kernel imx-lib imx-vpu"
DEPENDS_mx6q  = "virtual/kernel imx-lib imx-vpu"
DEPENDS_mx6dl = "virtual/kernel imx-lib imx-vpu"
DEPENDS_mx6sl = "virtual/kernel imx-lib"
DEPENDS_mxs   = "virtual/kernel"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

PE = "1"

PLATFORM_mx6 = "IMX6Q"
PLATFORM_mx6sl = "IMX6S"
PLATFORM_mx53 = "IMX53"
PLATFORM_mx51 = "IMX51"

SRC_URI = "${FSL_MIRROR}/imx-test-${PV}.tar.gz"

inherit module-base

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

INCLUDE_DIR="-I${STAGING_INCDIR} \
             -I${S}/include \
             -I${STAGING_KERNEL_DIR}/include/uapi \
             -I${STAGING_KERNEL_DIR}/include \
             -I${STAGING_KERNEL_DIR}/arch/arm/include \
             -I${STAGING_KERNEL_DIR}/drivers/mxc/security/rng/include \
             -I${STAGING_KERNEL_DIR}/drivers/mxc/security/sahara2/include"

# Required so the fixdep binary is generated
addtask make_scripts after do_patch before do_compile
do_make_scripts[lockfiles] = "${TMPDIR}/kernel-scripts.lock"
do_make_scripts[deptask] = "do_populate_sysroot"

do_compile() {
        LDFLAGS="" make PLATFORM=${PLATFORM} LINUXPATH=${STAGING_KERNEL_DIR} \
        KBUILD_OUTPUT=${STAGING_KERNEL_DIR} CROSS_COMPILE=${TARGET_PREFIX} V=1 \
        INC="${INCLUDE_DIR}"
}

do_install() {
        install -d ${D}/unit_tests
        install -m 755 test-utils.sh ${D}/unit_tests/test-utils.sh
        install -m 755 ${S}/platform/${PLATFORM}/* ${D}/unit_tests/
        if [ -e ${WORKDIR}/clocks.sh ]; then
                install -m 755 ${WORKDIR}/clocks.sh ${D}/unit_tests/clocks.sh
        fi
}

FILES_${PN} += "/unit_tests"
RDEPENDS_${PN} = "bash"

FILES_${PN}-dbg += "/unit_tests/.debug"
