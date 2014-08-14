# Copyright (C) 2013 Timesys Corporation
SUMMARY = "Multicore communication kernel module"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c8959abcbbe4d6676c58eab9354019e6"

inherit module

SRC_URI = "http://repository.timesys.com/buildsources/m/mcc-kmod/mcc-kmod-${PV}/mcc-kmod-${PV}.tar.bz2 \
           file://remove_bashisms_kmod.patch "

SRC_URI[md5sum] = "d72e8f6575605906be94ac2d26e6bc26"
SRC_URI[sha256sum] = "f86a860e68cefdce70796572d97ddd4cc5e08d9f13117b431599add55bf1a651"

S = "${WORKDIR}/mcc-kmod-${PV}"

COMPATIBLE_MACHINE = "(vf60)"
