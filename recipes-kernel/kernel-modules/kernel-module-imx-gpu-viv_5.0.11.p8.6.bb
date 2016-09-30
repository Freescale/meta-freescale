# Copyright (C) 2015-2016 Freescale Semiconductor

SUMMARY = "Kernel loadable module for Vivante GPU"
DESCRIPTION = "Builds the Vivante GPU kernel driver as a loadable kernel module, \
allowing flexibility to use an older kernel with a newer graphics release."
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=12f884d2ae1ff87c09e5b7ccc2c4ca7e"

SRC_URI = "${FSL_MIRROR}/${PN}-${PV}.tar.gz"
SRC_URI[md5sum] = "ed31740763b2aa6d56ecbe64d9cea565"
SRC_URI[sha256sum] = "3e748fc53ee46ffcf80b94d5f9535c58a3875aa107a3c77b550d8100df15de4f"

do_install_prepend() {
    install ${B}/kernel-module-imx-gpu-viv-src/Module.symvers ${B}
}

inherit module

KERNEL_MODULE_AUTOLOAD = "galcore"
