DESCRIPTION = "frontend for the i.MX6 / i.MX8 VPU hardware video engines"
HOMEPAGE = "https://github.com/Freescale/libimxvpuapi"
LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM = "file://LICENSE;md5=38fa42a5a6425b26d2919b17b1527324"
SECTION = "multimedia"
DEPENDS = "virtual/imxvpu libimxdmabuffer"
# Add imx-vpu-hantro-vc as dependency for being
# able to encode video using the VC8000E encoder
DEPENDS_append_mx8mp = " imx-vpu-hantro-vc"

PV .= "+git${SRCPV}"

SRCBRANCH ?= "master"
SRCREV = "6f803f46d6b53a08cf02fc3d440072e01e2f3a09"
SRC_URI = "git://github.com/Freescale/libimxvpuapi.git;branch=${SRCBRANCH};protocol=https"

S = "${WORKDIR}/git"

inherit waf pkgconfig use-imx-headers

IMX_PLATFORM_mx6 = "imx6"
IMX_PLATFORM_mx8mq = "imx8m"
IMX_PLATFORM_mx8mm = "imx8mm"
IMX_PLATFORM_mx8mp = "imx8mp"

EXTRA_OECONF = "--imx-platform=${IMX_PLATFORM} --libdir=${libdir} --imx-headers=${STAGING_INCDIR_IMX} --sysroot-path=${RECIPE_SYSROOT}"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(imxvpu)"
