# Copyright (C) 2019-2021 NXP

SUMMARY = "NXP i.MX SECO library"
DESCRIPTION = "NXP i.MX Security Controller library"
SECTION = "base"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://EULA.txt;md5=76871788f27c28af824e2ec1ca187832"

DEPENDS = "zlib"

SRCBRANCH = "lf-5.10.72_2.2.0"
SRC_URI = "git://github.com/NXP/imx-seco-libs.git;protocol=https;branch=${SRCBRANCH} \
           file://0002-Makefile-Fix-install-to-clear-host-user-contaminated.patch"
SRCREV = "3e8ad6b168f23a8c6ecd75edabc18d2673403e24"

S = "${WORKDIR}/git"

TARGET_CC_ARCH += "${LDFLAGS}"

do_install () {
	oe_runmake DESTDIR=${D} install
}

COMPATIBLE_MACHINE = "(mx8qm-nxp-bsp|mx8qxp-nxp-bsp|mx8dxl-nxp-bsp|mx8dx-nxp-bsp)"
