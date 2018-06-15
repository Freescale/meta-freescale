require recipes-multimedia/gstreamer/gstreamer1.0-plugins.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=73a5855a8119deb017f5f13cf327095d \
                    file://COPYING.LIB;md5=21682e4e8fea52413fd26c60acb907e5 "

DEPENDS_append_imxgpu2d = " virtual/libg2d"
DEPENDS_append_mx8 = " libdrm"

PACKAGECONFIG_GL_imxgpu2d = "${@bb.utils.contains('DISTRO_FEATURES', 'opengl x11', 'opengl', '', d)}"
PACKAGECONFIG_GL_imxgpu3d = "${@bb.utils.contains('DISTRO_FEATURES', 'opengl', 'gles2', '', d)}"

PACKAGECONFIG_append_mx6q = " opencv"
PACKAGECONFIG_append_mx6qp = " opencv"
PACKAGECONFIG_append_mx8 = " opencv kms"
PACKAGECONFIG_remove_mx6sl = " gles2"

#revert poky fido commit:cdc2c8aeaa96b07dfc431a4cf0bf51ef7f8802a3: move EGL to Wayland
PACKAGECONFIG[gles2]   = "--enable-gles2 --enable-egl,--disable-gles2 --disable-egl,virtual/libgles2 virtual/egl"
PACKAGECONFIG[wayland] = "--enable-wayland --disable-x11,--disable-wayland,wayland-native wayland wayland-protocols libdrm"

# Disable introspection to fix [GstGL-1.0.gir] Error
EXTRA_OECONF_append = " --disable-introspection"

EXTRA_OECONF_remove = " --disable-sdl --disable-nas --disable-libvisual --disable-xvid --disable-mimic \
                        --disable-pvr --disable-sdltest --disable-wininet --disable-timidity \
                        --disable-linsys --disable-sndio --disable-apexsink --disable-libssh2 \
"

GST1.0-PLUGINS-BAD_SRC ?= "gitsm://source.codeaurora.org/external/imx/gst-plugins-bad.git;protocol=https"
SRCBRANCH = "MM_04.03.05_1804_L4.9.88_MX7ULP_GA"

SRC_URI = " \
    ${GST1.0-PLUGINS-BAD_SRC};branch=${SRCBRANCH} \
    file://0001-Makefile.am-don-t-hardcode-libtool-name-when-running.patch \
"

SRCREV = "3bf09ef9cda8220b53459b45fe5384a99a7b1c6b"

DEFAULT_PREFERENCE = "-1"

# This remove "--exclude=autopoint" option from autoreconf argument to avoid
# configure.ac:30: error: required file './ABOUT-NLS' not found
EXTRA_AUTORECONF = ""

# include fragment shaders
FILES_${PN}-opengl += "/usr/share/*.fs"

PACKAGE_ARCH_imxpxp = "${MACHINE_SOCARCH}"
PACKAGE_ARCH_mx8 = "${MACHINE_SOCARCH}"

# Fix libgstbadion-1.0.so.0 which is under built directory cannot be found
do_compile_prepend () {
    export GIR_EXTRA_LIBS_PATH="${B}/gst-libs/gst/ion/.libs"
}

S = "${WORKDIR}/git"

LICENSE = "GPLv2+ & LGPLv2+ & LGPLv2.1+"

DEPENDS += "gstreamer1.0-plugins-base libpng jpeg"

inherit gettext bluetooth

# opengl packageconfig factored out to make it easy for distros
# and BSP layers to pick either (desktop) opengl, gles2, or no GL
PACKAGECONFIG_GL ?= "${@bb.utils.contains('DISTRO_FEATURES', 'opengl', 'gles2 egl', '', d)}"

