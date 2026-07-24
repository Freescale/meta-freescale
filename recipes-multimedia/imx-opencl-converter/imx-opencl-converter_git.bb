# Copyright 2023-2026 NXP
SUMMARY = "i.MX multimedia OpenCL converter library"
DESCRIPTION = "NXP Multimedia opencl converter lib"
HOMEPAGE = "https://www.nxp.com/"
SECTION = "multimedia"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://COPYING;md5=a93d5dd70d0a316d4d6d0a736c716513"
DEPENDS = "virtual/libopencl1"

PV = "0.12.0+git"

SRCBRANCH = "MM_04.11.00_2605_L6.18.20"
OPENCL_CONVERTER_SRC ?= "git://github.com/nxp-imx/imx-opencl-converter.git;protocol=https"
SRC_URI = "${OPENCL_CONVERTER_SRC};branch=${SRCBRANCH}"
SRCREV = "9ebda5b8ad602afa1e5d8af4e85e713b2f3f4f9a"

inherit pkgconfig meson

FILES:${PN} += "${datadir}/"

COMPATIBLE_MACHINE = "(^$)"
COMPATIBLE_MACHINE:imxgpu = "(mx8-nxp-bsp|mx95-nxp-bsp)"
COMPATIBLE_MACHINE:mx8mm-nxp-bsp = "(^$)"
