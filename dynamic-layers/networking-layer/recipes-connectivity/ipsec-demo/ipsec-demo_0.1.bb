SUMMARY = "Scripts and configuration files for ipsec demo"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

RDEPENDS_${PN} = "ipsec-tools bash"

inherit allarch

SRC_URI = "file://test_setkey"

S = "${WORKDIR}"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install(){
    install -d  ${D}${datadir}
    cp -a ${WORKDIR}/test_setkey ${D}${datadir}/
    chown -R root:root ${D}${datadir}/test_setkey
}

FILES_${PN} = "${datadir}/*"

COMPATIBLE_MACHINE = "(qoriq)"
PACKAGE_ARCH = "${MACHINE_SOCARCH}"

