DESCRIPTION = "GStreamer 1.0 plugins for i.MX platforms"
LICENSE = "LGPLv2+"
LIC_FILES_CHKSUM = "file://LICENSE;md5=55ca817ccb7d5b5b66355690e9abc605"
SECTION = "multimedia"
DEPENDS = "gstreamer1.0 gstreamer1.0-plugins-base libfslvpuwrap virtual/kernel virtual/egl \
           virtual/libgles2 ${@base_contains('DISTRO_FEATURES', 'x11', 'virtual/libx11', '', d)}"

SRCBRANCH ?= "master"
SRCREV = "67ea62069fca79d65bf244e98ff568d39399fdaf"
SRC_URI = "git://github.com/Freescale/gstreamer-imx.git;branch=${SRCBRANCH}"

S = "${WORKDIR}/git"

inherit waf

# configure the eglvivsink element to use the appropiate EGL platform code
EGLVIVSINK_PLATFORM = "${@base_contains('DISTRO_FEATURES', 'x11', 'x11', 'fb', d)}"
EXTRA_OECONF = "--egl-platform=${EGLVIVSINK_PLATFORM} --kernel-headers=${STAGING_KERNEL_DIR}/include"

# LIBV is used by gst-plugins-package.inc to specify the GStreamer version (0.10 vs 1.0)
LIBV = "1.0"
require recipes-multimedia/gstreamer/gst-plugins-package.inc

# the following line is required to produce one package for each plugin
PACKAGES_DYNAMIC = "^${PN}-.*"

COMPATIBLE_MACHINE = "(mx6)"
