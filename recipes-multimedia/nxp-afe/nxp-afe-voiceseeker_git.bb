# Copyright 2025 NXP

DESCRIPTION = "NXP RetuneDSP Voice Seeker Libraries"
SECTION = "multimedia"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=ca53281cc0caa7e320d4945a896fb837"

inherit pkgconfig

DEPENDS += "alsa-lib nxp-afe"

PV = "2.0+git"

SRCBRANCH = "MM_04.10.0_2505_L6.12.20"
SRC_URI = "git://github.com/nxp-imx/imx-voiceui.git;protocol=https;branch=${SRCBRANCH}"

SRCREV = "737c156469eeede28fe1a0777c968becf6fea886" 

EXTRA_CONF = "--enable-armv8 --bindir=/unit_tests/ --libdir=${libdir}"

EXTRA_OEMAKE:mx8-nxp-bsp = "BUILD_ARCH=CortexA53"
EXTRA_OEMAKE:mx8ulp-nxp-bsp = "BUILD_ARCH=CortexA35"
EXTRA_OEMAKE:mx9-nxp-bsp = "BUILD_ARCH=CortexA55"

do_install() {
    install -d ${D}${libdir}/nxp-afe
    install -d ${D}/unit_tests/nxp-afe
    install -m 0644 ${S}/release/*.so.2.0    ${D}${libdir}/nxp-afe/
    ln -sf -r ${D}${libdir}/nxp-afe/libvoiceseekerlight.so.2.0 ${D}${libdir}/nxp-afe/libvoiceseekerlight.so
    install -m 0755  ${S}/release/voice_ui_app    ${D}/unit_tests/nxp-afe
    install -m 0644  ${S}/release/Config.ini    ${D}/unit_tests/nxp-afe
}

PACKAGE_ARCH = "${MACHINE_ARCH}"

FILES:${PN} += "${libdir}/nxp-afe/* \
                /unit_tests/* \
"
INSANE_SKIP:${PN} += "dev-so"

COMPATIBLE_MACHINE = "(mx8-nxp-bsp|mx9-nxp-bsp)"
