DESCRIPTION = 'Library for allocating and managing physically contiguous memory \
               ("DMA memory" or "DMA buffers") on i.MX devices.'
HOMEPAGE = "https://github.com/Freescale/libimxdmabuffer"
LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM = "file://LICENSE;md5=38fa42a5a6425b26d2919b17b1527324"
SECTION = "base"

PV .= "+git${SRCPV}"

SRCBRANCH ?= "master"
SRCREV = "5410b44fb0c5bbd9fb1f3ba0681e65068d8cde57"
SRC_URI = "git://github.com/Freescale/libimxdmabuffer.git;branch=${SRCBRANCH};protocol=https \
           file://0001-g2d-Fix-typo.patch \
           file://run-ptest \
          "


S = "${WORKDIR}/git"

inherit pkgconfig waf use-imx-headers ptest

# dma-heap allocator is unavailable because it requires kernel 5.6 or newer.
EXTRA_OECONF = "--imx-linux-headers-path=${STAGING_INCDIR_IMX} \
                --libdir=${libdir} \
                --with-dma-heap-allocator=no \
                ${PACKAGECONFIG_CONFARGS}"

# If imxdpu is in use, the DPU is also used for implementing
# libg2d. However, that implementation's g2d_alloc() function
# is broken, so we cannot use it.
LIBG2D_PACKAGECONFIG = "g2d"
LIBG2D_PACKAGECONFIG_imxdpu = ""

PACKAGECONFIG ?= " ion "
PACKAGECONFIG_append_imxgpu2d = " ${LIBG2D_PACKAGECONFIG}"
PACKAGECONFIG_append_imxipu   = " ipu"
PACKAGECONFIG_append_imxpxp   = " pxp"
PACKAGECONFIG_append_mx8m     = " dwl"

HANTRO_CONF = "--hantro-headers-path=${STAGING_INCDIR}/hantro_dec --hantro-decoder-version=G2"

PACKAGECONFIG[dwl] = "--with-dwl-allocator=yes ${HANTRO_CONF},--with-dwl-allocator=no,imx-vpu-hantro"
PACKAGECONFIG[ion] = "--with-ion-allocator=yes,               --with-ion-allocator=no,"
PACKAGECONFIG[ipu] = "--with-ipu-allocator=yes,               --with-ipu-allocator=no,"
PACKAGECONFIG[g2d] = "--with-g2d-allocator=yes,               --with-g2d-allocator=no,virtual/libg2d"
PACKAGECONFIG[pxp] = "--with-pxp-allocator=yes,               --with-pxp-allocator=no,"

# Using do_install_ptest_base instead of do_install_ptest, since
# the default do_install_ptest_base is hardcoded to expect Makefiles.
do_install_ptest_base() {
    install -D ${WORKDIR}/run-ptest ${D}${PTEST_PATH}/run-ptest
    install -m 0755 ${B}/test-alloc ${D}${PTEST_PATH}
}

COMPATIBLE_MACHINE = "(mx6|mx7|mx8)"
