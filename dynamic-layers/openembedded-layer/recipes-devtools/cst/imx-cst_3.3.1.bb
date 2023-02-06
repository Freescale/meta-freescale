SUMMARY = "i.MX code signing tool"
DESCRIPTION = "Provides software code signing support designed that integrate the HABv4 and AHAB library"
SECTION = "cst"
LICENSE = "BSD-3-Clause"

LIC_FILES_CHKSUM = "file://LICENSE.bsd3;md5=1fbcd66ae51447aa94da10cbf6271530"

DEPENDS = "byacc-native flex-native openssl"

# tag=debian/3.3.1+dfsg-2
SRCREV = "e2c687a856e6670e753147aacef42d0a3c07891a"
SRC_URI = " \
    file://0001-fix-err-msg-linking.patch \
    git://gitlab.apertis.org/pkg/imx-code-signing-tool.git;protocol=https;branch=apertis/v2022pre \
"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = 'CC="${CC}" LD="${CC}" AR="${AR}" OBJCOPY="${OBJCOPY}"'

do_compile() {
    cd ${S}/code/cst
    oe_runmake build OSTYPE=linux64 ENCRYPTION=yes COPTIONS="${CFLAGS} ${CPPFLAGS}" LDOPTIONS="${LDFLAGS}"
    cd -
    oe_runmake -C code/hab_csf_parser COPTS="${CFLAGS} ${CPPFLAGS} ${LDFLAGS}"
}

do_install () {
    install -d ${D}${bindir}
    install -m 755 ${S}/code/cst/code/obj.linux64/cst ${D}${bindir}
    install -m 755 ${S}/code/cst/code/obj.linux64/srktool ${D}${bindir}
    install -m 755 ${S}/code/hab_csf_parser/csf_parser ${D}${bindir}
}

BBCLASSEXTEND = "native nativesdk"
