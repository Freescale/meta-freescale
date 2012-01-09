# Append path for freescale layer
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_imx53qsb_append += "file://Xserver-imx53-platform-support.patch"
SRC_URI_imx53ard_append += "file://Xserver-imx53-platform-support.patch"
