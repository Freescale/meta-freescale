# Copyright (C) 2022-2023 O.S. Systems Software LTDA.
# Released under the MIT License (see COPYING.MIT for the terms)

SUMMARY = "Universal Update Utility - Binaries"
DESCRIPTION = "Prebuilt Universal Update Utility (uuu) binaries used to download and deploy bootloader and OS images to NXP i.MX chips over USB."
HOMEPAGE = "https://github.com/nxp-imx/mfgtools"
SECTION = "console/utils"

LICENSE = "BSD-3-Clause AND LGPL-2.1-or-later"
LIC_FILES_CHKSUM = "file://mfgtools-${PV}-LICENSE;md5=38ec0c18112e9a92cffc4951661e85a5 \
                    file://libusb-${PV}-COPYING;md5=fbc093901857fcd118f065f900982c24"

# Prebuilt release binaries carry no license file; fetch it from source at the
# matching release tag instead. The bundled libusb copy comes from the exact
# submodule commit mfgtools pins for this release.
SRC_URI = "\
    https://github.com/nxp-imx/mfgtools/releases/download/uuu_${PV}/uuu;downloadfilename=uuu-${PV};name=Linux \
    https://github.com/nxp-imx/mfgtools/releases/download/uuu_${PV}/uuu_mac_x86;downloadfilename=uuu-${PV}_mac_x86;name=Mac_x86 \
    https://github.com/nxp-imx/mfgtools/releases/download/uuu_${PV}/uuu_mac_arm;downloadfilename=uuu-${PV}_mac_arm;name=Mac_arm \
    https://github.com/nxp-imx/mfgtools/releases/download/uuu_${PV}/uuu.exe;downloadfilename=uuu-${PV}.exe;name=Windows \
    https://raw.githubusercontent.com/nxp-imx/mfgtools/uuu_${PV}/LICENSE;downloadfilename=mfgtools-${PV}-LICENSE;name=License \
    https://raw.githubusercontent.com/libusb/libusb/15a7ebb4d426c5ce196684347d2b7cafad862626/COPYING;downloadfilename=libusb-${PV}-COPYING;name=LibusbLicense \
"

SRC_URI[Linux.sha256sum] = "c609fe6c4d9656102f7e3139a70488ba3988c33332486c89e5fc6d85ccedd96a"
SRC_URI[Mac_x86.sha256sum] = "cdbacab592661900d46e7f97f9c7dd8a720bf46b1c17f4dbb65adb372f5fc6cf"
SRC_URI[Mac_arm.sha256sum] = "6f8854946dfbeeb36894baf0f5f555b918974d465f4b541457e65c926fdd6a6a"
SRC_URI[Windows.sha256sum] = "a3c7241650c05dd6373a6aef086b34322c013103da729c1b446ec86694309939"
SRC_URI[License.sha256sum] = "cc8d47f7b9260f6669ecd41c24554c552f17581d81ee8fc602c6d23edb8bf495"
SRC_URI[LibusbLicense.sha256sum] = "5df07007198989c622f5d41de8d703e7bef3d0e79d62e24332ee739a452af62a"

S = "${UNPACKDIR}"

inherit allarch

do_install() {
    install -D -m 0755 ${UNPACKDIR}/uuu-${PV}           ${D}${libdir}/uuu/uuu
    install -D -m 0755 ${UNPACKDIR}/uuu-${PV}_mac_x86   ${D}${libdir}/uuu/uuu_mac_x86
    install -D -m 0755 ${UNPACKDIR}/uuu-${PV}_mac_arm   ${D}${libdir}/uuu/uuu_mac_arm
    install -D -m 0644 ${UNPACKDIR}/uuu-${PV}.exe       ${D}${libdir}/uuu/uuu.exe
}

# These prebuilt binaries are bundled for the MFGTOOL package, not run on target,
# so their foreign arch and unresolved file-rdeps are expected and the arch and
# file-rdeps QA checks do not apply.
# nooelint: oelint.vars.insaneskip
INSANE_SKIP:${PN} += "arch file-rdeps"
# Prebuilt uuu host binaries are staged under ${libdir}/uuu for the MFGTOOL
# bundle only; the main package holds exactly that directory.
# nooelint: oelint.var.filesoverride
FILES:${PN} = "${libdir}/uuu"
SYSROOT_DIRS = "${libdir}/uuu"
