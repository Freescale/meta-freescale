SUMMARY = "single-file public domain (or MIT licensed) libraries for C/C++"
DESCRIPTION = "Collection of single-file public domain (or MIT licensed) libraries for C/C++ covering image loading and writing, font rasterisation and more"
HOMEPAGE = "https://github.com/nothings/stb"
SECTION = "libs"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://stb.h;beginline=14418;endline=14433;md5=b10975d4c8155af1811ab611586f01d2"

PV = "0.0+git${SRCPV}"

SRC_URI = "git://github.com/nothings/stb.git;protocol=https;branch=master"
SRCREV = "f67165c2bb2af3060ecae7d20d6f731173485ad0"

do_install() {
    install -d ${D}${includedir}
    for hdr in ${S}/*.h; do
        install -m 0644 $hdr ${D}${includedir}
    done
}

# This is a header-only library, so the main package will be empty.
ALLOW_EMPTY:${PN} = "1"
