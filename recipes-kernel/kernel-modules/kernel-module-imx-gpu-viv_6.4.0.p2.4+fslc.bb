# Copyright (C) 2015-2018 O.S. Systems Software LTDA.
# Copyright (C) 2015-2016 Freescale Semiconductor

SUMMARY = "Kernel loadable module for Vivante GPU"
DESCRIPTION = "This package uses an exact copy of the GPU kernel driver source code of \
the same version as base and include fixes and improvements developed by FSL Community"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=12f884d2ae1ff87c09e5b7ccc2c4ca7e"

PV .= "+git${SRCPV}"

SRCREV = "f2e8483fbda59bf2482f77efb0804c014848f749"
SRC_URI = "git://github.com/Freescale/kernel-module-imx-gpu-viv.git;protocol=https"

S = "${WORKDIR}/git"

inherit module

KERNEL_MODULE_AUTOLOAD = "galcore"
COMPATIBLE_MACHINE = "(imx)"
