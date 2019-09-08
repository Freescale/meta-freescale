require recipes-multimedia/gstreamer/gstreamer1.0-plugins.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=73a5855a8119deb017f5f13cf327095d \
                    file://COPYING.LIB;md5=21682e4e8fea52413fd26c60acb907e5 "

DEPENDS_append_imxgpu2d = " virtual/libg2d"
DEPENDS_append_mx8 = " libdrm"

PACKAGECONFIG_append_mx6q = " opencv"
PACKAGECONFIG_append_mx6qp = " opencv"
PACKAGECONFIG_append_mx8 = " opencv kms"

PACKAGECONFIG[wayland] = "--enable-wayland --disable-x11,--disable-wayland,wayland-native wayland wayland-protocols libdrm"

# Disable introspection to fix [GstGL-1.0.gir] Error
EXTRA_OECONF_append = " --disable-introspection"


GST1.0-PLUGINS-BAD_SRC ?= "gitsm://source.codeaurora.org/external/imx/gst-plugins-bad.git;protocol=https"
SRCBRANCH = "MM_04.04.05_1902_L4.14.98_GA"

SRC_URI = " \
    ${GST1.0-PLUGINS-BAD_SRC};branch=${SRCBRANCH} \
    file://configure-allow-to-disable-libssh2.patch \
    file://0001-Makefile.am-don-t-hardcode-libtool-name-when-running.patch \
"

SRCREV = "0191521ba226904e4b2f84c38e5f6ae75169a18a"

DEFAULT_PREFERENCE = "-1"

# This remove "--exclude=autopoint" option from autoreconf argument to avoid
# configure.ac:30: error: required file './ABOUT-NLS' not found
EXTRA_AUTORECONF = ""

# include fragment shaders
FILES_${PN}-opengl += "/usr/share/*.fs"

PACKAGE_ARCH_imxpxp = "${MACHINE_SOCARCH}"
PACKAGE_ARCH_mx8 = "${MACHINE_SOCARCH}"

S = "${WORKDIR}/git"

LICENSE = "GPLv2+ & LGPLv2+ & LGPLv2.1+"

DEPENDS += "gstreamer1.0-plugins-base jpeg"

inherit gettext

PACKAGECONFIG ??= " \
    ${GSTREAMER_ORC} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'bluetooth', 'bluez', '', d)} \
    ${@bb.utils.filter('DISTRO_FEATURES', 'directfb vulkan', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'wayland', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'opengl', 'gl', '', d)} \
    bz2 curl dash dtls hls rsvg sbc smoothstreaming sndfile ttml uvch264 webp \
"

# the gl packageconfig enables OpenGL elements that haven't been ported
# to -base yet. They depend on the gstgl library in -base, so we do
# not add GL dependencies here, since these are taken care of in -base.

