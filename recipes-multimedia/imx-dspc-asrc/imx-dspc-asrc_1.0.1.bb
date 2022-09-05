# Copyright 2019 NXP
DESCRIPTION = "NXP Asynchronous Sample Rate Converter"
LICENSE = "Proprietary"
SECTION = "multimedia"
LIC_FILES_CHKSUM = "file://COPYING;md5=72c0f70181bb6e83eee6aab8de12a9f3"

SRC_URI = "${FSL_MIRROR}/${BP}.bin;fsl-eula=true"

SRC_URI[md5sum] = "d46005ee29ca603caa2e1b1e82953ff1"
SRC_URI[sha256sum] = "e79cbf4788a86a0068bbe80c37317e3332ae76e3d5c3ff2eabd71f03121fab6a"

inherit fsl-eula-unpack autotools pkgconfig

EXTRA_OECONF = "--enable-armv8 --libdir=${libdir} --bindir=/unit_tests"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_SYSROOT_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT  = "1"

FILES:${PN} += "/unit_tests ${datadir}/imx-mm"

COMPATIBLE_MACHINE = "(mx8-nxp-bsp)"
