# Copyright 2018-2023 NXP

DESCRIPTION = "i.MX DSP Codec Wrapper and Lib owned by NXP"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=2827219e81f28aba7c6a569f7c437fa7"

inherit fsl-eula-unpack autotools pkgconfig

SRC_URI = "${FSL_MIRROR}/${BP}.bin;fsl-eula=true"

SRC_URI[md5sum] = "d211febb231717649d35553d22a50578"
SRC_URI[sha256sum] = "d5f312d742493aee7e1f1304916aca8264bcf8d3f7de7e1522d30ba33efb6a8a"

EXTRA_OECONF:append:mx8qm-nxp-bsp = " --enable-imx8qmqxp"
EXTRA_OECONF:append:mx8qxp-nxp-bsp = " --enable-imx8qmqxp"
EXTRA_OECONF:append:mx8dx-nxp-bsp = " --enable-imx8qmqxp"
EXTRA_OECONF:append:mx8mp-nxp-bsp = " --enable-imx8m"
EXTRA_OECONF:append:mx8ulp-nxp-bsp = " --enable-imx8ulp"

# Fix strip command failed: 'Unable to recognise the format of the input file'
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_SYSROOT_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INSANE_SKIP:${PN} = "arch dev-so ldflags"

FILES:${PN} += "${libdir}/imx-mm/audio-codec/dsp ${datadir}/imx-mm"
COMPATIBLE_MACHINE = "(mx8qm-nxp-bsp|mx8qxp-nxp-bsp|mx8dx-nxp-bsp|mx8mp-nxp-bsp|mx8ulp-nxp-bsp)"
