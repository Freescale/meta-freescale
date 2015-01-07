# Copyright (C) 2013 Timesys Corporation
SUMMARY = "Multicore communication Library"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c49712341497d0b5f2e40c30dff2af9d"

DEPENDS = "kernel-module-mcc"

SRC_URI = "http://repository.timesys.com/buildsources/l/libmcc/libmcc-${PV}/libmcc-${PV}.tar.bz2 \
           file://build-Fix-symlink-generation.patch \
           file://build-with-versioned-soname.patch"

SRC_URI[md5sum] = "cc3965f162dd20b8e6b9babf5dd963ee"
SRC_URI[sha256sum] = "b98c1f857bf851e41c35c4fcdb823e83e0e058c746dffb50038c8759c8c1e104"

S = "${WORKDIR}/libmcc-${PV}"

CFLAGS += "-I${STAGING_KERNEL_DIR}/include"

RDEPENDS_${PN} = "kernel-module-mcc"

COMPATIBLE_MACHINE = "(vf60)"

do_install() {
    oe_runmake 'DESTDIR=${D}' install
}

RDEPENDS_${PN}-dev += "kernel-module-mcc-dev"
