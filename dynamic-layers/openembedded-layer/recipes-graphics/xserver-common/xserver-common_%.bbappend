# i.MX extra configuration 
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append_imxgpu3d = " \
    file://0016-xserver-common-enable-iglx-module.patch \
"

PACKAGE_ARCH_imxgpu3d = "${MACHINE_SOCARCH}"
