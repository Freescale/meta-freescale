# Copyright (C) 2012-2018 O.S. Systems Software LTDA.
# Copyright (C) 2012-2016 Freescale Semiconductor
# Copyright 2017, 2019 NXP
# Released under the MIT license (see COPYING.MIT for the terms)
DESCRIPTION = "Freescale Multimedia parser libs"
LICENSE = "Proprietary"
SECTION = "multimedia"
LIC_FILES_CHKSUM = "file://COPYING;md5=fd4b227530cd88a82af6a5982cfb724d"

# For backwards compatibility
PROVIDES += "libfslparser"
RREPLACES_${PN} = "libfslparser"
RPROVIDES_${PN} = "libfslparser"
RCONFLICTS_${PN} = "libfslparser"

SRC_URI = "${FSL_MIRROR}/${BPN}-${PV}.bin;fsl-eula=true"
SRC_URI[md5sum] = "9659678b1a07a14558d55ed7e23e04d3"
SRC_URI[sha256sum] = "c6cb8a06acad73f50052d18cda02be729616218472f93974390622d6acea768e"

inherit fsl-eula-unpack autotools pkgconfig

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
        d.setVar("INSANE_SKIP_%s" % p, "ldflags dev-so textrel")
}

do_package_qa[prefuncs] += "__set_insane_skip"

# FIXME: gst-fsl-plugin looks for the .so files so we need to deploy those
FILES_${PN} += "${libdir}/imx-mm/*/*${SOLIBS} ${libdir}/imx-mm/*/*${SOLIBSDEV}"

PACKAGE_ARCH = "${MACHINE_ARCH}"

INHIBIT_SYSROOT_STRIP = "1"

COMPATIBLE_MACHINE = "(mx6|mx7|mx8)"
