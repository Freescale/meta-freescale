# Copyright (C) 2013 Timesys Corporation
SUMMARY = "MCC Userspace Library"
DESCRIPTION = "Timesys Multicore communication Library"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c49712341497d0b5f2e40c30dff2af9d"

RDEPENDS_${PN} = "kernel-module-mcc"

inherit autotools

SRC_URI = "http://repository.timesys.com/buildsources/l/libmcc/libmcc-${PV}/libmcc-${PV}.tar.bz2 \
           file://libmcc-cflags.patch \
           file://libmcc-symlink.patch"

SRC_URI[md5sum] = "adac15d52206be21a4589b2b923af4cc"
SRC_URI[sha256sum] = "8b4d88f260d4d3a67ee2c16e20112e0584403f350182087daa1cfafeceb16a7f"

S = "${WORKDIR}/libmcc-${PV}"

COMPATIBLE_MACHINE = "(vf60)"
