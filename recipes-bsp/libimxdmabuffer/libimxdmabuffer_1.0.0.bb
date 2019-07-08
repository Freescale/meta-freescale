DESCRIPTION = 'Library for allocating and managing physically contiguous memory \
               ("DMA memory" or "DMA buffers") on i.MX devices.'
HOMEPAGE = "https://github.com/Freescale/libimxdmabuffer"
LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM = "file://LICENSE;md5=38fa42a5a6425b26d2919b17b1527324"
SECTION = "base"

PV = "1.0.0+git${SRCPV}"

SRCBRANCH ?= "master"
SRCREV = "db17cb57d1087cf590b28487c43cb5c47bf76fe7"
SRC_URI = "git://github.com/Freescale/libimxdmabuffer.git;branch=${SRCBRANCH}"

S = "${WORKDIR}/git"

inherit pkgconfig waf use-imx-headers ptest

EXTRA_OECONF = "--imx-linux-headers-path=${STAGING_INCDIR_IMX} \
                --libdir=${libdir} \
                ${PACKAGECONFIG_CONFARGS}"

PACKAGECONFIG ?= " "
PACKAGECONFIG_append_imxgpu2d = " g2d"
PACKAGECONFIG_append_imxipu   = " ipu"
PACKAGECONFIG_append_imxpxp   = " pxp"
PACKAGECONFIG_append_mx8m     = " dwl ion"

HANTRO_CONF = "--hantro-headers-path=${STAGING_INCDIR}/hantro_dec --hantro-decoder-version=G2"

PACKAGECONFIG[dwl] = "--with-dwl-allocator=yes ${HANTRO_CONF},--with-dwl-allocator=no,imx-vpu-hantro"
PACKAGECONFIG[ion] = "--with-ion-allocator=yes,               --with-ion-allocator=no,"
PACKAGECONFIG[ipu] = "--with-ipu-allocator=yes,               --with-ipu-allocator=no,"
PACKAGECONFIG[g2d] = "--with-g2d-allocator=yes,               --with-g2d-allocator=no,virtual/libg2d"
PACKAGECONFIG[pxp] = "--with-pxp-allocator=yes,               --with-pxp-allocator=no,"

do_install_ptest () {
    install -d ${D}${PTEST_PATH}/tests
    install -m 0755 ${B}/test-alloc ${D}${PTEST_PATH}/tests
}

COMPATIBLE_MACHINE = "(mx6|mx7|mx8)"
