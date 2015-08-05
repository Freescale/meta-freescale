# Append path for freescale layer to include alsa-state asound.conf
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PACKAGE_ARCH_mx7 = "${MACHINE_ARCH}"
PACKAGE_ARCH_mx6 = "${MACHINE_ARCH}"
