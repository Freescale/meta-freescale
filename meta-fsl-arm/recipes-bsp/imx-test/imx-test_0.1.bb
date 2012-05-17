SUMMARY = "Test programs for IMX BSP"
DESCRIPTION = "Unit tests for the IMX BSP"
SECTION = "base"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58"

PR = "r3"

COMPATIBLE_MACHINE = "(mx5)"

TESTVERSION = "11.09.01"
S = "${WORKDIR}/${PN}-${TESTVERSION}"

SRC_URI = "file://imx-test-${TESTVERSION}.tar.gz \
           file://0001-ENGR00158471-fix-ipu-unit-test-application-missing-i.patch \
           file://0002-ENGR00170223-vpu-Fix-encoder-with-rotation-90-or-270.patch \
           file://0003-ENGR00162747-fix-asrc-sample-rate-convert-issue.patch"

TESTPLATFORM_imx53ard = "IMX53"
TESTPLATFORM_imx53qsb = "IMX53"
TESTPLATFORM_imx51evk = "IMX51"

do_compile() {
	# LDFLAGS="" else modules' compilation fails
	LDFLAGS="" make PLATFORM=${TESTPLATFORM} LINUXPATH=${STAGING_KERNEL_DIR} \
	KBUILD_OUTPUT=${STAGING_KERNEL_DIR} CROSS_COMPILE=${TARGET_PREFIX} V=1
}

do_install() {
	install -d ${D}/unit_tests
	install -m 755 test-utils.sh ${D}/unit_tests/test-utils.sh
	install -m 755 ${S}/platform/${TESTPLATFORM}/* ${D}/unit_tests/
	install -d ${D}/unit_tests/modules
	cp ${S}/module_test/*.ko ${D}/unit_tests/modules
}

FILES_${PN} += "/unit_tests"
FILES_${PN}-dbg += "/unit_tests/.debug"
