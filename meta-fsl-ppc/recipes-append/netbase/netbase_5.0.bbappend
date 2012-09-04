FILESEXTRAPATHS_prepend_fsl := "${THISDIR}/files:"

SRC_URI_fsl += "file://71-fsl-dpaa-persistent-networking.rules"

do_install_append_fsl () {
	install -d ${D}${sysconfdir}/udev/rules.d/
	install -m 0644 ${WORKDIR}/71-fsl-dpaa-persistent-networking.rules ${D}${sysconfdir}/udev/rules.d
}

PR_fsl .= "+${DISTRO}"
