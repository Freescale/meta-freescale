DESCRIPTION = "provides the tcl script for endian swap"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"


SRC_URI = "file://byte_swap.tcl"

RDEPENDS += "tcl-native"

inherit native

S = "${WORKDIR}"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install () {
    install -d ${D}/${bindir}
    install -m 755 ${WORKDIR}/byte_swap.tcl ${D}/${bindir}
}

BBCLASSEXTEND = "native nativesdk"
