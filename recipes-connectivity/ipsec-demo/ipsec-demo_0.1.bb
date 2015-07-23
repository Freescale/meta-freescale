SUMMARY = "Scripts and configuration files for ipsec demo"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

RDEPENDS_${PN} = "ipsec-tools"

inherit allarch

SRC_URI = "file://test_setkey"

do_configure() {
    :
}

do_compile() {
    :
}

do_install(){
    install -d  ${D}${datadir}
    cp -a ${WORKDIR}/test_setkey ${D}${datadir}/
}

FILES_${PN} = "${datadir}/*"

