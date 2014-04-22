FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PRINC := "${@int(PRINC) + 2}"

SRC_URI_append = " file://${@bb.utils.contains("TUNE_FEATURES", "e6500", \
    "72-fsl-dpaa-persistent-networking.rules", "71-fsl-dpaa-persistent-networking.rules", d)}"

do_install_append () {
    install -d ${D}${sysconfdir}/udev/rules.d/
    install -m 0644 ${WORKDIR}/*-fsl-dpaa-persistent-networking.rules ${D}${sysconfdir}/udev/rules.d
}

