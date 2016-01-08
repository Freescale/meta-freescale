DESCRIPTION = "GStreamer 1.0 plugins for i.MX platforms"
LICENSE = "LGPLv2+"
LIC_FILES_CHKSUM = "file://LICENSE;md5=55ca817ccb7d5b5b66355690e9abc605"
SECTION = "multimedia"
# gstreamer1.0-plugins-bad is in DEPENDS because imxv4l2videosrc requires
# the GstPhotography headers and libraries
DEPENDS = "gstreamer1.0 gstreamer1.0-plugins-base gstreamer1.0-plugins-bad imx-gpu-viv \
           libfslcodec libimxvpuapi virtual/kernel virtual/egl virtual/libgles2 \
           ${@base_contains('DISTRO_FEATURES', 'x11', 'virtual/libx11', '', d)}"
# add the audioparsers and the videoparsersbad plugins as RDEPENDS ; audioparsers
# for the uniaudio decoder, videoparsersbad for the VPU video decoder
# the gstreamer1.0-plugins-imx RDEPENDS is necessary to ensure the -good recipe is
# build (it is not a compile-time dependency however, hence RDEPENDS and not DEPENDS)
RDEPENDS_gstreamer1.0-plugins-imx = "gstreamer1.0-plugins-good"
RDEPENDS_gstreamer1.0-plugins-imx-imxaudio = "gstreamer1.0-plugins-good-audioparsers"
RDEPENDS_gstreamer1.0-plugins-imx-imxvpu = "gstreamer1.0-plugins-bad-videoparsersbad"

SRCBRANCH ?= "master"
SRCREV = "f8ad953c458be6a89f474a948b6401664a6a1745"
SRC_URI = "git://github.com/Freescale/gstreamer-imx.git;branch=${SRCBRANCH}"

S = "${WORKDIR}/git"

inherit waf

do_compile[depends] += "virtual/kernel:do_shared_workdir"

# configure the eglvivsink element to use the appropriate EGL platform code
# X11 if x11 is present in DISTRO_FEATURES
# Wayland if x11 is not present in DISTRO_FEATURES, but wayland is
# Framebuffer otherwise
EGLVIVSINK_PLATFORM = "${@base_contains('DISTRO_FEATURES', 'x11', 'x11', \
                          base_contains('DISTRO_FEATURES', 'wayland', 'wayland', \
                          'fb', d),d)}"

EXTRA_OECONF = "--egl-platform=${EGLVIVSINK_PLATFORM} --kernel-headers=${STAGING_KERNEL_DIR}/include"

# LIBV is used by gst-plugins-package.inc to specify the GStreamer version (0.10 vs 1.0)
LIBV = "1.0"
require recipes-multimedia/gstreamer/gst-plugins-package.inc

# the following line is required to produce one package for each plugin
PACKAGES_DYNAMIC = "^${PN}-.*"

COMPATIBLE_MACHINE = "(mx6)"

# disable the false alarm (the "it isn't a build dependency" QA warning)
INSANE_SKIP_gstreamer1.0-plugins-imx-imxaudio = "build-deps"
INSANE_SKIP_gstreamer1.0-plugins-imx-imxvpu = "build-deps"
