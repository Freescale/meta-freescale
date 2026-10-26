# Copyright (C) 2012-2018 O.S. Systems Software LTDA.
# Copyright (C) 2012-2016 Freescale Semiconductor
# Copyright (C) 2017-2023,2026 NXP
# Released under the MIT license (see COPYING.MIT for the terms)
SUMMARY = "i.MX multimedia parser libraries"
DESCRIPTION = "Freescale Multimedia parser libs"
HOMEPAGE = "https://www.nxp.com/"
SECTION = "multimedia"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=6f862c6751ebcaa393467694c7b0c69a"

# For backwards compatibility
PROVIDES += "libfslparser"

PV = "4.11.1+git"

SRCBRANCH = "MM_04.11.01_2608_L6.18.37"
IMXPARSER_SRC ?= "git://github.com/nxp-imx/imx-parser;protocol=https"
SRC_URI = "${IMXPARSER_SRC};branch=${SRCBRANCH}"
SRCREV = "d99e35dcf788d0f7f7cde01c7ac6401d7b8779d1"

inherit pkgconfig meson

# Choose between 32-bit and 64-bit binaries and between Soft Float-Point and Hard Float-Point
EXTRA_OECONF = "${@bb.utils.contains('TUNE_FEATURES', 'aarch64', '--enable-armv8', \
                  bb.utils.contains('TUNE_FEATURES', 'callconvention-hard', '--enable-fhw', '--enable-fsw', d), d)}"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

python __set_insane_skip() {
    # FIXME: All binaries lack GNU_HASH in elf binary but as we don't have
    # the source we cannot fix it. Disable the insane check for now.
    # FIXME: gst-fsl-plugin looks for the .so files so we need to deploy those
    for p in d.getVar('PACKAGES').split():
        d.setVar("INSANE_SKIP:%s" % p, "ldflags dev-so textrel")
}
__set_insane_skip[doc] = "Skip QA checks that cannot be satisfied by the prebuilt parser binaries"

do_package_qa[prefuncs] += "__set_insane_skip"

# FIXME: gst-fsl-plugin looks for the .so files so we need to deploy those
FILES:${PN} += "${libdir}/imx-mm/*/*${SOLIBS} ${libdir}/imx-mm/*/*${SOLIBSDEV}"

INHIBIT_SYSROOT_STRIP = "1"

# For backwards compatibility
RREPLACES:${PN} = "libfslparser"
RPROVIDES:${PN} = "libfslparser"
RCONFLICTS:${PN} = "libfslparser"

COMPATIBLE_MACHINE = "(imx-nxp-bsp)"
