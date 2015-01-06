# Copyright (C) 2013 Timesys Corporation
SUMMARY = "Multicore communication kernel module"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c8959abcbbe4d6676c58eab9354019e6"

inherit module

SRC_URI = "http://repository.timesys.com/buildsources/m/mcc-kmod/mcc-kmod-${PV}/mcc-kmod-${PV}.tar.bz2 \
           file://mcc_config.h-Only-include-mvf_sema4.h-if-building-in.patch"

SRC_URI[md5sum] = "d0507968592af5d8781e8bdbbb249e7d"
SRC_URI[sha256sum] = "43f834ddf9845da843c7790a307a0dbc8b04a40deed06aed7c3c99ad3a273f6c"

S = "${WORKDIR}/mcc-kmod-${PV}"

COMPATIBLE_MACHINE = "(vf60)"
