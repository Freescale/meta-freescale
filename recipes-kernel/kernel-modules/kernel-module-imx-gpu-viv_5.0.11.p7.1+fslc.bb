# Copyright (C) 2015 O.S. Systems Software LTDA.
# Copyright (C) 2015 Freescale Semiconductor

SUMMARY = "Kernel loadable module for Vivante GPU"
DESCRIPTION = "This package uses an exact copy of the GPU kernel driver source code of \
the same version as base and include fixes and improvements developed by FSL Community"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=12f884d2ae1ff87c09e5b7ccc2c4ca7e"

PV .= "+git${SRCPV}"

SRCREV = "eeeb23c0fb1cee01318088d417025263479c44ac"
SRC_URI = "git://github.com/Freescale/kernel-module-imx-gpu-viv.git;protocol=https"

S = "${WORKDIR}/git"

inherit module

KERNEL_MODULE_AUTOLOAD = "galcore"
