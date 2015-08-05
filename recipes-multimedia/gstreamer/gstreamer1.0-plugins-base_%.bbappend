
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append_mx6 = " file://gstplaybin-enable-native-video.patch"

PACKAGE_ARCH_mx6 = "${MACHINE_SOCARCH}"
