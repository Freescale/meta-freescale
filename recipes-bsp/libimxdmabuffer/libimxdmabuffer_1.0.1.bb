DESCRIPTION = 'Library for allocating and managing physically contiguous memory \
               ("DMA memory" or "DMA buffers") on i.MX devices.'
HOMEPAGE = "https://github.com/Freescale/libimxdmabuffer"
LICENSE = "LGPL-2.1-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=38fa42a5a6425b26d2919b17b1527324"
SECTION = "base"

PV .= "+git${SRCPV}"

SRCBRANCH ?= "master"
SRCREV = "d2058aa404ee1e8e8abd552c6a637787bcdcf514"
SRC_URI = "git://github.com/Freescale/libimxdmabuffer.git;branch=${SRCBRANCH};protocol=https \
           file://run-ptest \
          "


S = "${WORKDIR}/git"

inherit pkgconfig waf use-imx-headers ptest

EXTRA_OECONF = "--imx-linux-headers-path=${STAGING_INCDIR_IMX} \
                --libdir=${libdir} \
                ${PACKAGECONFIG_CONFARGS}"

# If imxdpu is in use, the DPU is also used for implementing
# libg2d. However, that implementation's g2d_alloc() function
# is broken, so we cannot use it.
LIBG2D_PACKAGECONFIG = "g2d"
LIBG2D_PACKAGECONFIG:imxdpu = ""

PACKAGECONFIG ?= " "
PACKAGECONFIG:append:imxgpu2d = " ${LIBG2D_PACKAGECONFIG}"
PACKAGECONFIG:append:imxipu   = " ipu"
PACKAGECONFIG:append:imxpxp   = " pxp"
PACKAGECONFIG:append:mx8m-nxp-bsp     = " ion dwl"
PACKAGECONFIG:append:mx8qm-nxp-bsp    = " ion"
PACKAGECONFIG:append:mx8qxp-nxp-bsp   = " ion"

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

COMPATIBLE_MACHINE = "(imx-nxp-bsp)"
