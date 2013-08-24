FILESEXTRAPATHS_prepend := "${THISDIR}/qt4:"

SRC_URI_append_mx5 += "file://0001-Add-support-for-i.MX-codecs-to-phonon.patch"
SRC_URI_append_mx6 += " \
	file://0001-Add-support-for-i.MX-codecs-to-phonon.patch \
	file://0002-i.MX-video-renderer-Allow-v4l-device-from-environmen.patch \
"