# gtk is not in the PACKAGECONFIG variable by default until
# the transition to gtk+3 is finished
PACKAGECONFIG ??= " \
    ${GSTREAMER_ORC} \
    ${PACKAGECONFIG_GL} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'bluetooth', 'bluez', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'directfb', 'directfb', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'wayland egl', '', d)} \
    bz2 curl dash dtls hls rsvg sbc smoothstreaming sndfile uvch264 webp \
"
PACKAGECONFIG[assrender]       = "--enable-assrender,--disable-assrender,libass"
PACKAGECONFIG[bluez]           = "--enable-bluez,--disable-bluez,${BLUEZ}"
PACKAGECONFIG[bz2]             = "--enable-bz2,--disable-bz2,bzip2"
PACKAGECONFIG[curl]            = "--enable-curl,--disable-curl,curl"
PACKAGECONFIG[dash]            = "--enable-dash,--disable-dash,libxml2"
PACKAGECONFIG[dc1394]          = "--enable-dc1394,--disable-dc1394,libdc1394"
PACKAGECONFIG[directfb]        = "--enable-directfb,--disable-directfb,directfb"
PACKAGECONFIG[dtls]            = "--enable-dtls,--disable-dtls,openssl"
PACKAGECONFIG[egl]             = "--enable-egl,--disable-egl,virtual/egl"
PACKAGECONFIG[faac]            = "--enable-faac,--disable-faac,faac"
PACKAGECONFIG[faad]            = "--enable-faad,--disable-faad,faad2"
PACKAGECONFIG[flite]           = "--enable-flite,--disable-flite,flite-alsa"
PACKAGECONFIG[fluidsynth]      = "--enable-fluidsynth,--disable-fluidsynth,fluidsynth"
PACKAGECONFIG[gles2]           = "--enable-gles2,--disable-gles2,virtual/libgles2"
PACKAGECONFIG[gtk]             = "--enable-gtk3,--disable-gtk3,gtk+3"
PACKAGECONFIG[hls]             = "--enable-hls --with-hls-crypto=nettle,--disable-hls,nettle"
PACKAGECONFIG[kms]             = "--enable-kms,--disable-kms,libdrm"
PACKAGECONFIG[libmms]          = "--enable-libmms,--disable-libmms,libmms"
PACKAGECONFIG[libssh2]         = "--enable-libssh2,--disable-libssh2,libssh2"
PACKAGECONFIG[modplug]         = "--enable-modplug,--disable-modplug,libmodplug"
PACKAGECONFIG[neon]            = "--enable-neon,--disable-neon,neon"
PACKAGECONFIG[openal]          = "--enable-openal,--disable-openal,openal-soft"
PACKAGECONFIG[opencv]          = "--enable-opencv,--disable-opencv,opencv"
PACKAGECONFIG[opengl]          = "--enable-opengl,--disable-opengl,virtual/libgl libglu"
PACKAGECONFIG[openjpeg]        = "--enable-openjpeg,--disable-openjpeg,openjpeg"
# the opus encoder/decoder elements are now in the -base package,
# but the opus parser remains in -bad
PACKAGECONFIG[opusparse]       = "--enable-opus,--disable-opus,libopus"
PACKAGECONFIG[resindvd]        = "--enable-resindvd,--disable-resindvd,libdvdread libdvdnav"
PACKAGECONFIG[rsvg]            = "--enable-rsvg,--disable-rsvg,librsvg"
PACKAGECONFIG[rtmp]            = "--enable-rtmp,--disable-rtmp,rtmpdump"
PACKAGECONFIG[sbc]             = "--enable-sbc,--disable-sbc,sbc"
PACKAGECONFIG[schroedinger]    = "--enable-schro,--disable-schro,schroedinger"
PACKAGECONFIG[smoothstreaming] = "--enable-smoothstreaming,--disable-smoothstreaming,libxml2"
PACKAGECONFIG[sndfile]         = "--enable-sndfile,--disable-sndfile,libsndfile1"
PACKAGECONFIG[srtp]            = "--enable-srtp,--disable-srtp,libsrtp"
PACKAGECONFIG[uvch264]         = "--enable-uvch264,--disable-uvch264,libusb1 libgudev"
PACKAGECONFIG[voaacenc]        = "--enable-voaacenc,--disable-voaacenc,vo-aacenc"
PACKAGECONFIG[voamrwbenc]      = "--enable-voamrwbenc,--disable-voamrwbenc,vo-amrwbenc"
PACKAGECONFIG[wayland]         = "--enable-wayland,--disable-wayland,wayland-native wayland wayland-protocols libdrm"
PACKAGECONFIG[webp]            = "--enable-webp,--disable-webp,libwebp"

# these plugins have no corresponding library in OE-core or meta-openembedded:
#   openni2 winks direct3d directsound winscreencap acm apple_media iqa
#   android_media avc bs2b chromaprint daala dts fdkaac gme gsm kate ladspa libde265
#   lv2 mpeg2enc mplex msdk musepack nvenc ofa openh264 opensles soundtouch spandsp
#   spc teletextdec tinyalsa vdpau wasapi x265 zbar webrtcdsp

# qt5 support is disabled, because it is not present in OE core, and requires more work than
# just adding a packageconfig (it requires access to moc, uic, rcc, and qmake paths).
# This is better done in a separate qt5 layer (which then should add a "qt5" packageconfig
# in a gstreamer1.0-plugins-bad bbappend).

EXTRA_OECONF += " \
    --enable-decklink \
    --enable-dvb \
    --enable-fbdev \
    --enable-netsim \
    --enable-shm \
    --enable-vcd \
    --disable-acm \
    --disable-android_media \
    --disable-apple_media \
    --disable-avc \
    --disable-bs2b \
    --disable-chromaprint \
    --disable-cocoa \
    --disable-daala \
    --disable-direct3d \
    --disable-directsound \
    --disable-dts \
    --disable-fdk_aac \
    --disable-gme \
    --disable-gsm \
    --disable-iqa \
    --disable-kate \
    --disable-ladspa \
    --disable-libde265 \
    --disable-lv2 \
    --disable-mpeg2enc \
    --disable-mplex \
    --disable-msdk \
    --disable-musepack \
    --disable-nvenc \
    --disable-ofa \
    --disable-openexr \
    --disable-openh264 \
    --disable-openni2 \
    --disable-opensles \
    --disable-qt \
    --disable-soundtouch \
    --disable-spandsp \
    --disable-spc \
    --disable-teletextdec \
    --disable-tinyalsa \
    --disable-vdpau \
    --disable-vulkan \
    --disable-wasapi \
    --disable-webrtcdsp \
    --disable-wildmidi \
    --disable-winks \
    --disable-winscreencap \
    --disable-x265 \
    --disable-zbar \
    ${@bb.utils.contains("TUNE_FEATURES", "mx32", "--disable-yadif", "", d)} \
"

export OPENCV_PREFIX = "${STAGING_DIR_TARGET}${prefix}"

ARM_INSTRUCTION_SET_armv4 = "arm"
ARM_INSTRUCTION_SET_armv5 = "arm"

FILES_${PN}-dev += "${libdir}/gstreamer-${LIBV}/include/gst/gl/gstglconfig.h"
FILES_${PN}-freeverb += "${datadir}/gstreamer-${LIBV}/presets/GstFreeverb.prs"
FILES_${PN}-opencv += "${datadir}/gst-plugins-bad/${LIBV}/opencv*"
FILES_${PN}-voamrwbenc += "${datadir}/gstreamer-${LIBV}/presets/GstVoAmrwbEnc.prs"

do_compile_prepend() {
    export GIR_EXTRA_LIBS_PATH="${B}/gst-libs/gst/allocators/.libs"
}

COMPATIBLE_MACHINE = "(mx6|mx7|mx8)"
