# i.MX customization for gstreamer1.0_1.28.1.imx.bb.
# Not a standalone recipe: required by that .bb, and must stay beside
# it. Keeping it separate lets the .bb remain a verbatim copy of the
# OE-core original.

DEFAULT_PREFERENCE = "-1"

LIC_FILES_CHKSUM = "\
    file://LICENSE.txt;md5=69333daa044cb77e486cc36129f7a770 \
    file://gst/gst.h;beginline=1;endline=21;md5=e059138481205ee2c6fc1c079c016d0d \
"

# Use i.MX fork of GST for customizations
SRC_URI:remove = "https://gstreamer.freedesktop.org/src/gstreamer/gstreamer-${PV}.tar.xz \
                  file://0001-tests-respect-the-idententaion-used-in-meson.patch \
                  file://0002-tests-add-support-for-install-the-tests.patch \
                  file://0003-tests-use-a-dictionaries-for-environment.patch;striplevel=3 \
                  file://0004-tests-add-helper-script-to-run-the-installed_tests.patch;striplevel=3 \
"
SRC_URI:prepend = "${GST1.0_SRC};branch=${SRCBRANCH} "
GST1.0_SRC ?= "gitsm://github.com/nxp-imx/gstreamer.git;protocol=https"
SRCBRANCH = "MM_04.11.00_2605_L6.18.20"
SRCREV = "c95d8ba246d60779d8ffd7864f97dfa44e8b7386"

PACKAGECONFIG[tests] = "-Dtests=enabled,-Dtests=disabled"

COMPATIBLE_MACHINE = "(imx-nxp-bsp)"
