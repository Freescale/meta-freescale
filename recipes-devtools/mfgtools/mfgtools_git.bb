DESCRIPTION = "Freescale/NXP I.MX Chip image deploy tools library"
SECTION = "devel"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=76b1b3cba07c1020780276c11101dd5a"

DEPENDS = "libusb1 dtc"

SRCREV = "7ad03a087644f703afeb3b5204c9a9192466627f"
SRC_URI = " \
    git://github.com/codeauroraforum/mfgtools;protocol=https;branch=linux \
    file://0001-Fix-CXXFLAGS-in-CMakeList.patch \
    file://0002-Simple-cmake.patch \
    file://0003-exit.patch \
"
PV = "1.0+git${SRCPV}"

S = "${WORKDIR}/git"

inherit cmake

EXTRA_OECMAKE = "-DCMAKE_CXX_STANDARD=11"

BBCLASSEXTEND = "native nativesdk"
