# Copyright (C) 2013 Timesys Corporation
SUMMARY = "MCC KERNEL MODULE"
DESCRIPTION = "Timesys Multicore communication kernel module"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c8959abcbbe4d6676c58eab9354019e6"

inherit module

SRC_URI = "http://repository.timesys.com/buildsources/m/mcc-kmod/mcc-kmod-${PV}/mcc-kmod-${PV}.tar.bz2 \
           file://mcc-kmod-oe-module-makefile.patch"

SRC_URI[md5sum] = "046b0a86d70b9e3424efc86f442bc335"
SRC_URI[sha256sum] = "44ed7f812625ef764d62ff0871784ceb155ef86df8932e91830f9222adbf3a9e"

S = "${WORKDIR}/mcc-kmod-${PV}"

COMPATIBLE_MACHINE = "(vf60)"
