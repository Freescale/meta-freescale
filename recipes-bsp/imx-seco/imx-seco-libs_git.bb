# Copyright 2019-22 NXP

SUMMARY = "NXP i.MX SECO library"
DESCRIPTION = "Library for NXP i.MX Security Controller Subsystem"
SECTION = "base"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://EULA.txt;md5=d3c315c6eaa43e07d8c130dc3a04a011"

DEPENDS = "zlib"

SRCBRANCH = "lf-6.6.3_1.0.0"
SRC_URI = "git://github.com/NXP/imx-seco-libs.git;protocol=https;branch=${SRCBRANCH} \
           file://0001-Makefile-Fix-LIBDIR-for-multilib.patch \
           file://0002-Makefile-Fix-install-to-clear-host-user-contaminated.patch \
           file://0001-add-v2x_test-to-the-installed-binaries.patch \
           file://0001-Fix-return-value-of-void-functions.patch"
SRCREV = "273553f207425f773400be7a7f3a7c425d892d6f"

S = "${WORKDIR}/git"

TARGET_CC_ARCH += "${LDFLAGS}"

do_install () {
    oe_runmake DESTDIR=${D} BINDIR=${bindir} LIBDIR=${libdir} install
}

COMPATIBLE_MACHINE = "(mx8qm-nxp-bsp|mx8qxp-nxp-bsp|mx8dxl-nxp-bsp|mx8dx-nxp-bsp)"
