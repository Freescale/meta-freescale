SUMMARY = "Cross-platform image library"
DESCRIPTION = "A full featured cross-platform image library"
HOMEPAGE = "https://openil.sourceforge.net/"
SECTION = "libs"
LICENSE = "LGPL-2.1-only"
LIC_FILES_CHKSUM = "file://../LICENSE;md5=fc178bcd425090939a8b634d1d6a9594"
PR = "r0"

DEPENDS = "jpeg libpng tiff xz"

SRC_URI = "\
    http://sourceforge.net/projects/openil/files/DevIL/${PV}/DevIL-${PV}.zip \
    file://0001-cmake-Update-cmake_minimum_required-to-3.5.patch \
"
SRC_URI[sha256sum] = "451337f392c65bfb83698a781370534dc63d7bafca21e9b37178df0518f7e895"

# The NXP BSP releases for this branch (meta-imx wrynose-6.18.*) add this same
# patch from their own bbappend; listed twice, do_patch fails on the second
# copy. Keep one, so it applies once with or without those layers.
python __anonymous () {
    patch = "file://0001-cmake-Update-cmake_minimum_required-to-3.5.patch"
    src_uri = d.getVar("SRC_URI").split()
    if src_uri.count(patch) > 1:
        first = src_uri.index(patch) + 1
        d.setVar("SRC_URI", " ".join(src_uri[:first] + [u for u in src_uri[first:] if u != patch]))
}

S = "${UNPACKDIR}/DevIL/DevIL"

inherit cmake

TARGET_CFLAGS += "-Dpng_set_gray_1_2_4_to_8=png_set_expand_gray_1_2_4_to_8"

SOLIBS = ".so"
FILES_SOLIBSDEV = ""

PACKAGE_ARCH = "${MACHINE_ARCH}"
