# Copyright 2021-2026 NXP
SUMMARY = "i.MX Hantro V4L2 daemon"
DESCRIPTION = "V4L2 userspace daemon for the Hantro VPU on i.MX SoCs."
HOMEPAGE = "https://www.nxp.com/"
SECTION = "multimedia"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=cd8bc2a79509c22fc9c1782a151210b1"

DEPENDS = "imx-vpu-hantro"
DEPENDS:append:mx8mp-nxp-bsp = " imx-vpu-hantro-vc"

SRC_URI = "${FSL_MIRROR}/${BP}-${IMX_SRCREV_ABBREV}.tar.gz"
IMX_SRCREV_ABBREV = "dd94ffe"

SRC_URI[sha256sum] = "e561576f66e3a131e4345294c43b66a7333b491f4573e451ce9c489be8cf73ec"

S = "${UNPACKDIR}/${BP}-${IMX_SRCREV_ABBREV}"

PLATFORM:mx8mm-nxp-bsp = "IMX8MM"
PLATFORM:mx8mq-nxp-bsp = "IMX8MQ"
PLATFORM:mx8mp-nxp-bsp = "IMX8MP"

EXTRA_OEMAKE = "\
    CROSS_COMPILE="${HOST_PREFIX}"\
    SDKTARGETSYSROOT="${STAGING_DIR_TARGET}"\
    CTRLSW_HDRPATH="${STAGING_INCDIR}"\
    PLATFORM="${PLATFORM}"\
"

do_install () {
    oe_runmake install DEST_DIR="${D}"
}

PACKAGE_ARCH = "${MACHINE_SOCARCH}"
COMPATIBLE_MACHINE = "(mx8mq-nxp-bsp|mx8mm-nxp-bsp|mx8mp-nxp-bsp)"
