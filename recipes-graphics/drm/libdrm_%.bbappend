FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append_imxgpu2d = " file://drm-update-arm.patch"

PACKAGE_ARCH_imxgpu2d = "${MACHINE_SOCARCH}"
