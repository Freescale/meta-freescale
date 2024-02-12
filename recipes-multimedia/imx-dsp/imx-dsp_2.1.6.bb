# Copyright 2017-2023 NXP

DESCRIPTION = "i.MX DSP Wrapper, Firmware Binary, Codec Libraries"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=2827219e81f28aba7c6a569f7c437fa7"


inherit fsl-eula-unpack autotools pkgconfig

SRC_URI = "${FSL_MIRROR}/${BP}.bin;fsl-eula=true"

SRC_URI[md5sum] = "07ff97f93abe2bc6117a120e534e70d4"
SRC_URI[sha256sum] = "5eab0259228746499990afe8de758a6ed505ec3fbd8a8adfc87a83c46a69bf2c"

EXTRA_OECONF = " \
    -datadir=${base_libdir}/firmware \
    --bindir=/unit_tests \
    ${@bb.utils.contains('TUNE_FEATURES', 'aarch64', '--enable-armv8', '', d)} \
"

HIFI4_PLATFORM               ?= "HIFI4_PLATFORM_IS_UNDEFINED"
HIFI4_PLATFORM:mx8qm-nxp-bsp  = "imx8qmqxp"
HIFI4_PLATFORM:mx8qxp-nxp-bsp = "imx8qmqxp"
HIFI4_PLATFORM:mx8dx-nxp-bsp  = "imx8qmqxp"
HIFI4_PLATFORM:mx8mp-nxp-bsp  = "imx8mp"
HIFI4_PLATFORM:mx8ulp-nxp-bsp = "imx8ulp"

UNSUPPORTED_TESTS                = "dsp_tflm"
UNSUPPORTED_TESTS:mx8ulp-nxp-bsp = ""

do_install:append () {
    # Remove firmware not for this platform
    find ${D}/${base_libdir}/firmware/imx/dsp -name hifi4_*.bin -not -name *${HIFI4_PLATFORM}* -exec rm {} \;
    # Set the expected generic name for the firmware
    mv ${D}/${base_libdir}/firmware/imx/dsp/hifi4_${HIFI4_PLATFORM}.bin ${D}/${base_libdir}/firmware/imx/dsp/hifi4.bin
    # Remove unit tests not for this platform
    for unsupported_test in ${UNSUPPORTED_TESTS}; do
        find ${D}/unit_tests/DSP -name $unsupported_test* -exec rm {} \;
    done
}

FILES:${PN} = "${libdir}/imx-mm/audio-codec/dsp \
               ${libdir}/imx-mm/audio-codec/wrap \
               ${base_libdir}/firmware/imx/dsp \
               /unit_tests \
"
RDEPENDS:${PN} += "imx-dsp-codec-ext"

INSANE_SKIP:${PN} = "already-stripped arch ldflags dev-so"

# Fix strip command failed: 'Unable to recognise the format of the input file'
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INHIBIT_SYSROOT_STRIP = "1"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mx8qm-nxp-bsp|mx8qxp-nxp-bsp|mx8dx-nxp-bsp|mx8mp-nxp-bsp|mx8ulp-nxp-bsp)"
