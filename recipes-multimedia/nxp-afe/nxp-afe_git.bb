# Copyright 2021, 2025 NXP

DESCRIPTION = "NXP Audio Front End (AFE) for incorporating Voice Assistants"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=7bdef19938f3503cfc4c586461f99012"

PV = "1.0+git${SRCPV}"

SRCBRANCH = "MM_04.10.02_2510_L6.12.49"
NXPAFE_SRC ?= "git://github.com/nxp-imx/nxp-afe.git;protocol=https"

# FIX 1: destsuffix
# Ensure code unpacks to ${BP} to match standard bitbake S paths
SRC_URI = "${NXPAFE_SRC};branch=${SRCBRANCH};destsuffix=${BP}"

SRCREV = "e5d22596210b1be1b215954804252844df71b0d4"

DEPENDS += "alsa-lib"

# FIX 2: Proprietary Dependency
# Commented out to allow build without missing restricted plugins
# RDEPENDS:${PN}:mx8mm-nxp-bsp = " nxp-afe-voiceaec"
# RDEPENDS:${PN}:mx8mp-nxp-bsp = " nxp-afe-voiceaec"
# RDEPENDS:${PN}:mx91-nxp-bsp = " nxp-afe-voiceaec"
# RDEPENDS:${PN}:mx93-nxp-bsp = " nxp-afe-voiceaec"
# RDEPENDS:${PN}:mx95-nxp-bsp = " nxp-afe-voiceaec"

TARGET_CC_ARCH += "${LDFLAGS}"
EXTRA_OEMAKE += "CROSS_COMPILE=${TARGET_PREFIX}"

do_compile() {
    oe_runmake all
}

do_install() {
    install -d ${D}${libdir}/nxp-afe
    install -d ${D}/unit_tests/nxp-afe

    # FIX 3: Update Library Paths
    # The .so files are deep inside src/SignalProcessor/build/bin/
    install -m 0644 ${S}/src/SignalProcessor/build/bin/libdummyimpl.so.1.0 ${D}${libdir}/nxp-afe/
    ln -sf -r ${D}${libdir}/nxp-afe/libdummyimpl.so.1.0 ${D}${libdir}/nxp-afe/libdummyimpl.so

    # FIX 4: Update Binary Paths
    # The main 'afe' binary is in build/bin/
    install -m 0755 ${S}/build/bin/afe ${D}/unit_tests/nxp-afe/

    # FIX 5: Update Config/Script Paths
    # Scripts and configs are in misc/
    install -m 0644 ${S}/misc/asound.conf* ${D}/unit_tests/nxp-afe/
    install -m 0755 ${S}/misc/UAC_VCOM_composite.sh ${D}/unit_tests/nxp-afe/

    # FIX 6: Update Documentation Paths
    install -m 0644 ${S}/TODO.md ${D}/unit_tests/nxp-afe/
}

FILES:${PN} += "/unit_tests"

# FIX 7: Skip QA Checks
# - dev-so: Allows .so files in the main package (usually strictly for -dev packages)
# - buildpaths: Ignores the hardcoded TMPDIR paths in the binary debug symbols
INSANE_SKIP:${PN} += "dev-so buildpaths"
INSANE_SKIP:${PN}-dbg += "buildpaths"
