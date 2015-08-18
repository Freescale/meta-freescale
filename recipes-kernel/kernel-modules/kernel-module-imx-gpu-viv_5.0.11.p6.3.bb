# Copyright (C) 2015 Freescale Semiconductor

SUMMARY = "Kernel loadable module for Vivante GPU"
DESCRIPTION = "Provides flexibility to switch graphics between different kernels in future \
releases. This package uses same source code as GPU kernel driver source."
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=12f884d2ae1ff87c09e5b7ccc2c4ca7e"

inherit module

SRC_URI = "${FSL_MIRROR}/imx-gpu-viv-kernel-${@'${PV}'.replace('5.0.11.p6.3', '5.0.11.p6.3-beta')}.tar.gz \
           file://platform-Fix-kernel-version-check-for-3.14-based-ker.patch"

SRC_URI[md5sum] = "6d46da80de94e98ee68ab1a75f384b89"
SRC_URI[sha256sum] = "e4b02fc0c9bdbfc7ecc67a0bad0917e788921c8f2444d99bd77daae7f3cd95df"

S = "${WORKDIR}/imx-gpu-viv-kernel-${@'${PV}'.replace('5.0.11.p6.3', '5.0.11.p6.3-beta')}"
