# Copyright 2020,2023,2026 NXP

SUMMARY = "i.MX PDM-to-PCM software decimation library"
DESCRIPTION = "NXP PDM to PCM Software Decimation SIMD Library"
HOMEPAGE = "https://www.nxp.com/"
SECTION = "multimedia"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=0858ec9c7a80c4a2cf16e4f825a2cc91"

PV = "1.0.3+git"

SRCBRANCH = "MM_04.11.00_2605_L6.18.20"
PDM_SRC ?= "git://github.com/nxp-imx/imx-sw-pdm.git;protocol=https"
SRC_URI = "${PDM_SRC};branch=${SRCBRANCH}"
SRCREV = "60c4e8f3fd9d2ae67518ea2b9486834c2d49309f"

inherit pkgconfig

TOOLCHAIN = "clang"
EXTRA_OEMAKE += "CLANGCC='${CC}' CLANGCXX='${CXX}' SDKTARGETSYSROOT='${STAGING_DIR_NATIVE}'"

do_install() {
    install -d ${D}${bindir}/imx-mm
    install -m 0755 ${S}/release/imx-sw-pdm ${D}${bindir}/imx-mm

    install -d ${D}${libdir}
    install -m 0644 ${S}/release/libimxswpdm.so* ${D}${libdir}
    ln -sf -r ${D}${libdir}/libimxswpdm.so.* ${D}${libdir}/libimxswpdm.so

    install -d ${D}${libdir}/pkgconfig
    install -m 0644 ${S}/libimxswpdm.pc ${D}${libdir}/pkgconfig

    install -d ${D}${includedir}/imx-mm/audio-codec/swpdm
    install -m 0644 ${S}/include/imx-swpdm.h ${D}${includedir}/imx-mm/audio-codec/swpdm
}

INSANE_SKIP:${PN} = "already-stripped"

FILES:${PN} += "${datadir}/imx-mm ${libdir}/*"

COMPATIBLE_MACHINE = "(mx8-nxp-bsp|mx9-nxp-bsp)"
