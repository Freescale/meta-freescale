# This recipe is for the i.MX fork of gstreamer1.0-plugins-base. For ease of
# maintenance, the top section is a verbatim copy of an OE-core
# recipe. The second section customizes the recipe for i.MX.

########### OE-core copy ##################
# Upstream hash: a21649109374fde44cf77de845cfb3cb6cbfb138

require recipes-multimedia/gstreamer/gstreamer1.0-plugins-common.inc

DESCRIPTION = "'Base' GStreamer plugins and helper libraries"
HOMEPAGE = "https://gstreamer.freedesktop.org/"
BUGTRACKER = "https://gitlab.freedesktop.org/gstreamer/gst-plugins-base/-/issues"
LICENSE = "LGPL-2.1-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=69333daa044cb77e486cc36129f7a770"

SRC_URI = "https://gstreamer.freedesktop.org/src/gst-plugins-base/gst-plugins-base-${PV}.tar.xz \
           file://0001-ENGR00312515-get-caps-from-src-pad-when-query-caps.patch \
           file://0003-viv-fb-Make-sure-config.h-is-included.patch \
           file://0002-ssaparse-enhance-SSA-text-lines-parsing.patch \
           "
SRC_URI[sha256sum] = "96d8a6413ba9394fbec1217aeef63741a729d476a505a797c1d5337d8fa7c204"

S = "${WORKDIR}/gst-plugins-base-${PV}"

DEPENDS += "iso-codes util-linux zlib"

inherit gobject-introspection

PACKAGES_DYNAMIC =+ "^libgst.*"

# opengl packageconfig factored out to make it easy for distros
# and BSP layers to choose OpenGL APIs/platforms/window systems
PACKAGECONFIG_GL ?= "${@bb.utils.contains('DISTRO_FEATURES', 'opengl', 'gles2 egl', '', d)}"

PACKAGECONFIG ??= " \
    ${GSTREAMER_ORC} \
    ${PACKAGECONFIG_GL} \
    ${@bb.utils.filter('DISTRO_FEATURES', 'alsa x11', d)} \
    jpeg ogg pango png theora vorbis \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'wayland egl', '', d)} \
"

OPENGL_APIS = 'opengl gles2'
OPENGL_PLATFORMS = 'egl'

X11DEPENDS = "virtual/libx11 libsm libxrender libxv"
X11ENABLEOPTS = "-Dx11=enabled -Dxvideo=enabled -Dxshm=enabled"
X11DISABLEOPTS = "-Dx11=disabled -Dxvideo=disabled -Dxshm=disabled"

PACKAGECONFIG[alsa]         = "-Dalsa=enabled,-Dalsa=disabled,alsa-lib"
PACKAGECONFIG[cdparanoia]   = "-Dcdparanoia=enabled,-Dcdparanoia=disabled,cdparanoia"
PACKAGECONFIG[graphene]     = "-Dgl-graphene=enabled,-Dgl-graphene=disabled,graphene"
PACKAGECONFIG[jpeg]         = "-Dgl-jpeg=enabled,-Dgl-jpeg=disabled,jpeg"
PACKAGECONFIG[ogg]          = "-Dogg=enabled,-Dogg=disabled,libogg"
PACKAGECONFIG[opus]         = "-Dopus=enabled,-Dopus=disabled,libopus"
PACKAGECONFIG[pango]        = "-Dpango=enabled,-Dpango=disabled,pango"
PACKAGECONFIG[png]          = "-Dgl-png=enabled,-Dgl-png=disabled,libpng"
PACKAGECONFIG[qt5]          = "-Dqt5=enabled,-Dqt5=disabled,qtbase qtdeclarative qtbase-native"
PACKAGECONFIG[theora]       = "-Dtheora=enabled,-Dtheora=disabled,libtheora"
PACKAGECONFIG[tremor]       = "-Dtremor=enabled,-Dtremor=disabled,tremor"
PACKAGECONFIG[visual]       = "-Dlibvisual=enabled,-Dlibvisual=disabled,libvisual"
PACKAGECONFIG[vorbis]       = "-Dvorbis=enabled,-Dvorbis=disabled,libvorbis"
PACKAGECONFIG[x11]          = "${X11ENABLEOPTS},${X11DISABLEOPTS},${X11DEPENDS}"

# OpenGL API packageconfigs
PACKAGECONFIG[opengl]       = ",,virtual/libgl libglu"
PACKAGECONFIG[gles2]        = ",,virtual/libgles2"

