DESCRIPTION = "frontend for the i.MX6 / i.MX8 VPU hardware video engines"
HOMEPAGE = "https://github.com/Freescale/libimxvpuapi"
LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM = "file://LICENSE;md5=38fa42a5a6425b26d2919b17b1527324"
SECTION = "multimedia"
DEPENDS = "virtual/imxvpu libimxdmabuffer"

PV .= "+git${SRCPV}"

SRCBRANCH ?= "master"
SRCREV = "29c90975dcbb52ca09512693af417d73ab2800a9"
SRC_URI = "git://github.com/Freescale/libimxvpuapi.git;branch=${SRCBRANCH}"

S = "${WORKDIR}/git"

inherit waf pkgconfig use-imx-headers

IMX_PLATFORM_mx6 = "imx6"
IMX_PLATFORM_mx8mq = "imx8m"
IMX_PLATFORM_mx8mm = "imx8mm"
IMX_PLATFORM_mx8mp = "imx8mm"

EXTRA_OECONF = "--imx-platform=${IMX_PLATFORM} --libdir=${libdir} --imx-headers=${STAGING_INCDIR_IMX} --sysroot-path=${RECIPE_SYSROOT}"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(imxvpu)"
