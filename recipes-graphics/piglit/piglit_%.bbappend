FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

CFLAGS:append:imxgpu:toolchain-clang = " -Wno-error=int-conversion"
PACKAGECONFIG:remove:imxgpu = "x11 glx"
PACKAGECONFIG:append:mx8-nxp-bsp = " opencl"
