# Copyright 2020,2023 NXP

SUMMARY = "i.MX PDM-to-PCM software decimation library"
DESCRIPTION = "NXP PDM to PCM Software Decimation SIMD Library"
HOMEPAGE = "https://www.nxp.com/"
SECTION = "multimedia"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=63a38e9f392d8813d6f1f4d0d6fbe657"

SRC_URI = "${FSL_MIRROR}/${BP}.bin;fsl-eula=true"
SRC_URI[sha256sum] = "f778a7b785fc500df5573f5b810a190ddc561267b47ca54b9ddf4ae12571cfe3"

inherit fsl-eula-unpack autotools pkgconfig

INSANE_SKIP:${PN} = "already-stripped"

FILES:${PN} += "${datadir}/imx-mm"

COMPATIBLE_MACHINE = "(mx8-nxp-bsp)"

INSANE_SKIP:append:libc-musl = " file-rdeps"

RDEPENDS:${PN}:append:libc-musl = " gcompat"
