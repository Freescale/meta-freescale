# Copyright (C) 2013, 2014 Freescale Semiconductor

SUMMARY = "Nand boot write source"
SECTION = "base"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=393a5ca445f6965873eca0259a17f833"

# FIXME: Drop 'beta' suffix for GA release
SRC_URI = "${FSL_MIRROR}/imx-kobs-${PV}_beta.tar.gz"
S = "${WORKDIR}/${PN}-${PV}_beta"

SRC_URI[md5sum] = "88d0a2e892ce0d3309390f48d4173e01"
SRC_URI[sha256sum] = "22abed5d43fea314570cf6f33b17d944180f4571932d8304fd8f84508f6b1527"

inherit  autotools pkgconfig
