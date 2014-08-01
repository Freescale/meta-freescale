FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

SRC_URI_append_qoriq-ppc = " file://${@bb.utils.contains("TUNE_FEATURES", "e6500", \
    "72-fsl-dpaa-persistent-networking.rules", "71-fsl-dpaa-persistent-networking.rules", d)}"

do_install_append_qoriq-ppc () {
    install -d ${D}${sysconfdir}/udev/rules.d/
    install -m 0644 ${WORKDIR}/*-fsl-dpaa-persistent-networking.rules ${D}${sysconfdir}/udev/rules.d

    # skip mmc rpmb partitions
    echo "/dev/mmcblk.*rpmb" >>${D}${sysconfdir}/udev/mount.blacklist
}

