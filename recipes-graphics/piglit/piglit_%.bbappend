FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

PACKAGECONFIG:remove:imxgpu = "glx"
PACKAGECONFIG:append:mx8-nxp-bsp = " opencl"
