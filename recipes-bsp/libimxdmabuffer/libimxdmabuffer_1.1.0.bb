DESCRIPTION = 'Library for allocating and managing physically contiguous memory \
               ("DMA memory" or "DMA buffers") on i.MX devices.'
HOMEPAGE = "https://github.com/Freescale/libimxdmabuffer"
LICENSE = "LGPL-2.1-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=38fa42a5a6425b26d2919b17b1527324"
SECTION = "base"

PV .= "+git${SRCPV}"

SRCBRANCH ?= "master"
SRCREV = "5e15481b71468e689f7529d7609dc31790275504"
SRC_URI = "git://github.com/Freescale/libimxdmabuffer.git;branch=${SRCBRANCH};protocol=https \
           file://run-ptest \
          "


S = "${WORKDIR}/git"

inherit pkgconfig waf use-imx-headers ptest

# Device node path to the DMA-BUF heap to use in the dma-heap allocator.
# This must be one that allocates physically contiguous memory, otherwise
# it cannot allocate usable DMA buffers.
DMA_HEAP_DEVICE_NODE_PATH = "/dev/dma_heap/linux,cma"

EXTRA_OECONF = "--imx-linux-headers-path=${STAGING_INCDIR_IMX} \
                --dma-heap-device-node-path=${DMA_HEAP_DEVICE_NODE_PATH} \
                --libdir=${libdir} \
                ${PACKAGECONFIG_CONFARGS}"

# If imxdpu is in use, the DPU is also used for implementing
# libg2d. However, that implementation's g2d_alloc() function
# is broken, so we cannot use it.
LIBG2D_PACKAGECONFIG = "g2d"
LIBG2D_PACKAGECONFIG:imxdpu = ""

PACKAGECONFIG ?= " "
PACKAGECONFIG:append:imxgpu2d         = " ${LIBG2D_PACKAGECONFIG}"
PACKAGECONFIG:append:imxipu           = " ipu"
PACKAGECONFIG:append:imxpxp           = " pxp"
# All i.MX8 machines can use the dma-heap allocator. i.MX8m ones
# can also use the DWL allocator, though dma-heap is preferred.
# Vendor kernels that are older than kernel 5.6 cannot use
# dma-heap, however, and should use ion instead, since the
# former is not available pre-5.6.
PACKAGECONFIG:append:mx8-nxp-bsp      = " dma-heap"
PACKAGECONFIG:append:mx8m-nxp-bsp     = " dwl"

HANTRO_CONF = "--hantro-headers-path=${STAGING_INCDIR}/hantro_dec --hantro-decoder-version=G2"

PACKAGECONFIG[dwl]      = "--with-dwl-allocator=yes ${HANTRO_CONF},--with-dwl-allocator=no,imx-vpu-hantro"
PACKAGECONFIG[ion]      = "--with-ion-allocator=yes,               --with-ion-allocator=no,"
PACKAGECONFIG[ipu]      = "--with-ipu-allocator=yes,               --with-ipu-allocator=no,"
PACKAGECONFIG[g2d]      = "--with-g2d-allocator=yes,               --with-g2d-allocator=no,virtual/libg2d"
PACKAGECONFIG[pxp]      = "--with-pxp-allocator=yes,               --with-pxp-allocator=no,"
PACKAGECONFIG[dma-heap] = "--with-dma-heap-allocator=yes,          --with-dma-heap-allocator=no,"

# Using do_install_ptest_base instead of do_install_ptest, since
# the default do_install_ptest_base is hardcoded to expect Makefiles.
do_install_ptest_base() {
    install -D ${WORKDIR}/run-ptest ${D}${PTEST_PATH}/run-ptest
    install -m 0755 ${B}/test-alloc ${D}${PTEST_PATH}
}

COMPATIBLE_MACHINE = "(imx-nxp-bsp)"
