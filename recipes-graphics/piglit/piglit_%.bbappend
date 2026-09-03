# Standard bbappend idiom; cannot carry an override.
# nooelint: oelint.vars.noncoreoverride
FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

# Both fix upstream cl tests generally, not on i.MX only, so scoping them would
# leave the tests broken on every other machine. Both are filed upstream.
# nooelint: oelint.vars.noncoreoverride
SRC_URI += "file://0001-tests-Fix-cl-test-Include-Directories-error-Error-0-.patch \
            file://0002-cl-Add-mutually-exclusive-memory-flags-for-CL_MEM_KE.patch"

# Dispatch lines consume machine-specialized helper vars below.
# No-op off-target: the helper it consumes expands empty.
# nooelint: oelint.vars.noncoreoverride
PACKAGECONFIG:append = " ${PACKAGECONFIG_APPEND}"
# No-op off-target: the helper it consumes expands empty.
# nooelint: oelint.vars.noncoreoverride
PACKAGECONFIG:remove = "${PACKAGECONFIG_REMOVE}"

# The gbm/vulkan default is scoped to imx-generic-bsp; unscoped it pulled
# virtual/libgbm, glslang-native and vulkan-loader into DEPENDS and flipped
# PIGLIT_BUILD_VK_TESTS on for machines this layer does not own. The mx6/mx7
# clears below still win, their overrides coming later in OVERRIDES. The empty
# base default cannot itself be scoped.
# nooelint: oelint.vars.noncoreoverride
PACKAGECONFIG_APPEND ?= ""
PACKAGECONFIG_APPEND:imx-generic-bsp ?= "\
    gbm \
    ${@bb.utils.filter('DISTRO_FEATURES', 'vulkan', d)}"
PACKAGECONFIG_APPEND:append:imxviv:mx8-nxp-bsp = " opencl"
PACKAGECONFIG_APPEND:imxgpu:mx6-nxp-bsp = ""
PACKAGECONFIG_APPEND:imxgpu:mx7-nxp-bsp = ""

# Fallback default for the machine overrides below.
# nooelint: oelint.vars.noncoreoverride
PACKAGECONFIG_REMOVE ?= ""
PACKAGECONFIG_REMOVE:imxgpu = "glx"
PACKAGECONFIG_REMOVE:imxgpu:mx6-nxp-bsp = "glx x11"
PACKAGECONFIG_REMOVE:imxgpu:mx7-nxp-bsp = "glx x11"

PACKAGECONFIG[gbm] = "-DPIGLIT_USE_GBM=1,-DPIGLIT_USE_GBM=0,virtual/libgbm"

CFLAGS:append:imxgpu:toolchain-clang = " -Wno-error=int-conversion"
