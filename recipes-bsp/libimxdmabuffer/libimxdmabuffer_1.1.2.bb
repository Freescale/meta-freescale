DESCRIPTION = 'Library for allocating and managing physically contiguous memory \
               ("DMA memory" or "DMA buffers") on i.MX devices.'
HOMEPAGE = "https://github.com/Freescale/libimxdmabuffer"
LICENSE = "LGPL-2.1-only"
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

EXTRA_OECONF = "--imx-linux-headers-path=${STAGING_INCDIR_IMX} \
                --libdir=${libdir} \
                ${@bb.utils.contains_any('PACKAGECONFIG', \
                   [ 'dma-heap-cached', 'dma-heap-uncached' ], \
                   '', '--with-dma-heap-allocator=no',d)} \
                ${PACKAGECONFIG_CONFARGS}"

# If imxdpu is in use, the DPU is also used for implementing
# libg2d. However, that implementation's g2d_alloc() function
# is broken, so we cannot use it.
LIBG2D_PACKAGECONFIG = "g2d"
LIBG2D_PACKAGECONFIG:imxdpu = ""

# The dma-heap allocator is special in that it can use more than one heap device
# node. In libimxdmabuffer, only heaps that allocate physically contiguous memory
# can be used, otherwise usable DMA buffers cannot be allocated. Furthermore,
# on NXP machines, there is a dma-heap for cached and one for uncached memory.
#
# One of them has to be selected by choosing either the "dma-heap-cached" or the
# "dma-heap-uncached" PACKAGECONFIG. The uncached one is picked by default for
# performance reasons (it does not suffer from cache coherence issues).
#
# Note that the uncached heap is not available prior to imx-kernel 5.15.5;
# older kernels can only use the cached one.

CACHED_DMA_HEAP_DEVICE_NODE_PATH = "/dev/dma_heap/linux,cma"
UNCACHED_DMA_HEAP_DEVICE_NODE_PATH = "/dev/dma_heap/linux,cma-uncached"

CACHED_DMA_HEAP_CONF = "--dma-heap-device-node-path=${CACHED_DMA_HEAP_DEVICE_NODE_PATH}"
UNCACHED_DMA_HEAP_CONF = "--dma-heap-device-node-path=${UNCACHED_DMA_HEAP_DEVICE_NODE_PATH} \
                          --dma-heap-uncached-memory"

PACKAGECONFIG ?= " "
PACKAGECONFIG:append:imxgpu2d         = " ${LIBG2D_PACKAGECONFIG}"
PACKAGECONFIG:append:imxipu           = " ipu"
PACKAGECONFIG:append:imxpxp           = " pxp"
# All i.MX8 machines can use the dma-heap allocator. i.MX8m ones
# can also use the DWL allocator, though dma-heap is preferred.
# Vendor kernels that are older than kernel 5.6 cannot use
# dma-heap, however, and should use ion instead, since the
# former is not available pre-5.6. Out of the dma-heaps, we
# pick the uncached one by default (see above).
PACKAGECONFIG:append:mx8-nxp-bsp      = " dma-heap-uncached"
PACKAGECONFIG:append:mx8m-nxp-bsp     = " dwl"

HANTRO_CONF = "--hantro-headers-path=${STAGING_INCDIR}/hantro_dec --hantro-decoder-version=G2"

PACKAGECONFIG[dwl]      = "--with-dwl-allocator=yes ${HANTRO_CONF}, --with-dwl-allocator=no,imx-vpu-hantro"
PACKAGECONFIG[ion]      = "--with-ion-allocator=yes,                --with-ion-allocator=no,"
PACKAGECONFIG[ipu]      = "--with-ipu-allocator=yes,                --with-ipu-allocator=no,"
PACKAGECONFIG[g2d]      = "--with-g2d-allocator=yes,                --with-g2d-allocator=no,virtual/libg2d"
PACKAGECONFIG[pxp]      = "--with-pxp-allocator=yes,                --with-pxp-allocator=no,"
# --with-dma-heap-allocator=no is not added by these packageconfigs.
# Otherwise, it would always be added, since only one of these two
# dma-heap-* packageconfigs can be selected. Instead, that switch
# is added to EXTRA_OECONF above.
PACKAGECONFIG[dma-heap-cached]   = "--with-dma-heap-allocator=yes ${CACHED_DMA_HEAP_CONF}, \
                                    ,,,,dma-heap-uncached"
PACKAGECONFIG[dma-heap-uncached] = "--with-dma-heap-allocator=yes ${UNCACHED_DMA_HEAP_CONF}, \
                                    ,,,,dma-heap-cached"

# Using do_install_ptest_base instead of do_install_ptest, since
# the default do_install_ptest_base is hardcoded to expect Makefiles.
do_install_ptest_base() {
    install -D ${WORKDIR}/run-ptest ${D}${PTEST_PATH}/run-ptest
    install -m 0755 ${B}/test-alloc ${D}${PTEST_PATH}
}

COMPATIBLE_MACHINE = "(imx-nxp-bsp)"
