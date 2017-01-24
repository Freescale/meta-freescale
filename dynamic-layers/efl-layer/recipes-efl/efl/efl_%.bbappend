FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append_imxgpu2d = " file://0001-Add-preprocessor-definitions-for-Vivante-GLES-header.patch"
