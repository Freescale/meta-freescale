PACKAGECONFIG:remove:imxgpu = "glx"

PACKAGECONFIG:append:mx8-nxp-bsp = " opencl"
PACKAGECONFIG:append:mx9-nxp-bsp = " opencl"

CFLAGS:append:imxgpu:toolchain-clang = " -Wno-error=int-conversion"
