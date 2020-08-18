# Copyright (C) 2019-2020 NXP

SUMMARY = "NXP i.MX SECO library"
DESCRIPTION = "NXP IMX SECO library"
SECTION = "base"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://EULA.txt;md5=228c72f2a91452b8a03c4cab30f30ef9"

DEPENDS = "zlib"

SRCBRANCH = "imx_5.4.24_2.1.0"
SRC_URI = "git://github.com/NXP/imx-seco-libs.git;protocol=https;branch=${SRCBRANCH}"
SRCREV = "a4faaa474e49fa0d3668db466739834fe45a6767"

S = "${WORKDIR}/git"

TARGET_CC_ARCH += "${LDFLAGS}"

do_install () {
	oe_runmake DESTDIR=${D} install
}

COMPATIBLE_MACHINE = "(mx8)"
COMPATIBLE_MACHINE_mx8m = "(^$)"
