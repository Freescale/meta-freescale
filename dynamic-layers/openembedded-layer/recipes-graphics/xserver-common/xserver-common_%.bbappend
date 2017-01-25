# i.MX extra configuration 
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PATCHES_IMX_SPECIFIC = " file://0016-xserver-common-enable-iglx-module.patch \
"
SRC_URI_append = " \
    ${PATCHES_IMX_SPECIFIC} \
"
PACKAGE_ARCH_mx6 = "${MACHINE_SOCARCH}"
PACKAGE_ARCH_mx7 = "${MACHINE_SOCARCH}"
