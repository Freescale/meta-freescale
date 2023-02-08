SUMMARY = "Freescale TLU(Table Lookup Unit) test package"
DESCRIPTION = "This package includes the TLU(Table Lookup Unit) test scripts \
and configuration files."

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=8a71d0475d08eee76d8b6d0c6dbec543"

SRC_URI = "git://github.com/nxp-qoriq-yocto-sdk/fsl-tlu;protocol=https;nobranch=1"
SRCREV = "8837cce3c86b30c0931c319e9e1a8ca622ae5354"

S = "${WORKDIR}/git"

do_install() {
    install -d ${D}${sbindir}/fsl_tlu
    find . -type f -exec cp {} ${D}${sbindir}/fsl_tlu/ \;
}

COMPATIBLE_MACHINE = "(e500mc)"
PACKAGE_ARCH = "${MACHINE_SOCARCH}"

