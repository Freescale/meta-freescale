SUMMARY = "Universal Update Utility"
DESCRIPTION = "Image deploy tool for i.MX chips"
HOMEPAGE = "https://github.com/nxp-imx/mfgtools"

SRC_URI = "git://github.com/nxp-imx/mfgtools.git;protocol=https;branch=master"
SRCREV = "7347a80c7a943dd7e9081d9d2bab9e6ca8e0ba07"
PV = "1.5.165"

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=38ec0c18112e9a92cffc4951661e85a5"

inherit cmake pkgconfig

DEPENDS = "libusb zlib bzip2 openssl zstd libtinyxml2"

BBCLASSEXTEND = "native nativesdk"
