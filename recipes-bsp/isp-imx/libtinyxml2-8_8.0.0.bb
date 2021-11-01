SUMMARY = "TinyXML-2 is a simple, small, efficient, C++ XML parser that can be easily integrating into other programs"
HOMEPAGE = "http://www.grinninglizard.com/tinyxml2/"
SECTION = "libs"
LICENSE = "Zlib"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=135624eef03e1f1101b9ba9ac9b5fffd"

SRC_URI = "git://github.com/leethomason/tinyxml2.git"

SRCREV = "bf15233ad88390461f6ab0dbcf046cce643c5fcb"

S = "${WORKDIR}/git"

inherit cmake

# make sure we don't provide files which are also present in the
# current libtinyxml2 version's -dev package.
do_install:append() {
    rm -rf ${D}/${includedir}
    rm -rf ${D}/${libdir}/cmake
    rm -rf ${D}/${libdir}/libtinyxml2.so
    rm -rf ${D}/${libdir}/pkgconfig
}
