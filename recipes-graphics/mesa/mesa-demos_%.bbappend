FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append_imxgpu = " file://Replace-glWindowPos2iARB-calls-with-glWindowPos2i.patch \
                    file://fix-clear-build-break.patch \
                    file://Add-OpenVG-demos-to-support-wayland.patch"

PACKAGECONFIG_REMOVE_IF_2D_ONLY          = ""
PACKAGECONFIG_REMOVE_IF_2D_ONLY_imxgpu2d = "gles1 gles2"
PACKAGECONFIG_REMOVE_IF_2D_ONLY_imxgpu3d = ""
PACKAGECONFIG_REMOVE_IF_GPU              = ""
PACKAGECONFIG_REMOVE_IF_GPU_imxgpu       = " \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'glu x11', '', d)} \
"
PACKAGECONFIG_remove = " \
    ${PACKAGECONFIG_REMOVE_IF_2D_ONLY} \
    ${PACKAGECONFIG_REMOVE_IF_GPU} \
"

PACKAGECONFIG_APPEND_IF_GPU        = ""
PACKAGECONFIG_APPEND_IF_GPU_imxgpu = " \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'wayland vg', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES',     'x11',       'glut', '', d)} \
"
PACKAGECONFIG_append = " \
    ${PACKAGECONFIG_APPEND_IF_GPU} \
"
