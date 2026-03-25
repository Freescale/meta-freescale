SUMMARY = "uGuzzi IPA for the libcamera NXP NEO pipeline"
DESCRIPTION = "\
An Image Processing Algorithm library for libcamera, for i.MX95 ISP, \
based on proprietary MM Solutions EAD uGuzzi 3A library"
SECTION = "libs"
LICENSE = "Apache-2.0 & BSD-2-Clause & GPL-2.0-or-later & LGPL-2.1-or-later & MIT & Proprietary"
# Put EULA on separate line for automated recipe updates
LIC_FILES_CHKSUM = "file://LICENSES/LA_OPT_NXP_Software_License.txt;md5=bc649096ad3928ec06a8713b8d787eac"
LIC_FILES_CHKSUM += " \
    file://COPYING;md5=6462e06298403caf09a22e67a7cc551a \
    file://LICENSES/Apache-2.0.txt;md5=3b83ef96387f14655fc854ddc3c6bd57 \
    file://LICENSES/BSD-2-Clause.txt;md5=63d6ee386b8aaba70b1bf15a79ca50f2 \
    file://LICENSES/GPL-2.0-or-later.txt;md5=fed54355545ffd980b814dab4a3b312c \
    file://LICENSES/LGPL-2.1-or-later.txt;md5=3c328714bf889b2c3c7cd842e3e4893b \
    file://LICENSES/MIT.txt;md5=38aa75cf4c4c87f018227d5ec9638d75 "
DEPENDS = "libcamera"

SRC_URI = "${NEO_IPA_UGUZZI_SRC};branch=${SRCBRANCH}"
NEO_IPA_UGUZZI_SRC ?= "git://github.com/nxp-imx/neo-ipa-uguzzi;protocol=https"
SRCBRANCH = "lf-6.18.2_1.0.0"
SRCREV = "3c3b18e397a81fac2babe14dca01f4ada1ecc8b2"


inherit meson pkgconfig

FILES:${PN} += "${libdir}/libcamera ${datadir}/libcamera"

# Libraries are unversioned
SOLIBS = ".so"
FILES_SOLIBSDEV = ""

# Pre-built binaries are already stripped, so skip the QA test
INSANE_SKIP:${PN} = "already-stripped"

COMPATIBLE_MACHINE = "(mx95-generic-bsp)"
