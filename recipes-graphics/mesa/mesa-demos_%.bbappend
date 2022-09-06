FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:imxgpu = " \
    file://Replace-glWindowPos2iARB-calls-with-glWindowPos2i.patch \
    file://fix-clear-build-break.patch"

REQUIRED_DISTRO_FEATURES:remove:imxgpu = "x11"

PACKAGECONFIG:remove = " \
    ${PACKAGECONFIG_REMOVE_IF_2D_ONLY} \
    ${PACKAGECONFIG_REMOVE_IF_GPU}"
PACKAGECONFIG_REMOVE_IF_2D_ONLY          = ""
PACKAGECONFIG_REMOVE_IF_2D_ONLY:imxgpu2d = "gles1 gles2"
PACKAGECONFIG_REMOVE_IF_2D_ONLY:imxgpu3d = ""
PACKAGECONFIG_REMOVE_IF_GPU              = ""
PACKAGECONFIG_REMOVE_IF_GPU:imxgpu       = "x11"

PACKAGECONFIG:append = " \
    ${PACKAGECONFIG_APPEND_IF_GPU}"
PACKAGECONFIG_APPEND_IF_GPU        = ""
PACKAGECONFIG_APPEND_IF_GPU:imxgpu = "glu"

PACKAGECONFIG[glu] = ",,libglu"
