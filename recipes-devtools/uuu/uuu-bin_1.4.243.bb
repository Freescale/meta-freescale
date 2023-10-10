# Copyright (C) 2022-2023 O.S. Systems Software LTDA.
# Released under the MIT License (see COPYING.MIT for the terms)

SUMMARY = "Universal Update Utility - Binaries"
DESCRIPTION = "Image deploy tool for i.MX chips"
HOMEPAGE = "https://github.com/nxp-imx/mfgtools"

LICENSE = "BSD-3-Clause & LGPL-2.1-or-later"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9 \
                    file://${COMMON_LICENSE_DIR}/LGPL-2.1-or-later;md5=2a4f4fd2128ea2f65047ee63fbca9f68"

SRC_URI = " \
    https://github.com/nxp-imx/mfgtools/releases/download/uuu_${PV}/uuu;downloadfilename=uuu-${PV};name=Linux \
    https://github.com/nxp-imx/mfgtools/releases/download/uuu_${PV}/uuu_mac;downloadfilename=uuu-${PV}_mac;name=Mac \
    https://github.com/nxp-imx/mfgtools/releases/download/uuu_${PV}/uuu.exe;downloadfilename=uuu-${PV}.exe;name=Windows \
"

SRC_URI[Linux.sha256sum] = "dfb2a6dca337ebd59675ea5ce7f1bce6724e3b901bcb455126d4bf9bdfa2e585"
SRC_URI[Mac.sha256sum] = "399efa4bc7e3eb452fefe89ef5e2e453b516ea716658a963a890c430ad81a471"
SRC_URI[Windows.sha256sum] = "f3f178e7be161c7dc058dbcd35c8cfa1516981e7c4f915fe0256ae4cda7f101e"

S = "${WORKDIR}"

inherit allarch

do_install() {
    install -D -m 0755 ${WORKDIR}/uuu-${PV}     ${D}${libdir}/uuu/uuu
    install -D -m 0755 ${WORKDIR}/uuu-${PV}_mac ${D}${libdir}/uuu/uuu_mac
    install -D -m 0644 ${WORKDIR}/uuu-${PV}.exe ${D}${libdir}/uuu/uuu.exe
}

# HACK! We are not aiming to run those binaries during the build but copy then for MFGTOOL bundle.
INSANE_SKIP:${PN} += "arch file-rdeps"
FILES:${PN} = "${libdir}/uuu"
SYSROOT_DIRS = "${libdir}/uuu"
