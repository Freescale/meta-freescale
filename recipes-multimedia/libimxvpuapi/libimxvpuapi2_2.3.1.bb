SUMMARY = "i.MX VPU API library (v2)"
DESCRIPTION = "frontend for the i.MX6 / i.MX8 VPU hardware video engines"
HOMEPAGE = "https://github.com/Freescale/libimxvpuapi"
SECTION = "multimedia"
LICENSE = "LGPL-2.1-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=38fa42a5a6425b26d2919b17b1527324"
DEPENDS = "libimxdmabuffer virtual/imxvpu"
# Add imx-vpu-hantro-vc as dependency for being
# able to encode video using the VC8000E encoder
DEPENDS:append:mx8mp-nxp-bsp = " imx-vpu-hantro-vc"

PV .= "+git${SRCPV}"

SRCBRANCH ?= "master"
SRC_URI = "git://github.com/Freescale/libimxvpuapi.git;branch=${SRCBRANCH};protocol=https"
SRCREV = "37095a854aa176bb763a25ce98ceb6a787501271"

inherit waf pkgconfig use-imx-headers python3native

IMX_PLATFORM:mx6-nxp-bsp = "imx6"
IMX_PLATFORM:mx8mq-nxp-bsp = "imx8m"
IMX_PLATFORM:mx8mm-nxp-bsp = "imx8mm"
IMX_PLATFORM:mx8mp-nxp-bsp = "imx8mp"

EXTRA_OECONF = "--imx-platform=${IMX_PLATFORM} --libdir=${libdir} --imx-headers=${STAGING_INCDIR_IMX} --sysroot-path=${RECIPE_SYSROOT}"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(imxvpu)"
