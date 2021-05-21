# Copyright (C) 2019-2020 NXP

SUMMARY = "NXP i.MX SECO library"
DESCRIPTION = "NXP i.MX Security Controller library"
SECTION = "base"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://EULA.txt;md5=228c72f2a91452b8a03c4cab30f30ef9"

DEPENDS = "zlib"

SRCBRANCH = "imx_5.4.70_2.3.1"
SRC_URI = "git://github.com/NXP/imx-seco-libs.git;protocol=https;branch=${SRCBRANCH} \
           file://0002-Makefile-Fix-install-to-clear-host-user-contaminated.patch"
SRCREV = "79e5fb5d096c61b1c3163c9fa8e42719509d95a0"

S = "${WORKDIR}/git"

TARGET_CC_ARCH += "${LDFLAGS}"

do_install () {
	oe_runmake DESTDIR=${D} install
}

COMPATIBLE_MACHINE = "(mx8qm|mx8qxp|mx8phantomdxl|mx8dxl)"
