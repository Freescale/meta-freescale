# Copyright 2020-2022 NXP

SUMMARY = "NXP i.MX CAAM Keyctl"
DESCRIPTION = "NXP i.MX keyctl tool to manage CAAM Keys"
SECTION = "base"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://COPYING;md5=8636bd68fc00cc6a3809b7b58b45f982"

SRCBRANCH = "master"
SRC_URI = "git://github.com/nxp-imx/keyctl_caam.git;protocol=https;branch=${SRCBRANCH}"

SRCREV = "81dc06cdb9c4d0d4ba10459d85af9a8603774948"

S = "${WORKDIR}/git"

TARGET_CC_ARCH += "${LDFLAGS}"

do_install () {
	oe_runmake DESTDIR=${D} install
}

COMPATIBLE_MACHINE = "(imx-generic-bsp)"