# OpenGL platform packageconfigs
PACKAGECONFIG[egl]          = ",,virtual/egl"

# OpenGL window systems (except for X11)
PACKAGECONFIG[gbm]          = ",,virtual/libgbm libgudev libdrm"
PACKAGECONFIG[wayland]      = ",,wayland-native wayland wayland-protocols libdrm"
PACKAGECONFIG[dispmanx]     = ",,virtual/libomxil"
PACKAGECONFIG[viv-fb]       = ",,virtual/libgles2 virtual/libg2d"

OPENGL_WINSYS = "${@bb.utils.filter('PACKAGECONFIG', 'x11 gbm wayland dispmanx egl viv-fb', d)}"

EXTRA_OEMESON += " \
    -Ddoc=disabled \
    ${@get_opengl_cmdline_list('gl_api', d.getVar('OPENGL_APIS'), d)} \
    ${@get_opengl_cmdline_list('gl_platform', d.getVar('OPENGL_PLATFORMS'), d)} \
    ${@get_opengl_cmdline_list('gl_winsys', d.getVar('OPENGL_WINSYS'), d)} \
"

FILES:${PN}-dev += "${libdir}/gstreamer-1.0/include/gst/gl/gstglconfig.h"
FILES:${MLPREFIX}libgsttag-1.0 += "${datadir}/gst-plugins-base/1.0/license-translations.dict"

def get_opengl_cmdline_list(switch_name, options, d):
    selected_options = []
    if bb.utils.contains('DISTRO_FEATURES', 'opengl', True, False, d):
        for option in options.split():
            if bb.utils.contains('PACKAGECONFIG', option, True, False, d):
                selected_options += [option]
    if selected_options:
        return '-D' + switch_name + '=' + ','.join(selected_options)
    else:
        return ''

CVE_PRODUCT += "gst-plugins-base"

########### End of OE-core copy ###########

########### i.MX overrides ################

DEFAULT_PREFERENCE = "-1"

SRC_URI:remove = " \
    https://gstreamer.freedesktop.org/src/gst-plugins-base/gst-plugins-base-${PV}.tar.xz \
    file://0001-ENGR00312515-get-caps-from-src-pad-when-query-caps.patch \
    file://0003-viv-fb-Make-sure-config.h-is-included.patch \
    file://0002-ssaparse-enhance-SSA-text-lines-parsing.patch"
SRC_URI:prepend = "${GST1.0-PLUGINS-BASE_SRC};branch=${SRCBRANCH} "
SRC_URI += "file://0001-gstallocatorphymem.c-Typecast-result-of-gst_phymem_g.patch"
GST1.0-PLUGINS-BASE_SRC ?= "gitsm://github.com/nxp-imx/gst-plugins-base.git;protocol=https"
SRCBRANCH = "MM_04.07.02_2210_L5.15.y"
SRCREV = "cbf542ce3e0bad1009d5ecf72707e870c375c3f0"

S = "${WORKDIR}/git"

inherit use-imx-headers

PACKAGECONFIG_GL:imxgpu2d = \
    "${@bb.utils.contains('DISTRO_FEATURES', 'opengl x11', 'opengl viv-fb', '', d)}"
PACKAGECONFIG_GL:imxgpu3d = \
    "${@bb.utils.contains('DISTRO_FEATURES', 'opengl', 'gles2 egl viv-fb', '', d)}"
PACKAGECONFIG_GL:use-mainline-bsp = \
    "${@bb.utils.contains('DISTRO_FEATURES', 'opengl', 'gles2 egl gbm', '', d)}"

PACKAGECONFIG_REMOVE ?= "jpeg"
PACKAGECONFIG:remove = "${PACKAGECONFIG_REMOVE}"
PACKAGECONFIG:append:imxgpu2d = " g2d"

PACKAGECONFIG[g2d] = ",,virtual/libg2d"
PACKAGECONFIG[viv-fb] = ",,virtual/libgles2"

EXTRA_OEMESON += "-Dc_args="${CFLAGS} -I${STAGING_INCDIR_IMX}""

# links with imx-gpu libs which are pre-built for glibc
# gcompat will address it during runtime
LDFLAGS:append:imxgpu:libc-musl = " -Wl,--allow-shlib-undefined"

COMPATIBLE_MACHINE = "(imx-nxp-bsp)"

########### End of i.MX overrides #########
