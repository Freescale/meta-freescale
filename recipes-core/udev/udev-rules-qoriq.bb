DESCRIPTION = "udev rules for Freescale QorIQ SOCs"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690"

SRC_URI = "\
    file://71-fsl-dpaa-persistent-networking.rules \
    file://72-fsl-dpaa-persistent-networking.rules \
"
RULE_qoriq-ppc = "71-fsl-dpaa-persistent-networking.rules"
RULE_e6500 = "72-fsl-dpaa-persistent-networking.rules"
RULE_e6500-64b = "72-fsl-dpaa-persistent-networking.rules"
RULE_t1024 = "72-fsl-dpaa-persistent-networking.rules"
RULE_t1023 = "72-fsl-dpaa-persistent-networking.rules"

S = "${WORKDIR}"

do_install () {
    install -d ${D}${sysconfdir}/udev/rules.d/
    install -m 0644 ${WORKDIR}/${RULE} ${D}${sysconfdir}/udev/rules.d/

    # skip mmc rpmb partitions
    echo "/dev/mmcblk.*rpmb" >>${D}${sysconfdir}/udev/mount.blacklist
    # skip nbd (network block device)
    echo "/dev/nbd*" >>${D}${sysconfdir}/udev/mount.blacklist
}

COMPATIBLE_MACHINE = "(qoriq-ppc)"
