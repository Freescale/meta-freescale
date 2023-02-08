SUMMARY = "Scatter-gather logic for multiple tables"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=e9605a22ea50467bd2bfe4cdd66e69ae"

inherit module

SRC_URI = "git://github.com/nxp-qoriq-yocto-sdk/scatter-gather;branch=nxp/master"
SRCREV = "97db173d08a70abe2b9a6fa928299a117f3febc2"

S = "${WORKDIR}/git"

COMPATIBLE_MACHINE = "(ls1021a)"
