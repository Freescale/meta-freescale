DEPENDS_append_mx6 += " \
    libimxvpuapi \
"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append_mx6 = " \
    file://0001-imxvpu-video-decode-accelerator.patch;patchdir=src/3rdparty \
"

PACKAGECONFIG_append_mx6 += " proprietary-codecs webrtc"
