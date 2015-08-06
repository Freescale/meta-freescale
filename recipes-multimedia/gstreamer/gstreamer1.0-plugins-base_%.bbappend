FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append_mx6 = " file://gstplaybin-remove-flag-deinterlace.patch"
SRC_URI_append_mx7 = " file://gstplaybin-remove-flag-deinterlace.patch"

PACKAGE_ARCH_mx6 = "${MACHINE_SOCARCH}"
PACKAGE_ARCH_mx7 = "${MACHINE_SOCARCH}"
