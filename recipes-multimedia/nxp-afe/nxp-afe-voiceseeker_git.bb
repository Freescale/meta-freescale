# Copyright 2021-2024 NXP

DESCRIPTION = "NXP RetuneDSP Voice Seeker Libraries"
SECTION = "multimedia"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=ca53281cc0caa7e320d4945a896fb837"

inherit pkgconfig

DEPENDS += "alsa-lib nxp-afe"

SRCBRANCH = "MM_04.10.02_2510_L6.12.49"
PV = "2.0+${SRCPV}"

NXPAFE_VOICESEEKER_SRC ?= "git://github.com/nxp-imx/imx-voiceui.git;protocol=https"

# FIX 1: Add destsuffix=${BP}
# This forces the git fetcher to unpack into a folder named 'nxp-afe-voiceseeker-2.0+...'
# This aligns the physical directory with BitBake's default S variable.
SRC_URI = "${NXPAFE_VOICESEEKER_SRC};branch=${SRCBRANCH};destsuffix=${BP}"

SRCREV = "f2ff8703685b511371c3475f8c52c73dc1d21f32"

EXTRA_CONF = "--enable-armv8 --bindir=/unit_tests/ --libdir=${libdir}"

EXTRA_OEMAKE:mx8-nxp-bsp = "BUILD_ARCH=CortexA53"
EXTRA_OEMAKE:mx8ulp-nxp-bsp = "BUILD_ARCH=CortexA35"
EXTRA_OEMAKE:mx9-nxp-bsp = "BUILD_ARCH=CortexA55"

# FIX 2: Simplify do_compile
# We removed the manual 'cd ${WORKDIR}/git'.
# BitBake automatically runs do_compile inside ${S}, which is now correct thanks to Fix 1.
do_compile () {
    oe_runmake all
}

do_install() {
    install -d ${D}${libdir}/nxp-afe
    install -d ${D}/unit_tests/nxp-afe

    # FIX 3: Use ${S} instead of ${UNPACKDIR}/git
    # Now that we fixed the unpack location, we use the standard ${S} variable.
    install -m 0644 ${S}/release/*.so.2.0    ${D}${libdir}/nxp-afe/
    ln -sf -r ${D}${libdir}/nxp-afe/libvoiceseekerlight.so.2.0 ${D}${libdir}/nxp-afe/libvoiceseekerlight.so

    install -m 0755 ${S}/release/voice_ui_app    ${D}/unit_tests/nxp-afe
    install -m 0644 ${S}/release/Config.ini    ${D}/unit_tests/nxp-afe
}

PACKAGE_ARCH = "${MACHINE_ARCH}"

FILES:${PN} += "${libdir}/nxp-afe/* \
                /unit_tests/* \
"
INSANE_SKIP:${PN} += "dev-so"

COMPATIBLE_MACHINE = "(mx8-nxp-bsp|mx9-nxp-bsp)"
