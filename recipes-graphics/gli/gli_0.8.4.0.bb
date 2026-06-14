SUMMARY = "OpenGL Image Library"
DESCRIPTION = "OpenGL Image (GLI) is a header only C++ \
image library for graphics software."
HOMEPAGE = "http://gli.g-truc.net"
BUGTRACKER = "https://github.com/g-truc/gli/issues"

SECTION = "libs"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://readme.md;beginline=19;endline=20;md5=ab03b667ee630c1abb1add70365a50fb"

SRC_URI = " \
    git://github.com/g-truc/gli;protocol=https;branch=master \
"
SRCREV = "0c171ee87fcfe35a7e0e0445adef06f92e0b6a91"

inherit cmake

# This is a header-only library, so the main package will be empty.
ALLOW_EMPTY:${PN} = "1"

BBCLASSEXTEND = "native"
