# Copyright (C) 2015-2016 Freescale Semiconductor
# Copyright 2017 NXP

SUMMARY = "Kernel loadable module for Vivante GPU"
DESCRIPTION = "Builds the Vivante GPU kernel driver as a loadable kernel module, \
allowing flexibility to use an older kernel with a newer graphics release."
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=12f884d2ae1ff87c09e5b7ccc2c4ca7e"

SRC_URI = "${FSL_MIRROR}/${PN}-${PV}.tar.gz"
SRC_URI[md5sum] = "c5922730046c159fd5055ad114e027b9"
SRC_URI[sha256sum] = "01b13fa5e1ab69475ce401e997fe8e42775b7792b1233e871b2b28555f8a3a66"

inherit module

KERNEL_MODULE_AUTOLOAD = "galcore"
