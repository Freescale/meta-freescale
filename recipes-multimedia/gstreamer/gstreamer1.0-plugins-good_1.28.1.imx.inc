# i.MX customization for gstreamer1.0-plugins-good_1.28.1.imx.bb.
# Not a standalone recipe: required by that .bb, and must stay beside
# it. Keeping it separate lets the .bb remain a verbatim copy of the
# OE-core original.

DEFAULT_PREFERENCE = "-1"

LIC_FILES_CHKSUM = "\
    file://LICENSE.txt;md5=69333daa044cb77e486cc36129f7a770 \
    file://gst/replaygain/rganalysis.c;beginline=1;endline=23;md5=b60ebefd5b2f5a8e0cab6bfee391a5fe \
"
# fb implementation of v4l2 uses libdrm
DEPENDS:append = " ${@bb.utils.contains('PACKAGECONFIG', 'v4l2', '${DEPENDS_V4L2}', '', d)}"
DEPENDS_V4L2 = "${@bb.utils.contains_any('DISTRO_FEATURES', 'wayland x11', '', 'libdrm', d)}"

# FIXME: imx93/943 DISTRO_FEATURES contains x11 but v4l2 implement by libdrm ?
DEPENDS:append:mx93-nxp-bsp = " libdrm"
DEPENDS:append:mx943-nxp-bsp = " libdrm"

SRC_URI:remove = "https://gstreamer.freedesktop.org/src/gst-plugins-good/gst-plugins-good-${PV}.tar.xz \
                  file://0001-qt-include-ext-qt-gstqtgl.h-instead-of-gst-gl-gstglf.patch \
                  file://0001-v4l2-Define-ioctl_req_t-for-posix-linux-case.patch \
"

SRC_URI:prepend = "${GST1.0-PLUGINS-GOOD_SRC};branch=${SRCBRANCH} "
GST1.0-PLUGINS-GOOD_SRC ?= "gitsm://github.com/nxp-imx/gst-plugins-good.git;protocol=https"
SRCBRANCH = "MM_04.11.00_2605_L6.18.20"
SRCREV = "59dc51ce285f65dfde65c4484eb1da8b51c18841"

S = "${UNPACKDIR}/${BP}"

# Enable pulsesink in gstreamer
PACKAGECONFIG:append = " pulseaudio"

COMPATIBLE_MACHINE = "(imx-nxp-bsp)"

# The per-plugin split packages (${PN}-*, libgst*) are created dynamically by
# PACKAGES_DYNAMIC, which OE-core sets in gstreamer1.0-plugins-packaging.inc
# (pulled in through the required gstreamer1.0-plugins-common.inc above).
PACKAGES_DYNAMIC = "^${PN}-.* ^libgst.*"
