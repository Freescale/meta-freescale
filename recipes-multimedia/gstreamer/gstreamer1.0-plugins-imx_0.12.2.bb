DESCRIPTION = "GStreamer 1.0 plugins for i.MX platforms"
LICENSE = "LGPLv2+"
LIC_FILES_CHKSUM = "file://LICENSE;md5=55ca817ccb7d5b5b66355690e9abc605"
SECTION = "multimedia"
# gstreamer1.0-plugins-bad is in DEPENDS because the build script scans for the
# GstPhotography headers and libraries
DEPENDS = "gstreamer1.0 gstreamer1.0-plugins-base gstreamer1.0-plugins-bad virtual/kernel"
# add the audioparsers and the videoparsersbad plugins as RDEPENDS ; audioparsers
# for the uniaudio decoder, videoparsersbad for the VPU video decoder
# the gstreamer1.0-plugins-imx RDEPENDS is necessary to ensure the -good recipe is
# build (it is not a compile-time dependency however, hence RDEPENDS and not DEPENDS)
RDEPENDS_gstreamer1.0-plugins-imx = "gstreamer1.0-plugins-good"
RDEPENDS_gstreamer1.0-plugins-imx-imxaudio = "gstreamer1.0-plugins-good-audioparsers"
RDEPENDS_gstreamer1.0-plugins-imx-imxvpu = "gstreamer1.0-plugins-bad-videoparsersbad"

SRCBRANCH ?= "master"
SRCREV = "50ddcd9f2b6ecc3d94d0d2f71b6de841d8a8fb7b"
SRC_URI = "git://github.com/Freescale/gstreamer-imx.git;branch=${SRCBRANCH}"

S = "${WORKDIR}/git"

inherit waf

do_compile[depends] += "virtual/kernel:do_shared_workdir"

# configure the eglvivsink element to use the appropriate EGL platform code
# Wayland if 'wayland' is present in DISTRO_FEATURES, if not,
# X11 if x11 is present in DISTRO_FEATURES,
# Framebuffer otherwise
EGLVIVSINK_PLATFORM = "${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'wayland', \
                          bb.utils.contains('DISTRO_FEATURES', 'x11', 'x11', \
                          'fb', d),d)}"

EGL_PLATFORM_CONF = "--egl-platform=${EGLVIVSINK_PLATFORM}"
EXTRA_OECONF = "--kernel-headers=${STAGING_KERNEL_DIR}/include ${PACKAGECONFIG_CONFARGS}"

EGLVIVSINK_DEPENDS = " \
    virtual/egl virtual/libgles2 \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'wayland', \
       bb.utils.contains('DISTRO_FEATURES', 'x11', 'virtual/libx11', \
       '', d), d)} \
"

PACKAGECONFIG ?= "uniaudiodec mp3encoder v4l2src"
PACKAGECONFIG_append_imxgpu3d = " eglvivsink"
PACKAGECONFIG_append_imxgpu2d = " g2d"
PACKAGECONFIG_append_imxipu   = " ipu"
PACKAGECONFIG_append_imxvpu   = " vpu"
PACKAGECONFIG_append_imxpxp   = " pxp"

PACKAGECONFIG[g2d] = ",--disable-g2d,imx-gpu-viv"
PACKAGECONFIG[pxp] = ",--disable-pxp,"
PACKAGECONFIG[ipu] = ",--disable-ipu,"
PACKAGECONFIG[vpu] = ",--disable-vpu,libimxvpuapi"
PACKAGECONFIG[eglvivsink] = "${EGL_PLATFORM_CONF},--disable-eglvivsink,${EGLVIVSINK_DEPENDS}"
PACKAGECONFIG[v4l2src] = ",--disable-v4l2src,"
PACKAGECONFIG[uniaudiodec] = ",--disable-uniaudiodec,imx-codec"
PACKAGECONFIG[mp3encoder] = ",--disable-mp3encoder,imx-codec"

# LIBV is used by gst-plugins-package.inc to specify the GStreamer version (0.10 vs 1.0)
LIBV = "1.0"
require recipes-multimedia/gstreamer/gst-plugins-package.inc

# the following line is required to produce one package for each plugin
PACKAGES_DYNAMIC = "^${PN}-.*"

COMPATIBLE_MACHINE = "(mx6dl|mx6q|mx6sl|mx6sx|mx6ul|mx6ull|mx7d)"
