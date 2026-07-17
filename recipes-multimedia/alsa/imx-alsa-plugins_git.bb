# Copyright 2013-2016 Freescale Semiconductor
# Copyright 2017-2026 NXP
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "ALSA-lib plugins for i.MX SoCs"
DESCRIPTION = "Freescale/NXP ALSA-lib plugins for i.MX SoCs, including the \
               software PDM (pulse-density modulation) capture plugin."
HOMEPAGE = "https://github.com/nxp-imx/imx-alsa-plugins"
SECTION = "multimedia"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=94d55d512a9ba36caa9b7df079bae19f"
DEPENDS = "alsa-lib"

# Provide the old fsl-alsa-plugins name for backwards compatibility
PROVIDES += "fsl-alsa-plugins"

PV = "1.0.26+${SRCPV}"

SRC_URI = "${IMXALSA_SRC};branch=${SRCBRANCH}"
IMXALSA_SRC ?= "git://github.com/nxp-imx/imx-alsa-plugins.git;protocol=https"
SRCBRANCH = "MM_04.10.03_2512_L6.18.2"
SRCREV = "18eb79b6cf13fb988de7d4eef5b22eb60f8e2533"

inherit autotools pkgconfig use-imx-headers

CFLAGS:append = " -I${STAGING_INCDIR_IMX}"

PACKAGECONFIG ??= "${PACKAGECONFIG_SWPDM}"
PACKAGECONFIG_SWPDM = ""
PACKAGECONFIG_SWPDM:mx8-nxp-bsp = "swpdm"

PACKAGECONFIG[swpdm] = "--enable-swpdm,--disable-swpdm,imx-sw-pdm"

# ALSA plugins install versioned-looking .so modules into ${libdir}/alsa-lib;
# these are runtime plugin objects (not dev symlinks), so dev-so must be skipped.
# nooelint: oelint.vars.insaneskip
INSANE_SKIP:${PN} = "dev-so"

FILES:${PN} += "${libdir}/alsa-lib/libasound_*.so"
FILES:${PN}-dbg += "${libdir}/alsa-lib/.debug"
FILES:${PN}-dev += "${libdir}/alsa-lib/*.la"

# Runtime backwards compatibility with the old fsl-alsa-plugins name
RREPLACES:${PN} = "fsl-alsa-plugins"
RPROVIDES:${PN} = "fsl-alsa-plugins"
RCONFLICTS:${PN} = "fsl-alsa-plugins"

COMPATIBLE_MACHINE = "(imx-nxp-bsp)"
