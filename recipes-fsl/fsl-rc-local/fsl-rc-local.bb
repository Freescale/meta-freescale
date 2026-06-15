# Copyright (C) 2012 O.S. Systems Software LTDA.

DESCRIPTION = "Extra files for fsl-gui-image"
LICENSE = "LGPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=39ec502560ab2755c4555ee8416dfe42"

SRC_URI = "file://rc.local.etc \
           file://rc.local.init \
           file://LICENSE"

S = "${UNPACKDIR}"

inherit update-rc.d

INITSCRIPT_NAME = "rc.local"
INITSCRIPT_PARAMS = "start 99 2 3 4 5 ."

do_install () {
    install -d ${D}/${sysconfdir}/init.d
    install -m 755 ${S}/rc.local.etc ${D}/${sysconfdir}/rc.local
    install -m 755 ${S}/rc.local.init ${D}/${sysconfdir}/init.d/rc.local

}
