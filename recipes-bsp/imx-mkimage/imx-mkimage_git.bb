# Copyright (C) 2016 Freescale Semiconductor
# Copyright 2017-2018 NXP

require imx-mkimage_git.inc

DESCRIPTION = "i.MX make image"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"
SECTION = "BSP"

inherit native deploy

CFLAGS = "-O2 -Wall -std=c99 -I ${STAGING_INCDIR} -L ${STAGING_LIBDIR}"

do_compile () {
    cd ${S}
    oe_runmake clean
    oe_runmake bin
    oe_runmake -C iMX8M -f soc.mak mkimage_imx8
    oe_runmake -C iMX8QM -f soc.mak imx8qm_dcd.cfg.tmp
    oe_runmake -C iMX8QX -f soc.mak imx8qx_dcd.cfg.tmp
}

do_install () {
    cd ${S}
    install -d ${D}${bindir}
    install -m 0755 iMX8M/mkimage_imx8 ${D}${bindir}/mkimage_imx8m
    install -m 0755 mkimage_imx8 ${D}${bindir}/mkimage_imx8
}

do_deploy () {
    install -m 0644 ${S}/iMX8QM/imx8qm_dcd.cfg.tmp ${DEPLOYDIR}
    install -m 0644 ${S}/iMX8QX/imx8qx_dcd.cfg.tmp ${DEPLOYDIR}
}
addtask deploy before do_build after do_install
