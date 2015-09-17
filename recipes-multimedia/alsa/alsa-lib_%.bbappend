FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append_mx6 = " file://0001-add-conf-for-multichannel-support-in-imx.patch"
SRC_URI_append_mx6ul = " file://0001-add-conf-for-multichannel-support-in-imx.patch"
SRC_URI_append_mx7 = " file://0001-add-conf-for-multichannel-support-in-imx.patch"

PACKAGE_ARCH_mx6 = "${MACHINE_SOCARCH}"
PACKAGE_ARCH_mx6ul = "${MACHINE_SOCARCH}"
PACKAGE_ARCH_mx7 = "${MACHINE_SOCARCH}"
