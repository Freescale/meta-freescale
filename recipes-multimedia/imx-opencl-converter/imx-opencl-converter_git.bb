# Copyright 2023-2026 NXP
SUMMARY = "i.MX multimedia OpenCL converter library"
DESCRIPTION = "OpenCL-accelerated image conversion library used by the NXP \
               i.MX multimedia stack. It offloads pixel format and colour \
               space conversion to the GPU through an OpenCL implementation \
               provided by virtual/libopencl1, and is supported on the i.MX 8 \
               and i.MX 95 families that ship a GPU."
HOMEPAGE = "https://www.nxp.com/"
SECTION = "multimedia"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://COPYING;md5=a93d5dd70d0a316d4d6d0a736c716513"
DEPENDS = "virtual/libopencl1"

PV = "1.0.0+git"

SRCBRANCH = "MM_04.11.01_2608_L6.18.37"
OPENCL_CONVERTER_SRC ?= "git://github.com/nxp-imx/imx-opencl-converter.git;protocol=https"
SRC_URI = "${OPENCL_CONVERTER_SRC};branch=${SRCBRANCH}"
SRCREV = "48196a41b4a1c4b2bc3bc471b4caee71b58024c8"

inherit pkgconfig meson

FILES:${PN} += "${datadir}/"

COMPATIBLE_MACHINE = "(^$)"
COMPATIBLE_MACHINE:imxgpu = "(mx8-nxp-bsp)"
# Mali only ships the OpenCL ICD, so virtual/libopencl1 comes from meta-oe's
# ICD loader, which also requires the opencl distro feature.
COMPATIBLE_MACHINE:imxmali = "${@'(mx95-nxp-bsp)' if bb.utils.contains('BBFILE_COLLECTIONS', 'openembedded-layer', True, False, d) and bb.utils.contains('DISTRO_FEATURES', 'opencl', True, False, d) else '(^$)'}"
COMPATIBLE_MACHINE:mx8mm-nxp-bsp = "(^$)"
