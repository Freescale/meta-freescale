# Freescale imx extra configuration udev rules
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PRINC := "${@int(PRINC) + 1}"

SRC_URI_append_mxs = " file://10-imx.rules"
SRC_URI_append_mx5 = " file://10-imx.rules"
SRC_URI_append_mx6 = " file://10-imx.rules"

do_install_prepend () {
	if [ -e "${WORKDIR}/10-imx.rules" ]; then
		install -d ${D}${sysconfdir}/udev/rules.d
		install -m 0644 ${WORKDIR}/10-imx.rules ${D}${sysconfdir}/udev/rules.d
	fi
}

PACKAGE_ARCH_mxs = "${MACHINE_ARCH}"
PACKAGE_ARCH_mx5 = "${MACHINE_ARCH}"
PACKAGE_ARCH_mx6 = "${MACHINE_ARCH}"
