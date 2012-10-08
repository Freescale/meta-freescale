SUMMARY = "Test programs for IMX BSP"
DESCRIPTION = "Unit tests for the IMX BSP"
SECTION = "base"
DEPENDS = "virtual/kernel imx-lib"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58"

INC_PR = "r6"

PLATFORM_mx6 = "IMX6Q"
PLATFORM_mx53 = "IMX53"
PLATFORM_mx51 = "IMX51"

SRC_URI = "${FSL_MIRROR}/imx-test-${PV}.tar.gz"

do_compile() {
        LDFLAGS="" make PLATFORM=${PLATFORM} LINUXPATH=${STAGING_KERNEL_DIR} \
        KBUILD_OUTPUT=${STAGING_KERNEL_DIR} CROSS_COMPILE=${TARGET_PREFIX} V=1
}

do_install() {
        install -d ${D}/unit_tests
        install -m 755 test-utils.sh ${D}/unit_tests/test-utils.sh
        install -m 755 ${S}/platform/${PLATFORM}/* ${D}/unit_tests/
}

FILES_${PN} += "/unit_tests"
FILES_${PN}-dbg += "/unit_tests/.debug"

PACKAGE_ARCH = "${MACHINE_ARCH}"
