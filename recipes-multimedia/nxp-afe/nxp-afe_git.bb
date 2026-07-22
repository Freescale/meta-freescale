# Copyright 2025-2026 NXP

SUMMARY = "NXP Audio Front End (AFE)"
DESCRIPTION = "NXP Audio Front End (AFE) for incorporating Voice Assistants"
HOMEPAGE = "https://github.com/nxp-imx/nxp-afe"
SECTION = "multimedia"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=7bdef19938f3503cfc4c586461f99012"

DEPENDS += "alsa-lib"

PV = "1.0+git"

SRCBRANCH = "MM_04.11.00_2605_L6.18.20"
NXPAFE_SRC ?= "git://github.com/nxp-imx/nxp-afe.git;protocol=https"

SRC_URI = "${NXPAFE_SRC};branch=${SRCBRANCH}"

SRCREV = "7d518eb0b18f7c7faadeb3c3397f8b73f0012f3e"

TARGET_CC_ARCH += "${LDFLAGS}"

do_install() {
        install -d ${D}${libdir}/nxp-afe
        install -d ${D}/unit_tests/nxp-afe
        install -m 0644 ${UNPACKDIR}/deploy_afe/*.so.1.0 ${D}${libdir}/nxp-afe
        ln -sf -r ${D}${libdir}/nxp-afe/libdummyimpl.so.1.0 ${D}${libdir}/nxp-afe/libdummyimpl.so
        install -m 0755 ${UNPACKDIR}/deploy_afe/afe ${D}/unit_tests/nxp-afe
        install -m 0644 ${UNPACKDIR}/deploy_afe/asound.conf*    ${D}/unit_tests/nxp-afe
        install -m 0644 ${UNPACKDIR}/deploy_afe/TODO.md    ${D}/unit_tests/nxp-afe
        install -m 0755 ${UNPACKDIR}/deploy_afe/UAC_VCOM_composite.sh    ${D}/unit_tests/nxp-afe
}

FILES:${PN} += "/unit_tests"
# Prebuilt versioned .so shipped with an unversioned symlink in the main package.
# nooelint: oelint.vars.insaneskip
INSANE_SKIP:${PN} += "dev-so"
# nooelint: oelint.vars.insaneskip
INSANE_SKIP:${PN}-dbg += "buildpaths"
