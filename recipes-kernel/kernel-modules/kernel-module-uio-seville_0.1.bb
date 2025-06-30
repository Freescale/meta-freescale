DESCRIPTION = "UIO driver for T1040 L2 Switch"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=12f884d2ae1ff87c09e5b7ccc2c4ca7e"

SRC_URI = "git://github.com/nxp-qoriq-yocto-sdk/l2switch-uio;branch=nxp/sdk-v2.0.x;protocol=https"
SRCREV = "0f31fbcbe9ab1ab9c424da34f70c82314b16f8de"

inherit module

S = "${UNPACKDIR}/${BP}/uio-driver"

COMPATIBLE_MACHINE = "(t1040|t1042)"
