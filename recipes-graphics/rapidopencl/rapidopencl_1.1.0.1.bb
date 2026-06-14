SUMMARY = "Experimental low level header only C++11 RAII wrapper classes for the OpenCL API"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=b98f636daed34d12d11e25f3185c0204"

SRC_URI = "git://github.com/Unarmed1000/RapidOpenCL;protocol=https;branch=master \
"
SRCREV = "21254804a56ae96e8385c2652733c338d62da04f"

# RapidOpenCL is a header-only C++11 RAII wrapper class, so the main package will be empty.

do_install () {
    install -d ${D}${includedir}
    cp -r ${S}/include/* ${D}${includedir}
}

ALLOW_EMPTY:${PN} = "1"
