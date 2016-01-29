FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

IMX_PATCHES = " file://gstplaybin-remove-flag-deinterlace.patch \
                file://0001-MMFMWK-7030-Linux_MX6QP_ARD-IMXCameraApp-When-Enable.patch \
"

SRC_URI_append_mx6 = "${IMX_PATCHES}"
SRC_URI_append_mx7 = "${IMX_PATCHES}"

PACKAGE_ARCH_mx6 = "${MACHINE_SOCARCH}"
PACKAGE_ARCH_mx7 = "${MACHINE_SOCARCH}"
