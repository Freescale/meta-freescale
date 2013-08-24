# Copyright (C) 2013 Timesys Corporation
SUMMARY = "mqxboot"
DESCRIPTION = "Timesys MQX Image loader - starts an MQX image on the M4"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c49712341497d0b5f2e40c30dff2af9d"

RDEPENDS_${PN} = "kernel-module-mcc"

inherit autotools

SRC_URI = "http://repository.timesys.com/buildsources/m/mqxboot/mqxboot-${PV}/mqxboot-${PV}.tar.bz2"

SRC_URI[md5sum] = "3de3c8b5f8cd6664870587d29c04c421"
SRC_URI[sha256sum] = "c2f66ae962fe2529578d5c007e3f91a36085b714a0ea276f47bc3aea90e69699"

S = "${WORKDIR}/mqxboot-${PV}"

COMPATIBLE_MACHINE = "(vf60)"
