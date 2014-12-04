# Copyright (C) 2013, 2014 Freescale Semiconductor

SUMMARY = "Nand boot write source"
SECTION = "base"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=393a5ca445f6965873eca0259a17f833"

SRC_URI = "${FSL_MIRROR}/imx-kobs-${PV}.tar.gz"

SRC_URI[md5sum] = "7d83d73afc1fcd4767442ca35dd9fa35"
SRC_URI[sha256sum] = "f7c0f7b5b876e4dd5c74c93f17ff2a5c8ab154aa89c32590a389e9602f0445e6"

inherit  autotools pkgconfig
