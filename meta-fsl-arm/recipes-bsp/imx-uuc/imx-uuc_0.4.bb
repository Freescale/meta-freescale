# Copyright (C) 2014 Freescale Semiconductor
# Copyright (C) 2014 O.S. Systems Software LTDA.

SUMMARY = "Universal Adapter source"
SECTION = "base"
DEPENDS = "virtual/kernel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

# FIXME: From source code inspection this is the 0.4 release
SRC_URI = "${FSL_MIRROR}/imx-uuc-3.10.17-1.0.0_beta.tar.gz \
           file://COPYING-Add-license-file.patch \
           file://sdimage.c-uu.c-update-license-header.patch \
           file://Makefile-adapt-to-work-out-of-box-with-OE-Core.patch \
           file://linuxrc-Use-devtmpfs-and-general-cleanup.patch"

S = "${WORKDIR}/${PN}-3.10.17-1.0.0"

SRC_URI[md5sum] = "c111a44b436a5715babef897fc13ca2d"
SRC_URI[sha256sum] = "7f398aa3e735ad7176986c1c8cb08a8d9bd4d9c989b2e129ae8c9fd718f9b669"

inherit autotools

FILES_${PN} += "/linuxrc"
