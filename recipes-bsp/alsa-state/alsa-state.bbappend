# Append path for freescale layer to include alsa-state asound.conf
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PACKAGE_ARCH_mx6 = "${MACHINE_ARCH}"
PACKAGE_ARCH_mx5 = "${MACHINE_ARCH}"
PACKAGE_ARCH_mxs = "${MACHINE_ARCH}"

