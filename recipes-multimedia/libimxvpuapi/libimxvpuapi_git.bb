# Copyright 2018 (C) O.S. Systems Software LTDA.
DESCRIPTION = "frontend for the i.MX6 VPU hardware video engine"
HOMEPAGE = "https://github.com/Freescale/libimxvpuapi"
LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM = "file://LICENSE;md5=38fa42a5a6425b26d2919b17b1527324"
SECTION = "multimedia"
DEPENDS = "imx-vpu"

PV = "0.10.3+${SRCPV}"

SRCBRANCH ?= "master"
SRCREV = "4afb52f97e28c731c903a8538bf99e4a6d155b42"
SRC_URI = "git://github.com/Freescale/libimxvpuapi.git;branch=${SRCBRANCH}"

S = "${WORKDIR}/git"

inherit waf pkgconfig

COMPATIBLE_MACHINE = "(mx6q|mx6dl)"

EXTRA_OECONF = "--libdir=${libdir}"
