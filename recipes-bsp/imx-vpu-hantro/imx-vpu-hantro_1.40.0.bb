# Copyright (C) 2017-2020,2026 NXP

SUMMARY = "i.MX Hantro VPU library"
DESCRIPTION = "Hantro video processing unit (VPU) codec library for i.MX SoCs."
HOMEPAGE = "https://www.nxp.com/"
SECTION = "multimedia"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=bc649096ad3928ec06a8713b8d787eac"

PROVIDES = "virtual/imxvpu"

SRC_URI = "\
    ${FSL_MIRROR}/${BP}-${IMX_SRCREV_ABBREV}.bin;fsl-eula=true \
"
IMX_SRCREV_ABBREV = "52c7e45"

SRC_URI[sha256sum] = "cdfb28cdec4e26ef84ee8c54be89a1da269eaeb4b51eafe689eb084ac20c7d84"

S = "${UNPACKDIR}/${BP}-${IMX_SRCREV_ABBREV}"

inherit fsl-eula-unpack use-imx-headers

PARALLEL_MAKE = "-j 1"

PLATFORM:mx8mm-nxp-bsp = "IMX8MM"
PLATFORM:mx8mq-nxp-bsp = "IMX8MQ"
PLATFORM:mx8mp-nxp-bsp = "IMX8MP"

#| ../../source/h264high/h264decapi.c:1803:22: error: assignment to 'const u8 *' {aka 'const unsigned char *'} from incompatible pointer type 'u32 *' {aka 'unsigned int *'} [-Wincompatible-pointer-types]
#|  1803 |             ref_data = ref.virtual_address;
#| ../../source/h264high/h264decapi.c:2086:22: error: assignment to 'const u8 *' {aka 'const unsigned char *'} from incompatible pointer type 'u32 *' {aka 'unsigned int *'} [-Wincompatible-pointer-types]
#|  2086 |             ref_data = ref.virtual_address;

CFLAGS += "-Wno-error=incompatible-pointer-types"
EXTRA_OEMAKE = "\
    CROSS_COMPILE="${HOST_PREFIX}"\
    SDKTARGETSYSROOT="${STAGING_DIR_TARGET}"\
    PLATFORM="${PLATFORM}"\
"

do_install () {
    oe_runmake install DEST_DIR="${D}"
}

PACKAGE_ARCH = "${MACHINE_SOCARCH}"

FILES:${PN} += "/unit_tests"

COMPATIBLE_MACHINE = "(mx8mq-nxp-bsp|mx8mm-nxp-bsp|mx8mp-nxp-bsp)"
