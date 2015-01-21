# Copyright (C) 2013-2015 Freescale Semiconductor

SUMMARY = "Nand boot write source"
SECTION = "base"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=393a5ca445f6965873eca0259a17f833"

SRC_URI = "${FSL_MIRROR}/imx-kobs-${PV}.tar.gz"

SRC_URI[md5sum] = "762063004e56e7160157e7c13ed6f684"
SRC_URI[sha256sum] = "12970c94446998decacc3ed0c3f1e4edb4b5192982ba7facb1f8be0fd7775bb1"

inherit  autotools pkgconfig
