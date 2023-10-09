SUMMARY = "Universal Update Utility"
DESCRIPTION = "Image deploy tool for i.MX chips"
HOMEPAGE = "https://github.com/nxp-imx/mfgtools"

SRC_URI = "git://github.com/nxp-imx/mfgtools.git;protocol=https;branch=master"
SRCREV = "ed48c514ee4c1ea4562c875877b180a87474f895"
PV = "1.4.243"

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=38ec0c18112e9a92cffc4951661e85a5"

inherit cmake pkgconfig

S = "${WORKDIR}/git"

DEPENDS = "libusb zlib bzip2 openssl"

BBCLASSEXTEND = "native nativesdk"
