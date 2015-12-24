# Copyright (C) 2012-2016 O.S. Systems Software LTDA.
# Copyright (C) 2015 Freescale Semiconductor

SUMMARY = "Test programs for IMX BSP"
DESCRIPTION = "Unit tests for the IMX BSP"
SECTION = "base"
DEPENDS_mx6q  = "virtual/kernel imx-lib imx-vpu"
DEPENDS_mx6dl = "virtual/kernel imx-lib imx-vpu"
DEPENDS_mx6sl = "virtual/kernel imx-lib"
DEPENDS_mx6sx = "virtual/kernel imx-lib"
DEPENDS_mx6ul = "virtual/kernel imx-lib"
DEPENDS_mx7   = "virtual/kernel imx-lib"

DEPENDS_append = " \
    ${@base_contains('DISTRO_FEATURES', 'x11', 'libx11 libxdamage libxrender libxrandr', '', d)} \
"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

PE = "1"

SRC_URI = "${FSL_MIRROR}/imx-test-${PV}.tar.gz"

inherit module-base

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

PLATFORM_mx6 = "IMX6Q"
PLATFORM_mx6sl = "IMX6SL"
PLATFORM_mx6sx = "IMX6SX"
PLATFORM_mx7 = "IMX7D"
PLATFORM_mx6ul = "IMX6UL"

# Required so the fixdep binary is generated
addtask make_scripts after do_patch before do_compile
do_make_scripts[lockfiles] = "${TMPDIR}/kernel-scripts.lock"
do_make_scripts[deptask] = "do_populate_sysroot"
do_make_scripts[depends] += "virtual/kernel:do_install"

do_compile() {
    CFLAGS="${TOOLCHAIN_OPTIONS}"
    LDFLAGS="${TOOLCHAIN_OPTIONS} -L${STAGING_LIBDIR}"
    oe_runmake V=1 VERBOSE='' \
               CROSS_COMPILE=${TARGET_PREFIX} \
               INC="-I${STAGING_INCDIR} \
                    -I${S}/include \
                    -I${STAGING_KERNEL_BUILDDIR}/include/uapi \
                    -I${STAGING_KERNEL_BUILDDIR}/include \
                    -I${STAGING_KERNEL_DIR}/include/uapi \
                    -I${STAGING_KERNEL_DIR}/include \
                    -I${STAGING_KERNEL_DIR}/arch/arm/include \
                    -I${STAGING_KERNEL_DIR}/drivers/mxc/security/rng/include \
                    -I${STAGING_KERNEL_DIR}/drivers/mxc/security/sahara2/include" \
               LINUXPATH=${STAGING_KERNEL_DIR} \
               KBUILD_OUTPUT=${STAGING_KERNEL_BUILDDIR} \
               PLATFORM=${PLATFORM}
}

do_install() {
    oe_runmake DESTDIR=${D}/unit_tests \
               PLATFORM=${PLATFORM} \
               install

    if [ -e ${WORKDIR}/clocks.sh ]; then
        install -m 755 ${WORKDIR}/clocks.sh ${D}/unit_tests/clocks.sh
    fi
}

FILES_${PN} += "/unit_tests"
RDEPENDS_${PN} = "bash"

FILES_${PN}-dbg += "/unit_tests/.debug"
