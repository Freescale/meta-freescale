# Append path for freescale layer to include alsa-state asound.conf
FILESEXTRAPATHS_prepend_mx6 := "${THISDIR}/${PN}/imx:"
FILESEXTRAPATHS_prepend_mx6ul := "${THISDIR}/${PN}/imx:"
FILESEXTRAPATHS_prepend_mx7 := "${THISDIR}/${PN}/imx:"

PACKAGE_ARCH_mx6 = "${MACHINE_ARCH}"
PACKAGE_ARCH_mx6ul = "${MACHINE_ARCH}"
PACKAGE_ARCH_mx7 = "${MACHINE_ARCH}"
