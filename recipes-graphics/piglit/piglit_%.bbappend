FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://0005-cmake-Don-t-enable-GLX-if-tests-are-disabled.patch"

PACKAGECONFIG ?= "${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'x11 glx', '', d)}"
PACKAGECONFIG:remove:imxgpu = "glx"
PACKAGECONFIG:append:mx8-nxp-bsp = " opencl"

PACKAGECONFIG[glx] = "-DPIGLIT_BUILD_GLX_TESTS=ON,-DPIGLIT_BUILD_GLX_TESTS=OFF"
PACKAGECONFIG[opencl] = "-DPIGLIT_BUILD_CL_TESTS=ON,-DPIGLIT_BUILD_CL_TESTS=OFF,opencl-icd-loader"
