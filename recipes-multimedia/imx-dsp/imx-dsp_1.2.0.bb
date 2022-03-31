# Copyright 2017-2021 NXP

DESCRIPTION = "i.MX DSP Wrapper, Firmware Binary, Codec Libraries"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=03bcadc8dc0a788f66ca9e2b89f56c6f"

inherit fsl-eula-unpack autotools pkgconfig

SRC_URI = "${FSL_MIRROR}/${BP}.bin;fsl-eula=true"

SRC_URI[md5sum] = "238d3f0256573ca657228d7090bcb7d3"
SRC_URI[sha256sum] = "13f67f267d6d33e8be2a6c258a46cde3667258ac86435776cbf1a370de611476"

EXTRA_OECONF += " \
    -datadir=${base_libdir}/firmware --bindir=/unit_tests \
    ${@bb.utils.contains('TUNE_FEATURES', 'aarch64', '--enable-armv8', ' ', d)} \
"

RDEPENDS:${PN} += " imx-dsp-codec-ext"

HIFI4_BIN ?= "hifi4_imx8qmqxp.bin"
HIFI4_BIN:mx8mp-nxp-bsp = "hifi4_imx8mp.bin"
HIFI4_BIN:mx8ulp-nxp-bsp = "hifi4_imx8ulp.bin"

do_install:append () {
    # Rename DSP Firmware into hifi4.bin and remove unneeded binary
    mv ${D}/lib/firmware/imx/dsp/${HIFI4_BIN} ${D}/lib/firmware/imx/dsp/hifi4.bin
    find ${D}/lib/firmware/imx/dsp -name hifi4_*.bin -exec rm {} \;
}

FILES:${PN} = "${libdir}/imx-mm/audio-codec/dsp \
               ${libdir}/imx-mm/audio-codec/wrap \
               ${base_libdir}/firmware/imx/dsp \
               /unit_tests \
"

INSANE_SKIP:${PN} = "already-stripped arch ldflags dev-so"

# Fix strip command failed: 'Unable to recognise the format of the input file'
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INHIBIT_SYSROOT_STRIP = "1"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mx8qm-nxp-bsp|mx8qxp-nxp-bsp|mx8dx-nxp-bsp|mx8mp-nxp-bsp|mx8ulp-nxp-bsp)"
