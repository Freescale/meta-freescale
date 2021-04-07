FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"
SRC_URI_append_use-mainline-bsp = " \
    file://0001-frontend-dri-copy-image-use-in-dup_image.patch \
    file://0002-dri-bring-back-use-flags-for-createImageWithModifier.patch \
    file://0003-frontend-dri-add-EXPLICIT_FLUSH-hint-in-dri2_resourc.patch \
    file://0004-etnaviv-remove-double-assigment-of-surface-texture.patch \
    file://0005-etnaviv-compact-etna_state_updates.patch \
    file://0006-etnaviv-flush-used-render-buffers-on-context-flush-w.patch \
"

PROVIDES_remove_imxgpu   = "virtual/egl"
PROVIDES_remove_imxgpu3d = "virtual/libgl virtual/libgles1 virtual/libgles2"

PACKAGECONFIG_remove_imxgpu   = "egl gbm"
PACKAGECONFIG_remove_imxgpu3d = "gles"

# FIXME: mesa should support 'x11-no-tls' option
python () {
    overrides = d.getVar("OVERRIDES").split(":")
    if "imxgpu2d" not in overrides:
        return

    x11flag = d.getVarFlag("PACKAGECONFIG", "x11", False)
    d.setVarFlag("PACKAGECONFIG", "x11", x11flag.replace("--enable-glx-tls", "--enable-glx"))
}

# Enable Etnaviv and Freedreno support
PACKAGECONFIG_append_use-mainline-bsp = " gallium etnaviv kmsro freedreno"

# For NXP BSP, GPU drivers don't support dri
PACKAGECONFIG_remove_imxgpu_use-nxp-bsp = "dri"

# mainline/etnaviv:
RRECOMMENDS_${PN}-megadriver_append_use-mainline-bsp = " libdrm-etnaviv mesa-etnaviv-env"

BACKEND = \
    "${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'wayland', \
        bb.utils.contains('DISTRO_FEATURES',     'x11',     'x11', \
                                                             'fb', d), d)}"

# FIXME: Dirty hack to allow use of Vivante GPU libGL binary
do_install_append_imxgpu3d () {
    rm -f ${D}${libdir}/libGL.* \
          ${D}${includedir}/GL/gl.h \
          ${D}${includedir}/GL/glcorearb.h \
          ${D}${includedir}/GL/glext.h \
          ${D}${includedir}/GL/glx.h \
          ${D}${includedir}/GL/glxext.h
    if [ "${BACKEND}" = "x11" ]; then
        rm -f ${D}${libdir}/pkgconfig/gl.pc
    fi
}

do_install_append_imxgpu () {
    rm -rf ${D}${includedir}/KHR
}
