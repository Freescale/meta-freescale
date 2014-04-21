# gst-plugins-gl for imx6 Vivante

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

DEPENDS_append_mx6 = " gst-fsl-plugin gpu-viv-bin-mx6q"

SRC_URI_append_mx6 = " file://IMX_MMCODEC_3.0.35_4.0.0.patch"

CFLAGS_append_mx6 = " -DGLIB_DISABLE_DEPRECATION_WARNINGS -UG_DISABLE_DEPRECATED"

PACKAGE_ARCH_mx6 = "${MACHINE_ARCH}"
