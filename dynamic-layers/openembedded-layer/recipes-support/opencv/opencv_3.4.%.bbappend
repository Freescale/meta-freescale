FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append_mx6sx = " file://0001-MGS-515-ccc-Opencv-app-can-t-run-on-imx6sx-with-cam.patch"

PACKAGECONFIG_remove_imxgpu2d = "v4l"
