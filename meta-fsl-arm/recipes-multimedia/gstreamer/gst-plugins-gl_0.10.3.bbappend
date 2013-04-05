# gst-plugins-gl for imx6 Vivante

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
PRINC := "${@int(PRINC) + 3}"

DEPENDS_append_mx6 = " gst-fsl-plugin gpu-viv-bin-mx6q"

SRC_URI_append_mx6 = " file://0001-freescale-mx6-release-1.1.0.patch"

CFLAGS_append_mx6 = " -DGLIB_DISABLE_DEPRECATION_WARNINGS"
