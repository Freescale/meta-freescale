# Standard bbappend idiom; cannot carry an override.
# nooelint: oelint.vars.noncoreoverride
FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

# GBM needs virtual/libgbm; PIGLIT_USE_GBM is set below.
DEPENDS:append:imx-generic-bsp = " ${@oe.utils.conditional('PIGLIT_USE_GBM', '1', 'virtual/libgbm', '', d)}"

# Both fix upstream cl tests generally, but applied unscoped they changed the
# recipe on machines this layer does not own; the cl tests are only built
# here on i.MX (opencl, below). Both are filed upstream.
SRC_URI:append:imx-generic-bsp = " \
    file://0001-tests-Fix-cl-test-Include-Directories-error-Error-0-.patch \
    file://0002-cl-Add-mutually-exclusive-memory-flags-for-CL_MEM_KE.patch \
"

# Dispatch lines consume machine-specialized helper vars below. Scoped to
# imx-generic-bsp, where the helpers are set: unscoped, the append left
# whitespace in PACKAGECONFIG on every machine.
PACKAGECONFIG:append:imx-generic-bsp = " ${PACKAGECONFIG_APPEND}"
PACKAGECONFIG:remove:imx-generic-bsp = "${PACKAGECONFIG_REMOVE}"

# The vulkan default is scoped to imx-generic-bsp; unscoped it pulled
# glslang-native and vulkan-loader into DEPENDS and flipped
# PIGLIT_BUILD_VK_TESTS on for machines this layer does not own. The mx6/mx7
# clears below still win, their overrides coming later in OVERRIDES. The empty
# base default cannot itself be scoped.
# nooelint: oelint.vars.noncoreoverride
PACKAGECONFIG_APPEND ?= ""
PACKAGECONFIG_APPEND:imx-generic-bsp ?= "${@bb.utils.filter('DISTRO_FEATURES', 'vulkan', d)}"
PACKAGECONFIG_APPEND:append:imxviv:mx8-nxp-bsp = " opencl"
PACKAGECONFIG_APPEND:imxgpu:mx6-nxp-bsp = ""
PACKAGECONFIG_APPEND:imxgpu:mx7-nxp-bsp = ""

# Fallback default for the machine overrides below.
# nooelint: oelint.vars.noncoreoverride
PACKAGECONFIG_REMOVE ?= ""
PACKAGECONFIG_REMOVE:imxgpu = "glx"
PACKAGECONFIG_REMOVE:imxgpu:mx6-nxp-bsp = "glx x11"
PACKAGECONFIG_REMOVE:imxgpu:mx7-nxp-bsp = "glx x11"

# GBM, on the same machines and with the same mx6/mx7 clears as the
# PACKAGECONFIG_APPEND above. Not a PACKAGECONFIG[gbm] flag: a flag cannot be
# scoped, and it added -DPIGLIT_USE_GBM=0 to every other machine's configure.
# Fallback default for the machine overrides below.
# nooelint: oelint.vars.noncoreoverride
PIGLIT_USE_GBM ?= "0"
PIGLIT_USE_GBM:imx-generic-bsp ?= "1"
PIGLIT_USE_GBM:imxgpu:mx6-nxp-bsp = "0"
PIGLIT_USE_GBM:imxgpu:mx7-nxp-bsp = "0"
EXTRA_OECMAKE:append:imx-generic-bsp = " -DPIGLIT_USE_GBM=${PIGLIT_USE_GBM}"

# The X11 EGL tests pass X handles where the Vivante headers declare
# Wayland types; GCC 14 and later, like clang, make those errors.
CFLAGS:append:imxgpu = " -Wno-error=int-conversion -Wno-error=incompatible-pointer-types"
