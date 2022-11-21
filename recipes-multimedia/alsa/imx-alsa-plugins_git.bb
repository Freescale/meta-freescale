# Copyright 2013-2016 Freescale Semiconductor
# Copyright 2017-2022 NXP
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Freescale alsa-lib plugins"
LICENSE = "GPL-2.0-only"
SECTION = "multimedia"
DEPENDS = "alsa-lib"

# For backwards compatibility
PROVIDES += "fsl-alsa-plugins"
RREPLACES:${PN} = "fsl-alsa-plugins"
RPROVIDES:${PN} = "fsl-alsa-plugins"
RCONFLICTS:${PN} = "fsl-alsa-plugins"

LIC_FILES_CHKSUM = "file://COPYING.GPL;md5=94d55d512a9ba36caa9b7df079bae19f"

inherit autotools pkgconfig use-imx-headers

PV = "1.0.26+${SRCPV}"

SRC_URI = "git://github.com/nxp-imx/imx-alsa-plugins.git;protocol=https;branch=${SRCBRANCH}"
SRCBRANCH = "MM_04.07.01_2208_L5.15.y"
SRCREV = "a9643b8820dd89b7a8c99941a90e9498f1b68c14"

S = "${WORKDIR}/git"

INCLUDE_DIR = "-I${STAGING_INCDIR_IMX}"

EXTRA_OECONF = "CFLAGS="${INCLUDE_DIR}""

PACKAGECONFIG ??= "${PACKAGECONFIG_SWPDM}"
PACKAGECONFIG_SWPDM             = ""
PACKAGECONFIG_SWPDM:mx8-nxp-bsp = "swpdm"
PACKAGECONFIG[swpdm] = "--enable-swpdm,--disable-swpdm,imx-sw-pdm"

INSANE_SKIP:${PN} = "dev-so"

FILES:${PN} += "${libdir}/alsa-lib/libasound_*.so"
FILES:${PN}-dbg += "${libdir}/alsa-lib/.debug"
FILES:${PN}-dev += "${libdir}/alsa-lib/*.la"

COMPATIBLE_MACHINE = "(imx-nxp-bsp)"
