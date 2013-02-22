# gst-plugins-gl for imx6 Vivante

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
PRINC := "${@int(PRINC) + 1}"

DEPENDS_mx6 += "gst-fsl-plugin"
DEPENDS_${PN}_mx6 += "gpu-viv-bin-mx6q"
RDEPENDS_${PN}_mx6 += "libglslc-mx6"

SRC_URI_append_mx6 = " \
   file://0001-freescale-mx6-release-1.1.0.patch \
   file://0002-remove-deprecated-glib-semaphores.patch \
   "
