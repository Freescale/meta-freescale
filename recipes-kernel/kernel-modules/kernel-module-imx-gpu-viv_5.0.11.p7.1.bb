# Copyright (C) 2015 Freescale Semiconductor

SUMMARY = "Kernel loadable module for Vivante GPU"
DESCRIPTION = "Provides flexibility to switch graphics between different kernels in future \
releases. This package uses same source code as GPU kernel driver source."
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=12f884d2ae1ff87c09e5b7ccc2c4ca7e"

inherit module

SRC_URI = "${FSL_MIRROR}/${PN}-${PV}.tar.gz \
           file://updatemakefile.patch \
           file://platform-Fix-kernel-version-check-for-3.14-based-ker.patch"

SRC_URI[md5sum] = "a251a94390986371f75b338ad938e46f"
SRC_URI[sha256sum] = "9aaef0a62bc2be69dc568228192b060c54970b5c700fee602d83a4d13e04a9b3"
