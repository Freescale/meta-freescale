SUMMARY = "Test programs for IMX BSP"
DESCRIPTION = "Unit tests for the IMX BSP"
SECTION = "base"
DEPENDS = "virtual/kernel imx-lib"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58"

INC_PR = "r7"

PLATFORM_mx6 = "IMX6Q"
PLATFORM_mx53 = "IMX53"
PLATFORM_mx51 = "IMX51"

SRC_URI = "${FSL_MIRROR}/imx-test-${PV}.tar.gz \
           file://clocks.sh \
           file://mxc_x11_test-exclude-IMX-5-6-platforms.patch"

INCLUDE_DIR="-I${STAGING_INCDIR} \
             -I${STAGING_KERNEL_DIR}/include \
             -I${STAGING_KERNEL_DIR}/drivers/mxc/security/rng/include \
             -I${STAGING_KERNEL_DIR}/drivers/mxc/security/sahara2/include"

do_compile() {
        LDFLAGS="" make PLATFORM=${PLATFORM} LINUXPATH=${STAGING_KERNEL_DIR} \
        KBUILD_OUTPUT=${STAGING_KERNEL_DIR} CROSS_COMPILE=${TARGET_PREFIX} V=1 \
        INC="${INCLUDE_DIR}"
}

do_install() {
        install -d ${D}/unit_tests
        install -m 755 test-utils.sh ${D}/unit_tests/test-utils.sh
        install -m 0755 ${WORKDIR}/clocks.sh ${D}/unit_tests/clocks.sh
        install -m 755 ${S}/platform/${PLATFORM}/* ${D}/unit_tests/
}

FILES_${PN} += "/unit_tests"
FILES_${PN}-dbg += "/unit_tests/.debug"

PACKAGE_ARCH = "${MACHINE_ARCH}"
