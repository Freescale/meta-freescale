# The oelint.vars.noncoreoverride suppressions below are the layer's
# machine-gated dispatch idiom: none of it can carry an override of its own,
# and all of it is inert off-target -- measured on qemuarm64 with bitbake -e,
# meta-freescale in and out of BBLAYERS.

# Standard bbappend idiom; cannot carry an override.
# nooelint: oelint.vars.noncoreoverride
FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

# General build fixes (cl test include dirs, GCC memory-flag error).
#
# Deliberately unscoped and NOT suppressed: this really does patch piglit on
# every machine, so the finding is correct. Scoping it would break non-i.MX
# builds that currently succeed only because these fix genuine upstream
# errors. The real fix is to send both patches to openembedded-core.
SRC_URI += "file://0001-tests-Fix-cl-test-Include-Directories-error-Error-0-.patch \
            file://0002-cl-Add-mutually-exclusive-memory-flags-for-CL_MEM_KE.patch"

# Dispatch lines consume machine-specialized helper vars below.
PACKAGECONFIG:append = " ${PACKAGECONFIG_APPEND}"
# No-op off-target: the helper it consumes expands empty.
# nooelint: oelint.vars.noncoreoverride
PACKAGECONFIG:remove = "${PACKAGECONFIG_REMOVE}"

# The gbm/vulkan default is scoped to imx-generic-bsp. Unscoped it applied to
# every machine in the build, which pulled virtual/libgbm, glslang-native and
# vulkan-loader into piglit's DEPENDS and flipped PIGLIT_BUILD_VK_TESTS on for
# machines this layer does not own. The mx6/mx7 clears below still win over
# this because their overrides come later in OVERRIDES.
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
