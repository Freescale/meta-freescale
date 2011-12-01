FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += "file://71-fsl-dpaa-persistent-networking.rules"

do_install_append () {
	install -d ${D}${sysconfdir}/udev/rules.d/
	install -m 0644 ${WORKDIR}/71-fsl-dpaa-persistent-networking.rules ${D}${sysconfdir}/udev/rules.d
}

PR .= "+${DISTRO}"
