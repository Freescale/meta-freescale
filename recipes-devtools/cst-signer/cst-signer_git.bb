SUMMARY = "CST signer tool allows a way to automate the signing process"
DESCRIPTION = "The CST signer tool works in conjunction with the Code Signing Tool (CST) provided by \
NXP. The tool allows a way to automate the signing process in conjunction with a configuration file \
that can be populated with necessary inputs. In addition, this tool parses the 'to be signed' image \
and extracts the offset and length information needed to sign the image, thus reducing the possible \
human error while signing."
AUTHOR = "Utkarsh Gupta <utkarsh.gupta@nxp.com>"
HOMEPAGE = "https://github.com/nxp-imx-support/nxp-cst-signer"
BUGTRACKER = "https://github.com/nxp-imx-support/nxp-cst-signer/issues"
SECTION = "devel"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=b234ee4d69f5fce4486a80fdaf4a4263"

PV = "0.3+${SRCPV}"

SRC_URI = "git://github.com/nxp-imx-support/nxp-cst-signer;protocol=https;branch=master \
           file://0001-Improve-Makefile-so-it-is-overridable-and-provides-s.patch \
           file://0002-Fix-include-of-headers-so-it-indicates-it-uses-the-i.patch"
SRCREV = "7fa50daf3237661497cf753ff13e6f66d9d2fea8"

S = "${WORKDIR}/git"

EXTRA_OEMAKE += "DESTDIR=${D} PREFIX=${prefix}"

do_install () {
    oe_runmake install
}

do_install:append:class-native () {
    # The sample files are used as templates so here we copy them for later use.
    mkdir -p ${D}${datadir}/cst-signer
    for f in ${D}${datadir}/doc/cst-signer/*; do
        mv $f ${D}${datadir}/cst-signer/$(basename $f | sed 's,.sample,.in,g')
    done
    rmdir ${D}${datadir}/doc/cst-signer \
          ${D}${datadir}/doc
}

BBCLASSEXTEND = "native nativesdk"
