SUMMARY = "Low level header only C++11 RAII wrapper classes for the OpenVX API"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=b98f636daed34d12d11e25f3185c0204"

SRC_URI = "git://github.com/Unarmed1000/RapidOpenVX;protocol=https;branch=master \
"
SRCREV = "909c7abbff0ee35610f07f6fadeaf3a2eadce36e"

do_install () {
    install -d ${D}${includedir}
    cp -r ${S}/include/* ${D}${includedir}
}

ALLOW_EMPTY:${PN} = "1"
