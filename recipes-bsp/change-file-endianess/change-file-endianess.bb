DESCRIPTION = "provides the tcl script for endian swap"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

SRC_URI = "file://byte_swap.tcl"

RDEPENDS += "tcl-native"

inherit native deploy

S = "${WORKDIR}"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install () {
    install -d ${D}/${bindir}
    install -m 755 ${WORKDIR}/byte_swap.tcl ${D}/${bindir}
}

do_deploy () {
    :
}

do_deploy_class-native () {
    install -d ${DEPLOYDIR}
    cp -f ${S}/byte_swap.tcl ${DEPLOYDIR}/
}
addtask deploy before do_build after do_populate_sysroot

BBCLASSEXTEND = "native nativesdk"
