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
    https://github.com/nxp-imx/mfgtools/releases/download/uuu_${PV}/uuu_mac_x86;downloadfilename=uuu-${PV}_mac_x86;name=Mac_x86 \
    https://github.com/nxp-imx/mfgtools/releases/download/uuu_${PV}/uuu_mac_arm;downloadfilename=uuu-${PV}_mac_arm;name=Mac_arm \
    https://github.com/nxp-imx/mfgtools/releases/download/uuu_${PV}/uuu.exe;downloadfilename=uuu-${PV}.exe;name=Windows \
"

SRC_URI[Linux.sha256sum] = "c609fe6c4d9656102f7e3139a70488ba3988c33332486c89e5fc6d85ccedd96a"
SRC_URI[Mac_x86.sha256sum] = "cdbacab592661900d46e7f97f9c7dd8a720bf46b1c17f4dbb65adb372f5fc6cf"
SRC_URI[Mac_arm.sha256sum] = "6f8854946dfbeeb36894baf0f5f555b918974d465f4b541457e65c926fdd6a6a"
SRC_URI[Windows.sha256sum] = "a3c7241650c05dd6373a6aef086b34322c013103da729c1b446ec86694309939"

inherit allarch

do_install() {
    install -D -m 0755 ${UNPACKDIR}/uuu-${PV}           ${D}${libdir}/uuu/uuu
    install -D -m 0755 ${UNPACKDIR}/uuu-${PV}_mac_x86   ${D}${libdir}/uuu/uuu_mac_x86
    install -D -m 0755 ${UNPACKDIR}/uuu-${PV}_mac_arm   ${D}${libdir}/uuu/uuu_mac_arm
    install -D -m 0644 ${UNPACKDIR}/uuu-${PV}.exe       ${D}${libdir}/uuu/uuu.exe
}

# HACK! We are not aiming to run those binaries during the build but copy then for MFGTOOL bundle.
INSANE_SKIP:${PN} += "arch file-rdeps"
FILES:${PN} = "${libdir}/uuu"
SYSROOT_DIRS = "${libdir}/uuu"
