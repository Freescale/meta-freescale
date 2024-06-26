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

SRC_URI[Linux.sha256sum] = "f863bba022202361d19e5026be0af408d307f78d2dbf2c139fb7eaaabd220442"
SRC_URI[Mac.sha256sum] = "62da0bd7e333931fba100823aa50133621c7e6047be0546bc12e29c0ea78a4d8"
SRC_URI[Windows.sha256sum] = "013ed8bb45e21b971b6b3a5802c5f154733913714bece0b020cb770a809cd206"

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
