# Copyright (C) 2016 Freescale Semiconductor
# Copyright 2017-2022,2026 NXP

require imx-mkimage_git.inc

SUMMARY = "i.MX boot image generation tool"
DESCRIPTION = "Tooling to assemble i.MX boot images (mkimage_imx8 and related)."
HOMEPAGE = "https://github.com/nxp-imx/imx-mkimage"
SECTION = "bsp"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6"

inherit deploy native

EXTRA_OEMAKE = "SOC=iMX8M mkimage_imx8"

CFLAGS = "-O2 -Wall -std=c99 -I ${STAGING_INCDIR} -L ${STAGING_LIBDIR}"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 iMX8M/mkimage_imx8 ${D}${bindir}/mkimage_imx8m
    install -m 0755 iMX8M/mkimage_fit_atf.sh ${D}${bindir}/mkimage_fit_atf.sh
    install -m 0755 iMX8M/print_fit_hab.sh ${D}${bindir}/print_fit_hab.sh
    install -m 0755 mkimage_imx8 ${D}${bindir}/mkimage_imx8
}
