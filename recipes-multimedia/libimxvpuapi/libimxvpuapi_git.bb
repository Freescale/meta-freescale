# Copyright 2018 (C) O.S. Systems Software LTDA.
DESCRIPTION = "frontend for the i.MX6 VPU hardware video engine"
HOMEPAGE = "https://github.com/Freescale/libimxvpuapi"
LICENSE = "LGPL-2.1-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=38fa42a5a6425b26d2919b17b1527324"
SECTION = "multimedia"
DEPENDS = "imx-vpu"

PV = "0.10.3+${SRCPV}"

SRCBRANCH ?= "v1"
SRCREV = "3a1ee3a54fe93813868d38c3d32ea065b59e227e"
SRC_URI = "git://github.com/Freescale/libimxvpuapi.git;branch=${SRCBRANCH};protocol=https"

S = "${WORKDIR}/git"

inherit waf pkgconfig

COMPATIBLE_MACHINE = "(mx6q-nxp-bsp|mx6dl-nxp-bsp)"

EXTRA_OECONF = "--libdir=${libdir}"
