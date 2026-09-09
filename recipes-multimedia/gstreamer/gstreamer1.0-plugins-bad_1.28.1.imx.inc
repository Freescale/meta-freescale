# i.MX customization for gstreamer1.0-plugins-bad_1.28.1.imx.bb.
# Not a standalone recipe: required by that .bb, and must stay beside
# it. Keeping it separate lets the .bb remain a verbatim copy of the
# OE-core original.

DEFAULT_PREFERENCE = "-1"

LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=69333daa044cb77e486cc36129f7a770"

SRC_URI:remove = "https://gstreamer.freedesktop.org/src/gst-plugins-bad/gst-plugins-bad-${PV}.tar.xz \
                  file://0001-fix-maybe-uninitialized-warnings-when-compiling-with.patch \
                  file://0002-avoid-including-sys-poll.h-directly.patch \
                  file://0004-opencv-resolve-missing-opencv-data-dir-in-yocto-buil.patch \
                  "
SRC_URI:prepend = "${GST1.0-PLUGINS-BAD_SRC};branch=${SRCBRANCH} "

# The cuda gir patch carries no Signed-off-by. It is an NXP-internal change
# that arrived here without one, and a sign-off is a certification only its
# author can make, so there is nothing here for us to correct.
# nooelint: oelint.file.patchsignedoff
SRC_URI:append:mx93-nxp-bsp = " file://0001-MMFMWK-9590-gstcuda-disable-gir-build-for-cuda-plugi.patch"
SRC_URI:append:mx943-nxp-bsp = " file://0001-MMFMWK-9590-gstcuda-disable-gir-build-for-cuda-plugi.patch"

GST1.0-PLUGINS-BAD_SRC ?= "gitsm://github.com/nxp-imx/gst-plugins-bad.git;protocol=https"
SRCBRANCH = "MM_04.11.00_2605_L6.18.20"
SRCREV = "ae93b57a6a238604c305ca79e25d6248b5bab5d6"

inherit use-imx-headers

PACKAGE_ARCH:imxpxp = "${MACHINE_SOCARCH}"
PACKAGE_ARCH:mx8-nxp-bsp = "${MACHINE_SOCARCH}"

PACKAGECONFIG_REMOVE ?= "\
    dtls vulkan \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', '', 'gl', d)} \
"
PACKAGECONFIG:remove = "${PACKAGECONFIG_REMOVE}"
PACKAGECONFIG:remove:mx93-nxp-bsp = "gl"
PACKAGECONFIG:remove:mx943-nxp-bsp = "gl"
PACKAGECONFIG:append:mx8-nxp-bsp = " kms"

PACKAGECONFIG:append = " ${PACKAGECONFIG_G2D}"
PACKAGECONFIG_G2D ??= ""
PACKAGECONFIG_G2D:imxgpu2d ??= "g2d"

PACKAGECONFIG[g2d] = ",,virtual/libg2d"

EXTRA_OEMESON += "\
    -Dc_args="${CFLAGS} -I${STAGING_INCDIR_IMX}" \
"

EXTRA_OEMESON:remove = "\
    -Dkate=disabled \
"

COMPATIBLE_MACHINE = "(imx-nxp-bsp)"

# The per-plugin split packages (${PN}-*, libgst*) are created dynamically by
# PACKAGES_DYNAMIC, which OE-core sets in gstreamer1.0-plugins-packaging.inc
# (pulled in through the required gstreamer1.0-plugins-common.inc above).
PACKAGES_DYNAMIC = "^${PN}-.* ^libgst.*"
