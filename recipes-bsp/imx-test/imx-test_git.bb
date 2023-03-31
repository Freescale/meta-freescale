# Copyright (C) 2012-2016 O.S. Systems Software LTDA.
# Copyright (C) 2013-2016 Freescale Semiconductor
# Copyright (C) 2017-2019 NXP

SUMMARY = "Test programs for i.MX BSP"
DESCRIPTION = "Unit tests for the i.MX BSP"
SECTION = "base"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

DEPENDS = "alsa-lib libdrm"
DEPENDS_append_mx6 = " imx-lib"
DEPENDS_append_mx7 = " imx-lib"
DEPENDS_append_imxvpu = " virtual/imxvpu"

PE = "1"
PV = "7.0+${SRCPV}"

SRCBRANCH = "lf-5.4.y"
SRC_URI = " \
    git://github.com/nxp-imx/imx-test.git;protocol=https;branch=${SRCBRANCH} \
    file://0001-mxc_v4l2_test-fix-compilation-error-produced-by-gcc9.patch \
    file://memtool_profile \
"
SRCREV = "6d20e84f2dbe5940fe6d629c2839e1390994ee1f"
S = "${WORKDIR}/git"

inherit module-base use-imx-headers

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

PLATFORM_mx6q  = "IMX6Q"
PLATFORM_mx6dl = "IMX6Q"
PLATFORM_mx6sl = "IMX6SL"
PLATFORM_mx6sll = "IMX6SL"
PLATFORM_mx6sx = "IMX6SX"
PLATFORM_mx6ul = "IMX6UL"
PLATFORM_mx7d  = "IMX7D"
PLATFORM_mx7ulp = "IMX7D"
PLATFORM_mx8 = "IMX8"

PARALLEL_MAKE = "-j 1"
EXTRA_OEMAKE += "${PACKAGECONFIG_CONFARGS}"

PACKAGECONFIG = "${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'x11', '', d)}"
PACKAGECONFIG_append_imxvpu = " vpu"

PACKAGECONFIG[x11] = ",,libx11 libxdamage libxrender libxrandr"
PACKAGECONFIG[vpu] = "HAS_VPU=true,HAS_VPU=false,virtual/imxvpu"

# FIXME: Fail to build due to misuse of printf
SECURITY_STRINGFORMAT = ""

do_compile() {
    CFLAGS="${TOOLCHAIN_OPTIONS}"
    oe_runmake V=1 VERBOSE='' \
               CROSS_COMPILE=${TARGET_PREFIX} \
               INC="-I${S}/include \
                    -I${STAGING_INCDIR} \
                    -I${STAGING_INCDIR_IMX}" \
               CC="${CC} -L${STAGING_LIBDIR} ${LDFLAGS}" \
               SDKTARGETSYSROOT=${STAGING_DIR_HOST} \
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
    install -d -m 0755 ${D}/home/root/
    install -m 0644 ${WORKDIR}/memtool_profile ${D}/home/root/.profile
}

FILES_${PN} += "/unit_tests /home/root/.profile"
RDEPENDS_${PN} = "bash"

FILES_${PN}-dbg += "/unit_tests/.debug"
