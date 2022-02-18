SUMMARY = "Universal Update Utility"
DESCRIPTION = "Image deploy tool for i.MX chips"
HOMEPAGE = "https://github.com/NXPmicro/mfgtools"

SRC_URI = "git://github.com/NXPmicro/mfgtools.git;protocol=https;branch=master"
SRCREV = "e10b0260076e0119c259f7f44447904f14109ba2"
PV = "1.4.165"

SRC_URI += "file://0001-remove-unnecessary-libzip-dependency.patch"

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=38ec0c18112e9a92cffc4951661e85a5"

inherit cmake pkgconfig

S = "${WORKDIR}/git"

DEPENDS = "libusb zlib bzip2 openssl"

COMPATIBLE_MACHINE   = "(imx-nxp-bsp)"

BBCLASSEXTEND = "native nativesdk"
