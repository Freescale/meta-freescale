# Freescale gstplaybin2 rawvideo support

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append_mxs = " file://gstplaybin2-rawvideo-support.patch"
SRC_URI_append_mx5 = " file://gstplaybin2-rawvideo-support.patch"
SRC_URI_append_mx6 = " file://gstplaybin2-rawvideo-support.patch"

PACKAGE_ARCH_mxs = "${MACHINE_SOCARCH}"
PACKAGE_ARCH_mx5 = "${MACHINE_SOCARCH}"
PACKAGE_ARCH_mx6 = "${MACHINE_SOCARCH}"
