# i.MX extra configuration 
FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:imxgpu3d = " \
    file://0016-xserver-common-enable-iglx-module.patch \
"

PACKAGE_ARCH:imxgpu3d = "${MACHINE_SOCARCH}"
