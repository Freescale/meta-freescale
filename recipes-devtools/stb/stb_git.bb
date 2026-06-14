SUMMARY = "single-file public domain (or MIT licensed) libraries for C/C++"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://stb.h;beginline=14418;endline=14433;md5=b10975d4c8155af1811ab611586f01d2"

PV = "0.0+git${SRCPV}"

SRCREV = "f67165c2bb2af3060ecae7d20d6f731173485ad0"
SRC_URI = "git://github.com/nothings/stb.git;protocol=https;branch=master"

do_install() {
    install -d ${D}${includedir}
    for hdr in ${S}/*.h; do
        install -m 0644 $hdr ${D}${includedir}
    done
}

# This is a header-only library, so the main package will be empty.
ALLOW_EMPTY:${PN} = "1"
