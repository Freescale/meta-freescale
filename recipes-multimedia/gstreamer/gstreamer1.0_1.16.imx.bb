SUMMARY = "GStreamer 1.0 multimedia framework"
DESCRIPTION = "GStreamer is a multimedia framework for encoding and decoding video and sound. \
It supports a wide range of formats including mp3, ogg, avi, mpeg and quicktime."
HOMEPAGE = "http://gstreamer.freedesktop.org/"
BUGTRACKER = "https://bugzilla.gnome.org/enter_bug.cgi?product=Gstreamer"
SECTION = "multimedia"
LICENSE = "LGPLv2+"

DEPENDS = "glib-2.0 glib-2.0-native libcap libxml2 bison-native flex-native elfutils"

inherit autotools pkgconfig gettext upstream-version-is-even gobject-introspection gtk-doc

# This way common/m4/introspection.m4 will come first
# (it has a custom INTROSPECTION_INIT macro, and so must be used instead of our common introspection.m4 file)
acpaths = "-I ${S}/common/m4 -I ${S}/m4"

LIC_FILES_CHKSUM = "file://COPYING;md5=6762ed442b3822387a51c92d928ead0d \
                    file://gst/gst.h;beginline=1;endline=21;md5=e059138481205ee2c6fc1c079c016d0d"

# Use i.MX fork of GST for customizations
GST1.0_SRC ?= "gitsm://source.codeaurora.org/external/imx/gstreamer.git;protocol=https"
SRCBRANCH = "MM_04.05.01_1909_L4.19.35"

SRC_URI = " \
    ${GST1.0_SRC};branch=${SRCBRANCH} \
    file://0001-introspection.m4-prefix-pkgconfig-paths-with-PKG_CON.patch \
    file://gtk-doc-tweaks.patch \
    file://0001-gst-gstpluginloader.c-when-env-var-is-set-do-not-fal.patch \
"
SRCREV = "a4c220605ac0923596b89f4f07c05d235bc09259"

DEFAULT_PREFERENCE = "-1"

EXTRA_AUTORECONF = ""

PACKAGECONFIG ??= ""

PACKAGECONFIG[debug] = "--enable-debug,--disable-debug"
PACKAGECONFIG[tests] = "--enable-tests,--disable-tests"
PACKAGECONFIG[valgrind] = "--enable-valgrind,--disable-valgrind,valgrind,"
PACKAGECONFIG[gst-tracer-hooks] = "--enable-gst-tracer-hooks,--disable-gst-tracer-hooks,"

EXTRA_OECONF = " \
    --disable-dependency-tracking \
    --disable-examples \
"

CACHED_CONFIGUREVARS += "ac_cv_header_valgrind_valgrind_h=no"

# musl libc generates warnings if <sys/poll.h> is included directly
CACHED_CONFIGUREVARS += "ac_cv_header_sys_poll_h=no"

PACKAGES += "${PN}-bash-completion"

FILES_${PN} += "${libdir}/gstreamer-1.0/*.so"
FILES_${PN}-dev += "${libdir}/gstreamer-1.0/*.la ${libdir}/gstreamer-1.0/*.a ${libdir}/gstreamer-1.0/include"
FILES_${PN}-bash-completion += "${datadir}/bash-completion/completions/ ${datadir}/bash-completion/helpers/gst*"

RRECOMMENDS_${PN}_qemux86 += "kernel-module-snd-ens1370 kernel-module-snd-rawmidi"
RRECOMMENDS_${PN}_qemux86-64 += "kernel-module-snd-ens1370 kernel-module-snd-rawmidi"

delete_pkg_m4_file() {
        # This m4 file is out of date and is missing PKG_CONFIG_SYSROOT_PATH tweaks which we need for introspection
        rm "${S}/common/m4/pkg.m4" || true
        rm -f "${S}/common/m4/gtk-doc.m4"
}

do_configure[prefuncs] += "delete_pkg_m4_file"

do_compile_prepend() {
        export GIR_EXTRA_LIBS_PATH="${B}/gst/.libs:${B}/libs/gst/base/.libs"
}

S = "${WORKDIR}/git"

COMPATIBLE_MACHINE = "(mx6|mx7|mx8)"
