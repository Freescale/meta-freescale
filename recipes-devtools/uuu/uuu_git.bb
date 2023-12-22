SUMMARY = "Universal Update Utility"
DESCRIPTION = "Image deploy tool for i.MX chips"
HOMEPAGE = "https://github.com/nxp-imx/mfgtools"

SRC_URI = "git://github.com/nxp-imx/mfgtools.git;protocol=https;branch=master"
SRCREV = "de317f587003dbd6203af1c647e2fccb912f8f0d"
PV = "1.5.141"

SRC_URI += "file://0001-libuuu-usbhotplug.cpp-don-t-limit-retry-logic-to-win.patch"

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=38ec0c18112e9a92cffc4951661e85a5"

inherit cmake pkgconfig

S = "${WORKDIR}/git"

DEPENDS = "libusb zlib bzip2 openssl zstd"

BBCLASSEXTEND = "native nativesdk"
