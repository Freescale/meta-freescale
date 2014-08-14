# Copyright (C) 2013 Timesys Corporation
SUMMARY = "Multicore communication Library"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c49712341497d0b5f2e40c30dff2af9d"

DEPENDS = "kernel-module-mcc"

inherit autotools

SRC_URI = "http://repository.timesys.com/buildsources/l/libmcc/libmcc-${PV}/libmcc-${PV}.tar.bz2 \
           file://remove_bashisms.patch "

SRC_URI[md5sum] = "e93ce76b4419f0902a848e1769bf22ea"
SRC_URI[sha256sum] = "3de76c5b1a096021afebfc79ca4e85d05646beb3101edcecf73b82886e7a00c0"

S = "${WORKDIR}/libmcc-${PV}"

CFLAGS += "-I${STAGING_KERNEL_DIR}/include"

RDEPENDS_${PN} = "kernel-module-mcc"

COMPATIBLE_MACHINE = "(vf60)"
