DESCRIPTION = "udev rules for Freescale QorIQ SOCs"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "\
    file://71-fsl-dpaa-persistent-networking.rules \
    file://72-fsl-dpaa-persistent-networking.rules \
    file://73-fsl-dpaa-persistent-networking.rules \
    file://74-ls1046a-xfi2-networking.rules \
    file://73-fsl-enetc-networking.rules \
"
S = "${WORKDIR}"

RULE = "71-fsl-dpaa-persistent-networking.rules"
RULE_e6500 = "72-fsl-dpaa-persistent-networking.rules"
RULE_e6500-64b = "72-fsl-dpaa-persistent-networking.rules"
RULE_t1024 = "72-fsl-dpaa-persistent-networking.rules"
RULE_qoriq-arm64 = "73-fsl-dpaa-persistent-networking.rules \
    73-fsl-enetc-networking.rules \
"
RULE_ls1046a = "73-fsl-dpaa-persistent-networking.rules \
    74-ls1046a-xfi2-networking.rules \
"
RULE_ls1012a = ""
RULE_ls2080a = ""

do_install () {
    install -d ${D}${sysconfdir}/udev/rules.d/
    for r in ${RULE};do
        install -m 0644 ${WORKDIR}/${r} ${D}${sysconfdir}/udev/rules.d/
    done
}

ALLOW_EMPTY_${PN} = "1"

COMPATIBLE_MACHINE = "(qoriq)"
PACKAGE_ARCH = "${MACHINE_SOCARCH}"

