FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

PACKAGECONFIG:remove:imxgpu = "x11 glx"
PACKAGECONFIG:append:mx8-nxp-bsp = " opencl"
