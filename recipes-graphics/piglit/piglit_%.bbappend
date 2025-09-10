FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://0001-tests-Fix-cl-test-Include-Directories-error-Error-0-.patch \
            file://0002-cl-Add-mutually-exclusive-memory-flags-for-CL_MEM_KE.patch"

PACKAGECONFIG:append = " ${PACKAGECONFIG_APPEND}"
PACKAGECONFIG:remove = " ${PACKAGECONFIG_REMOVE}"

PACKAGECONFIG_APPEND ?= " \
    gbm \
    opencl \
    ${@bb.utils.filter('DISTRO_FEATURES', 'vulkan', d)}"
PACKAGECONFIG_APPEND:imxgpu:mx6-nxp-bsp  = ""
PACKAGECONFIG_APPEND:imxgpu:mx7-nxp-bsp  = ""

PACKAGECONFIG_REMOVE ?= ""
PACKAGECONFIG_REMOVE:imxgpu              = "glx"
PACKAGECONFIG_REMOVE:imxgpu:mx6-nxp-bsp  = "glx x11"
PACKAGECONFIG_REMOVE:imxgpu:mx7-nxp-bsp  = "glx x11"

PACKAGECONFIG[gbm] = "-DPIGLIT_USE_GBM=1,-DPIGLIT_USE_GBM=0,virtual/libgbm"

CFLAGS:append:imxgpu:toolchain-clang = " -Wno-error=int-conversion"
