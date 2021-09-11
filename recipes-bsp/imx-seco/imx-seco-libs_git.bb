# Copyright (C) 2019-2021 NXP

SUMMARY = "NXP i.MX SECO library"
DESCRIPTION = "NXP IMX SECO library"
SECTION = "base"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://EULA.txt;md5=85d06b38f17b935ca11784d67075b846"

DEPENDS = "zlib"

SRCBRANCH = "lf-5.10.y_2.0.0"
SRC_URI = "git://github.com/NXP/imx-seco-libs.git;protocol=https;branch=${SRCBRANCH} \
           file://0002-Makefile-Fix-install-to-clear-host-user-contaminated.patch"
SRCREV = "44caf511023012e9eda93f8bf64c90eaff1bade6"

S = "${WORKDIR}/git"

TARGET_CC_ARCH += "${LDFLAGS}"

do_install () {
	oe_runmake DESTDIR=${D} install
}

COMPATIBLE_MACHINE = "(mx8qm|mx8qxp|mx8phantomdxl|mx8dxl)"
