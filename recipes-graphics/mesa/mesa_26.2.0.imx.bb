# This recipe is modified for i.MX. For ease of maintenance the recipe itself
# is a verbatim copy of a OE-core recipe, so it can be diffed against
# upstream directly; the i.MX customization lives in the required
# mesa_26.2.0.imx.inc.
########### OE-core copy ##################
# Upstream hash: 2cf81caa69474c5ec2397ca37622c86519791d44

require recipes-graphics/mesa/mesa.inc

# Tab-indented in the upstream recipe at the header's Upstream hash.
# UPSTREAM-PARITY.
# nooelint: oelint.tabs.notabs
PACKAGECONFIG = " \
	expat \
	gallium \
	video-codecs \
	${@bb.utils.filter('DISTRO_FEATURES', 'x11 vulkan wayland glvnd', d)} \
	${@bb.utils.contains('DISTRO_FEATURES', 'opengl', 'opengl egl gles gbm', '', d)} \
	${@bb.utils.contains('DISTRO_FEATURES', 'opencl', 'opencl libclc gallium-llvm', '', d)} \
	${@bb.utils.contains('DISTRO_FEATURES', 'vulkan', 'zink', '', d)} \
	${@bb.utils.contains_any('DISTRO_FEATURES', 'opengl vulkan', 'virtio', '', d)} \
	xmlconfig \
	zlib \
"

# The leading blank is inside the expansion, as in the upstream recipe.
# UPSTREAM-PARITY.
# nooelint: oelint.vars.inconspaces
PACKAGECONFIG:append:x86 = "${@bb.utils.contains_any('DISTRO_FEATURES', 'opengl opencl vulkan', ' libclc gallium-llvm intel amd nouveau svga', '', d)}"
# nooelint: oelint.vars.inconspaces
PACKAGECONFIG:append:x86-64 = "${@bb.utils.contains_any('DISTRO_FEATURES', 'opengl opencl vulkan', ' libclc gallium-llvm intel amd nouveau svga', '', d)}"
# nooelint: oelint.vars.inconspaces
PACKAGECONFIG:append:i686 = "${@bb.utils.contains_any('DISTRO_FEATURES', 'opengl opencl vulkan', ' libclc gallium-llvm intel amd nouveau svga', '', d)}"
# nooelint: oelint.vars.inconspaces
PACKAGECONFIG:append:class-native = "${@bb.utils.contains_any('DISTRO_FEATURES', 'opengl opencl vulkan', ' libclc gallium-llvm amd nouveau svga', '', d)}"

GLPROVIDES = " \
    ${@bb.utils.contains('PACKAGECONFIG', 'opengl', 'virtual/libgl', '', d)} \
    ${@bb.utils.contains('PACKAGECONFIG', 'gles', 'virtual/libgles1 virtual/libgles2 virtual/libgles3', '', d)} \
    ${@bb.utils.contains('PACKAGECONFIG', 'egl', 'virtual/egl', '', d)} \
"
# Ordering comes from the upstream recipe at the header's Upstream hash.
# nooelint: oelint.var.order.PROVIDES
PROVIDES = " \
    ${@bb.utils.contains('PACKAGECONFIG', 'glvnd', '', d.getVar('GLPROVIDES'), d)} \
    ${@bb.utils.contains('PACKAGECONFIG', 'gbm', 'virtual/libgbm', '', d)} \
    virtual/mesa \
"

BBCLASSEXTEND = "native nativesdk"
########### End of OE-core copy ###########

require mesa_26.2.0.imx.inc
