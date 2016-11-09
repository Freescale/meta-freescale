DESCRIPTION = "frontend for the i.MX6 VPU hardware video engine"
HOMEPAGE = "https://github.com/Freescale/libimxvpuapi"
LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM = "file://LICENSE;md5=38fa42a5a6425b26d2919b17b1527324"
SECTION = "multimedia"
DEPENDS = "imx-vpu"

SRCBRANCH ?= "master"
SRCREV = "81a2bbd85ef2aaa56243dbf63a7352ca1758099b"
SRC_URI = "git://github.com/Freescale/libimxvpuapi.git;branch=${SRCBRANCH}"

S = "${WORKDIR}/git"

inherit waf pkgconfig

COMPATIBLE_MACHINE = "(mx6q|mx6dl)"

EXTRA_OECONF = "--libdir=${libdir}"
