# This recipe is for the i.MX fork of gstreamer1.0-plugins-good. For ease of
# maintenance, the top section is a verbatim copy of an OE-core
# recipe. The second section customizes the recipe for i.MX.

########### OE-core copy ##################
# Upstream hash: 937817e5164f8af8452aec03ae3c45cb23d63df9

require gstreamer1.0-plugins-common.inc

SUMMARY = "'Good' GStreamer plugins"
HOMEPAGE = "https://gstreamer.freedesktop.org/"
BUGTRACKER = "https://gitlab.freedesktop.org/gstreamer/gst-plugins-good/-/issues"

SRC_URI = "https://gstreamer.freedesktop.org/src/gst-plugins-good/gst-plugins-good-${PV}.tar.xz \
           file://0001-qt-include-ext-qt-gstqtgl.h-instead-of-gst-gl-gstglf.patch \
           file://0001-v4l2-Define-ioctl_req_t-for-posix-linux-case.patch"

SRC_URI[sha256sum] = "b67b31313a54c6929b82969d41d3cfdf2f58db573fb5f491e6bba5d84aea0778"

S = "${WORKDIR}/gst-plugins-good-${PV}"

LICENSE = "LGPL-2.1-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=a6f89e2100d9b6cdffcea4f398e37343 \
                    file://gst/replaygain/rganalysis.c;beginline=1;endline=23;md5=b60ebefd5b2f5a8e0cab6bfee391a5fe"

DEPENDS += "gstreamer1.0-plugins-base libcap zlib"
RPROVIDES:${PN}-pulseaudio += "${PN}-pulse"
RPROVIDES:${PN}-soup += "${PN}-souphttpsrc"
RDEPENDS:${PN}-soup += "${MLPREFIX}${@bb.utils.contains('PACKAGECONFIG', 'soup2', 'libsoup-2.4', 'libsoup', d)}"

PACKAGECONFIG_SOUP ?= "soup3"

PACKAGECONFIG ??= " \
    ${GSTREAMER_ORC} \
    ${PACKAGECONFIG_SOUP} \
    ${@bb.utils.filter('DISTRO_FEATURES', 'pulseaudio x11', d)} \
    ${@bb.utils.contains('TUNE_FEATURES', 'm64', 'asm', '', d)} \
    bz2 cairo flac gdk-pixbuf gudev jpeg lame libpng mpg123 speex taglib v4l2 \
"

X11DEPENDS = "virtual/libx11 libsm libxrender libxfixes libxdamage"
X11ENABLEOPTS = "-Dximagesrc=enabled -Dximagesrc-xshm=enabled -Dximagesrc-xfixes=enabled -Dximagesrc-xdamage=enabled"
X11DISABLEOPTS = "-Dximagesrc=disabled -Dximagesrc-xshm=disabled -Dximagesrc-xfixes=disabled -Dximagesrc-xdamage=disabled"

QT5WAYLANDDEPENDS = "${@bb.utils.contains("DISTRO_FEATURES", "wayland", "qtwayland", "", d)}"

