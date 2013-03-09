FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PRINC := "${@int(PRINC) + 1}"

SRC_URI_append = " file://71-fsl-dpaa-persistent-networking.rules"

do_install_append () {
    install -d ${D}${sysconfdir}/udev/rules.d/
    install -m 0644 ${WORKDIR}/71-fsl-dpaa-persistent-networking.rules ${D}${sysconfdir}/udev/rules.d
}

