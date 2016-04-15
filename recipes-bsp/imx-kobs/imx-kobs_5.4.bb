# Copyright (C) 2013-2016 Freescale Semiconductor

SUMMARY = "Nand boot write source"
SECTION = "base"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=393a5ca445f6965873eca0259a17f833"

SRC_URI = "${FSL_MIRROR}/imx-kobs-${PV}.tar.gz \
           file://fix-compile.patch \
"

SRC_URI[md5sum] = "77467d834f858c2ec216841583e5f437"
SRC_URI[sha256sum] = "85171b46068ac47c42fedb8104167bf9afd33dd9527ed127e1ca2eb29d7a86bf"

inherit  autotools pkgconfig

COMPATIBLE_MACHINE = "(imx)"