PACKAGECONFIG[asm]        = "-Dasm=enabled,-Dasm=disabled,nasm-native"
PACKAGECONFIG[bz2]        = "-Dbz2=enabled,-Dbz2=disabled,bzip2"
PACKAGECONFIG[cairo]      = "-Dcairo=enabled,-Dcairo=disabled,cairo"
PACKAGECONFIG[dv1394]     = "-Ddv1394=enabled,-Ddv1394=disabled,libiec61883 libavc1394 libraw1394"
PACKAGECONFIG[flac]       = "-Dflac=enabled,-Dflac=disabled,flac"
PACKAGECONFIG[gdk-pixbuf] = "-Dgdk-pixbuf=enabled,-Dgdk-pixbuf=disabled,gdk-pixbuf"
PACKAGECONFIG[gtk]        = "-Dgtk3=enabled,-Dgtk3=disabled,gtk+3"
PACKAGECONFIG[gudev]      = "-Dv4l2-gudev=enabled,-Dv4l2-gudev=disabled,libgudev"
PACKAGECONFIG[jack]       = "-Djack=enabled,-Djack=disabled,jack"
PACKAGECONFIG[jpeg]       = "-Djpeg=enabled,-Djpeg=disabled,jpeg"
PACKAGECONFIG[lame]       = "-Dlame=enabled,-Dlame=disabled,lame"
PACKAGECONFIG[libpng]     = "-Dpng=enabled,-Dpng=disabled,libpng"
PACKAGECONFIG[libv4l2]    = "-Dv4l2-libv4l2=enabled,-Dv4l2-libv4l2=disabled,v4l-utils"
PACKAGECONFIG[mpg123]     = "-Dmpg123=enabled,-Dmpg123=disabled,mpg123"
PACKAGECONFIG[pulseaudio] = "-Dpulse=enabled,-Dpulse=disabled,pulseaudio"
PACKAGECONFIG[qt5]        = "-Dqt5=enabled,-Dqt5=disabled,qtbase qtdeclarative qtbase-native ${QT5WAYLANDDEPENDS}"
PACKAGECONFIG[soup2]      = "-Dsoup=enabled,,libsoup-2.4,,,soup3"
PACKAGECONFIG[soup3]      = "-Dsoup=enabled,,libsoup,,,soup2"
PACKAGECONFIG[speex]      = "-Dspeex=enabled,-Dspeex=disabled,speex"
PACKAGECONFIG[rpi]        = "-Drpicamsrc=enabled,-Drpicamsrc=disabled,userland"
PACKAGECONFIG[taglib]     = "-Dtaglib=enabled,-Dtaglib=disabled,taglib"
PACKAGECONFIG[v4l2]       = "-Dv4l2=enabled -Dv4l2-probe=true,-Dv4l2=disabled -Dv4l2-probe=false"
PACKAGECONFIG[vpx]        = "-Dvpx=enabled,-Dvpx=disabled,libvpx"
PACKAGECONFIG[wavpack]    = "-Dwavpack=enabled,-Dwavpack=disabled,wavpack"
PACKAGECONFIG[x11]        = "${X11ENABLEOPTS},${X11DISABLEOPTS},${X11DEPENDS}"

EXTRA_OEMESON += " \
    -Ddoc=disabled \
    -Daalib=disabled \
    -Ddirectsound=disabled \
    -Ddv=disabled \
    -Dlibcaca=disabled \
    -Doss=enabled \
    -Doss4=disabled \
    -Dosxaudio=disabled \
    -Dosxvideo=disabled \
    -Dshout2=disabled \
    -Dtwolame=disabled \
    -Dwaveform=disabled \
"

FILES:${PN}-equalizer += "${datadir}/gstreamer-1.0/presets/*.prs"

########### End of OE-core copy ###########

########### i.MX overrides ################

DEFAULT_PREFERENCE = "-1"

LIC_FILES_CHKSUM = " \
    file://LICENSE.txt;md5=a6f89e2100d9b6cdffcea4f398e37343 \
    file://gst/replaygain/rganalysis.c;beginline=1;endline=23;md5=b60ebefd5b2f5a8e0cab6bfee391a5fe \
"
# Enable pulsesink in gstreamer
PACKAGECONFIG:append = "${@bb.utils.contains('DISTRO_FEATURES', 'pulseaudio', 'pulseaudio', '', d)}"

# fb implementation of v4l2 uses libdrm
DEPENDS += "${@bb.utils.contains('PACKAGECONFIG', 'v4l2', '${DEPENDS_V4L2}', '', d)}"
DEPENDS_V4L2 = "${@bb.utils.contains_any('DISTRO_FEATURES', 'wayland x11', '', 'libdrm', d)}"

SRC_URI:remove = "https://gstreamer.freedesktop.org/src/gst-plugins-good/gst-plugins-good-${PV}.tar.xz \
                file://0001-qt-include-ext-qt-gstqtgl.h-instead-of-gst-gl-gstglf.patch \
                file://0001-v4l2-Define-ioctl_req_t-for-posix-linux-case.patch \
"

SRC_URI:prepend = "${GST1.0-PLUGINS-GOOD_SRC};branch=${SRCBRANCH} "
GST1.0-PLUGINS-GOOD_SRC ?= "gitsm://github.com/nxp-imx/gst-plugins-good.git;protocol=https"
SRCBRANCH = "MM_04.08.03_2312_L6.6.y"
SRCREV = "d361360510c97dc23abbfcdd22dff8214890527d"

# set 32bit compile timer for 32-bit platform
GLIBC_64BIT_TIME_FLAGS:mx6-nxp-bsp = ""
GLIBC_64BIT_TIME_FLAGS:mx7-nxp-bsp = ""
INSANE_SKIP:mx6-nxp-bsp:append = " 32bit-time"
INSANE_SKIP:mx7-nxp-bsp:append = " 32bit-time"

# GCC-14 otherwise errors out
CFLAGS += " \
    -Wno-error=implicit-function-declaration \
    -Wno-error=incompatible-pointer-types \
"
S = "${WORKDIR}/git"

COMPATIBLE_MACHINE = "(imx-nxp-bsp)"

########### End of i.MX overrides #########
