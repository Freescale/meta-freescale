# Copyright (C) 2016 Freescale Semiconductor
# Copyright (C) 2017-2022 NXP

require imx-mkimage_git.inc

DESCRIPTION = "i.MX make image"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6"
SECTION = "BSP"

inherit deploy native

CFLAGS = "-O2 -Wall -std=c99 -I ${STAGING_INCDIR} -L ${STAGING_LIBDIR}"

do_compile () {
    cd ${S}
    oe_runmake clean
    oe_runmake bin
    oe_runmake -C iMX8M -f soc.mak mkimage_imx8
}

do_install () {
    cd ${S}
    install -d ${D}${bindir}
    install -m 0755 iMX8M/mkimage_imx8 ${D}${bindir}/mkimage_imx8m
    install -m 0755 iMX8M/mkimage_fit_atf.sh ${D}${bindir}/mkimage_fit_atf.sh
    install -m 0755 iMX8M/print_fit_hab.sh ${D}${bindir}/print_fit_hab.sh
    install -m 0755 mkimage_imx8 ${D}${bindir}/mkimage_imx8
}
