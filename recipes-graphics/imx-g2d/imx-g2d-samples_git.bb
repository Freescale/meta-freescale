SUMMARY = "i.MX G2D Samples"
DESCRIPTION = "Set of sample applications for i.MX G2D"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=0858ec9c7a80c4a2cf16e4f825a2cc91"

DEPENDS = "cairo"

PV = "2.2+git${SRCPV}"

SRC_URI = "${GPU_G2D_SAMPLES_SRC};branch=${SRCBRANCH}"
GPU_G2D_SAMPLES_SRC ?= "git://github.com/nxp-imx/g2d-samples.git;protocol=https"
SRCBRANCH = "imx_2.2"
SRCREV = "431f311a41ca052902ea0d6445e74defe0a4df2d"

S = "${WORKDIR}/git"

inherit pkgconfig

PACKAGECONFIG ??= "${PACKAGECONFIG_IMPLEMENTATION}"
PACKAGECONFIG_IMPLEMENTATION                   = ""
PACKAGECONFIG_IMPLEMENTATION:imxgpu2d:imxdpu   = "dpu"
PACKAGECONFIG_IMPLEMENTATION:mx95-nxp-bsp      = "dpu95"
PACKAGECONFIG_IMPLEMENTATION:imxgpu2d          = "gpu-drm"
PACKAGECONFIG_IMPLEMENTATION:imxgpu2d:imxfbdev = "gpu-fbdev"
PACKAGECONFIG_IMPLEMENTATION:mx93-nxp-bsp      = "pxp"

PACKAGECONFIG[dpu] = " \
    BUILD_IMPLEMENTATION=dpu, \
    , \
    imx-dpu-g2d wayland-native wayland wayland-protocols, \
    , \
    , \
    dpu95 gpu-drm gpu-fbdev pxp"
PACKAGECONFIG[dpu95] = " \
    BUILD_IMPLEMENTATION=dpu95, \
    , \
    imx-dpu-g2d wayland-native wayland wayland-protocols, \
    , \
    , \
    dpu gpu-drm gpu-fbdev pxp"
PACKAGECONFIG[gpu-drm] = " \
    BUILD_IMPLEMENTATION=gpu-drm, \
    , \
    imx-gpu-g2d wayland-native wayland wayland-protocols, \
    , \
    , \
    dpu dpu95 gpu-fbdev pxp"
PACKAGECONFIG[gpu-fbdev] = " \
    BUILD_IMPLEMENTATION=gpu-fbdev, \
    , \
    imx-gpu-g2d, \
    , \
    , \
    dpu dpu95 gpu-drm pxp"
PACKAGECONFIG[pxp] = " \
    BUILD_IMPLEMENTATION=pxp, \
    , \
    imx-pxp-g2d wayland-native wayland wayland-protocols, \
    , \
    , \
    dpu dpu95 gpu-drm gpu-fbdev"

EXTRA_OEMAKE += " \
    SDKTARGETSYSROOT=${STAGING_DIR_HOST} \
    ${PACKAGECONFIG_CONFARGS} \
"

do_install() {
    oe_runmake install DESTDIR=${D}
}

FILES:${PN} += "/opt"

PACKAGE_ARCH = "${MACHINE_SOCARCH}"

COMPATIBLE_MACHINE = "(imxgpu2d|mx93-nxp-bsp|mx95-nxp-bsp)"
