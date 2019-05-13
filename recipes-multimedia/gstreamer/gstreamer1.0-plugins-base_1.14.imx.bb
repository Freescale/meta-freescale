require recipes-multimedia/gstreamer/gstreamer1.0-plugins.inc

LICENSE = "GPLv2+ & LGPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=c54ce9345727175ff66d17b67ff51f58 \
                    file://COPYING.LIB;md5=6762ed442b3822387a51c92d928ead0d \
                    file://common/coverage/coverage-report.pl;beginline=2;endline=17;md5=a4e1830fce078028c8f0974161272607"

DEPENDS += "iso-codes util-linux"

GST1.0-PLUGINS-BASE_SRC ?= "gitsm://source.codeaurora.org/external/imx/gst-plugins-base.git;protocol=https"
SRCBRANCH = "MM_04.04.05_1902_L4.14.98_GA"

SRC_URI = " \
    ${GST1.0-PLUGINS-BASE_SRC};branch=${SRCBRANCH} \
    file://0001-introspection.m4-prefix-pkgconfig-paths-with-PKG_CON.patch \
    file://make-gio_unix_2_0-dependency-configurable.patch \
    file://0001-Makefile.am-don-t-hardcode-libtool-name-when-running.patch \
    file://0002-Makefile.am-prefix-calls-to-pkg-config-with-PKG_CONF.patch \
    file://0003-riff-add-missing-include-directories-when-calling-in.patch \
    file://0004-rtsp-drop-incorrect-reference-to-gstreamer-sdp-in-Ma.patch \
"
SRCREV = "f62f8ab685e549eefaecdb1e700239a5a408fcad"

DEFAULT_PREFERENCE = "-1"

EXTRA_AUTORECONF = ""

S = "${WORKDIR}/git"

# Enable pango lib
PACKAGECONFIG_append = " pango "

# Disable introspection to fix [GstGL-1.0.gir] Error
EXTRA_OECONF_append = " --disable-introspection --disable-opengl --enable-wayland"

inherit gettext use-imx-headers

PACKAGES_DYNAMIC =+ "^libgst.*"

PACKAGECONFIG ??= " \
    ${GSTREAMER_ORC} \
    ${@bb.utils.filter('DISTRO_FEATURES', 'alsa x11', d)} \
    ogg pango theora vorbis \
"

X11DEPENDS = "virtual/libx11 libsm libxrender libxv"
X11ENABLEOPTS = "--enable-x --enable-xvideo --enable-xshm"
X11DISABLEOPTS = "--disable-x --disable-xvideo --disable-xshm"

PACKAGECONFIG[alsa]         = "--enable-alsa,--disable-alsa,alsa-lib"
PACKAGECONFIG[cdparanoia]   = "--enable-cdparanoia,--disable-cdparanoia,cdparanoia"
PACKAGECONFIG[ivorbis]      = "--enable-ivorbis,--disable-ivorbis,tremor"
PACKAGECONFIG[ogg]          = "--enable-ogg,--disable-ogg,libogg"
PACKAGECONFIG[opus]         = "--enable-opus,--disable-opus,libopus"
PACKAGECONFIG[pango]        = "--enable-pango,--disable-pango,pango"
PACKAGECONFIG[theora]       = "--enable-theora,--disable-theora,libtheora"
PACKAGECONFIG[visual]       = "--enable-libvisual,--disable-libvisual,libvisual"
PACKAGECONFIG[vorbis]       = "--enable-vorbis,--disable-vorbis,libvorbis"
PACKAGECONFIG[x11]          = "${X11ENABLEOPTS},${X11DISABLEOPTS},${X11DEPENDS}"

EXTRA_OECONF += " \
    --enable-zlib \
    CPPFLAGS="-I${STAGING_INCDIR_IMX}" \
"

CACHED_CONFIGUREVARS_append_x86 = " ac_cv_header_emmintrin_h=no ac_cv_header_xmmintrin_h=no"

FILES_${MLPREFIX}libgsttag-1.0 += "${datadir}/gst-plugins-base/1.0/license-translations.dict"

do_compile_prepend() {
        export GIR_EXTRA_LIBS_PATH="${B}/gst-libs/gst/tag/.libs:${B}/gst-libs/gst/video/.libs:${B}/gst-libs/gst/audio/.libs:${B}/gst-libs/gst/rtp/.libs"
}

FILES_${PN} += "${libdir}/gstreamer-1.0/include"

COMPATIBLE_MACHINE = "(mx6|mx7|mx8)"