PACKAGECONFIG[assrender]       = "--enable-assrender,--disable-assrender,libass"
PACKAGECONFIG[bluez]           = "--enable-bluez,--disable-bluez,bluez5"
PACKAGECONFIG[bz2]             = "--enable-bz2,--disable-bz2,bzip2"
PACKAGECONFIG[curl]            = "--enable-curl,--disable-curl,curl"
PACKAGECONFIG[dash]            = "--enable-dash,--disable-dash,libxml2"
PACKAGECONFIG[dc1394]          = "--enable-dc1394,--disable-dc1394,libdc1394"
PACKAGECONFIG[directfb]        = "--enable-directfb,--disable-directfb,directfb"
PACKAGECONFIG[dtls]            = "--enable-dtls,--disable-dtls,openssl"
PACKAGECONFIG[faac]            = "--enable-faac,--disable-faac,faac"
PACKAGECONFIG[faad]            = "--enable-faad,--disable-faad,faad2"
PACKAGECONFIG[flite]           = "--enable-flite,--disable-flite,flite-alsa"
PACKAGECONFIG[fluidsynth]      = "--enable-fluidsynth,--disable-fluidsynth,fluidsynth"
PACKAGECONFIG[hls]             = "--enable-hls --with-hls-crypto=nettle,--disable-hls,nettle"
PACKAGECONFIG[gl]              = "--enable-gl,--disable-gl,"
PACKAGECONFIG[kms]             = "--enable-kms,--disable-kms,libdrm"
PACKAGECONFIG[libde265]        = "--enable-libde265,--disable-libde265,libde265"
PACKAGECONFIG[libmms]          = "--enable-libmms,--disable-libmms,libmms"
PACKAGECONFIG[libssh2]         = "--enable-libssh2,--disable-libssh2,libssh2"
PACKAGECONFIG[modplug]         = "--enable-modplug,--disable-modplug,libmodplug"
PACKAGECONFIG[neon]            = "--enable-neon,--disable-neon,neon"
PACKAGECONFIG[openal]          = "--enable-openal,--disable-openal,openal-soft"
PACKAGECONFIG[opencv]          = "--enable-opencv,--disable-opencv,opencv"
PACKAGECONFIG[openh264]        = "--enable-openh264,--disable-openh264,openh264"
PACKAGECONFIG[openjpeg]        = "--enable-openjpeg,--disable-openjpeg,openjpeg"
# the opus encoder/decoder elements are now in the -base package,
# but the opus parser remains in -bad
PACKAGECONFIG[opusparse]       = "--enable-opus,--disable-opus,libopus"
PACKAGECONFIG[resindvd]        = "--enable-resindvd,--disable-resindvd,libdvdread libdvdnav"
PACKAGECONFIG[rsvg]            = "--enable-rsvg,--disable-rsvg,librsvg"
PACKAGECONFIG[rtmp]            = "--enable-rtmp,--disable-rtmp,rtmpdump"
PACKAGECONFIG[sbc]             = "--enable-sbc,--disable-sbc,sbc"
PACKAGECONFIG[smoothstreaming] = "--enable-smoothstreaming,--disable-smoothstreaming,libxml2"
PACKAGECONFIG[sndfile]         = "--enable-sndfile,--disable-sndfile,libsndfile1"
PACKAGECONFIG[srtp]            = "--enable-srtp,--disable-srtp,libsrtp"
PACKAGECONFIG[tinyalsa]        = "--enable-tinyalsa,--disable-tinyalsa,tinyalsa"
PACKAGECONFIG[ttml]            = "--enable-ttml,--disable-ttml,libxml2 pango cairo"
PACKAGECONFIG[uvch264]         = "--enable-uvch264,--disable-uvch264,libusb1 libgudev"
PACKAGECONFIG[voaacenc]        = "--enable-voaacenc,--disable-voaacenc,vo-aacenc"
PACKAGECONFIG[voamrwbenc]      = "--enable-voamrwbenc,--disable-voamrwbenc,vo-amrwbenc"
PACKAGECONFIG[vulkan]          = "--enable-vulkan,--disable-vulkan,vulkan-loader"
PACKAGECONFIG[wayland]         = "--enable-wayland,--disable-wayland,wayland-native wayland wayland-protocols libdrm"
PACKAGECONFIG[webp]            = "--enable-webp,--disable-webp,libwebp"
PACKAGECONFIG[webrtc]          = "--enable-webrtc,--disable-webrtc,libnice"
PACKAGECONFIG[webrtcdsp]       = "--enable-webrtcdsp,--disable-webrtcdsp,webrtc-audio-processing"

# these plugins have no corresponding library in OE-core or meta-openembedded:
#   openni2 winks direct3d directsound winscreencap acm apple_media iqa
#   android_media avc bs2b chromaprint daala dts fdkaac gme gsm kate ladspa
#   lv2 mpeg2enc mplex msdk musepack nvenc ofa openmpt opensles soundtouch
#   spandsp spc teletextdec vdpau wasapi x265 zbar

EXTRA_OECONF += " \
    --enable-decklink \
    --enable-dvb \
    --enable-fbdev \
    --enable-ipcpipeline \
    --enable-netsim \
    --enable-shm \
    --enable-vcd \
    --disable-acm \
    --disable-android_media \
    --disable-aom \
    --disable-apple_media \
    --disable-avc \
    --disable-bs2b \
    --disable-chromaprint \
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
    --disable-lv2 \
    --disable-mpeg2enc \
    --disable-mplex \
    --disable-msdk \
    --disable-musepack \
    --disable-nvenc \
    --disable-ofa \
    --disable-openexr \
    --disable-openmpt \
    --disable-openni2 \
    --disable-opensles \
    --disable-soundtouch \
    --disable-spandsp \
    --disable-spc \
    --disable-srt \
    --disable-teletextdec \
    --disable-vdpau \
    --disable-wasapi \
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

FILES_${PN}-freeverb += "${datadir}/gstreamer-${LIBV}/presets/GstFreeverb.prs"
FILES_${PN}-opencv += "${datadir}/gst-plugins-bad/${LIBV}/opencv*"
FILES_${PN}-voamrwbenc += "${datadir}/gstreamer-${LIBV}/presets/GstVoAmrwbEnc.prs"

COMPATIBLE_MACHINE = "(mx6|mx7|mx8)"
