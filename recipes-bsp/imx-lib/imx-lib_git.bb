# Copyright (C) 2012-2016 Freescale Semiconductor
# Copyright (C) 2012-2018 O.S. Systems Software LTDA.
# Copyright 2017 NXP

DESCRIPTION = "Platform specific libraries for imx platform"
LICENSE = "LGPL-2.1-only"
SECTION = "multimedia"

LIC_FILES_CHKSUM = "file://COPYING-LGPL-2.1;md5=fbc093901857fcd118f065f900982c24"

PE = "1"

PV = "5.9+${SRCPV}"

SRCBRANCH = "lf-5.10.52_2.1.0"

SRC_URI = "git://source.codeaurora.org/external/imx/imx-lib.git;protocol=https;branch=${SRCBRANCH}"
SRCREV = "87ddd80953835eb29027d1d5a12044a08e809e40"

S = "${WORKDIR}/git"

inherit use-imx-headers

PLATFORM:mx6q-nxp-bsp  = "IMX6Q"
PLATFORM:mx6dl-nxp-bsp = "IMX6Q"
PLATFORM:mx6sl-nxp-bsp = "IMX6S"
PLATFORM:mx6sll-nxp-bsp = "IMX6UL"
PLATFORM:mx6sx-nxp-bsp = "IMX6S"
PLATFORM:mx6ul-nxp-bsp = "IMX6UL"
PLATFORM:mx6ull-nxp-bsp = "IMX6UL"
PLATFORM:mx7d-nxp-bsp  = "IMX7"
PLATFORM:mx7ulp-nxp-bsp = "IMX7"
PLATFORM:mx8ulp-nxp-bsp = "IMX8ULP"

PARALLEL_MAKE="-j 1"
EXTRA_OEMAKE = ""

do_compile () {
    INCLUDE_DIR="-I${STAGING_INCDIR_IMX}"
    oe_runmake CROSS_COMPILE="${HOST_PREFIX}" PLATFORM="${PLATFORM}" INCLUDE="${INCLUDE_DIR}" all
}

do_install () {
    oe_runmake PLATFORM="${PLATFORM}" DEST_DIR="${D}" install

    # Remove .go file for Android
    find ${D}/ -name *.go -exec rm {} \;
}

COMPATIBLE_MACHINE = "(mx6-nxp-bsp|mx7-nxp-bsp|mx8ulp-nxp-bsp)"
