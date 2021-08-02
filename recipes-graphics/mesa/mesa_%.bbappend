FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"
SRC_URI:append:use-mainline-bsp = " \
    file://0001-dri-add-createImageWithModifiers2-interface.patch \
    file://0002-dri-add-loader_dri_create_image-helper.patch \
    file://0003-loader-dri3-convert-to-loader_dri_create_image.patch \
    file://0004-loader-dri-hook-up-createImageWithModifiers2.patch \
    file://0005-gallium-dri-copy-image-use-in-dup_image.patch \
    file://0006-dri-don-t-call-modifier-interfaces-when-modifiers_co.patch \
    file://0007-frontend-dri-add-EXPLICIT_FLUSH-hint-in-dri2_resourc.patch \
    file://0008-etnaviv-remove-double-assigment-of-surface-texture.patch \
    file://0009-etnaviv-flush-used-render-buffers-on-context-flush-w.patch \
"

PROVIDES:remove:imxgpu   = "virtual/egl"
PROVIDES:remove:imxgpu3d = "virtual/libgl virtual/libgles1 virtual/libgles2"

PACKAGECONFIG:remove:imxgpu   = "egl gbm"
PACKAGECONFIG:remove:imxgpu3d = "gles"

# FIXME: mesa should support 'x11-no-tls' option
python () {
    overrides = d.getVar("OVERRIDES").split(":")
    if "imxgpu2d" not in overrides:
        return

    x11flag = d.getVarFlag("PACKAGECONFIG", "x11", False)
    d.setVarFlag("PACKAGECONFIG", "x11", x11flag.replace("--enable-glx-tls", "--enable-glx"))
}

# Enable Etnaviv and Freedreno support
PACKAGECONFIG:append:use-mainline-bsp = " gallium etnaviv kmsro freedreno"

# For NXP BSP, GPU drivers don't support dri
PACKAGECONFIG:remove:imxgpu:use-nxp-bsp = "dri"

# mainline/etnaviv:
RRECOMMENDS:${PN}-megadriver:append:use-mainline-bsp = " libdrm-etnaviv mesa-etnaviv-env"

BACKEND = \
    "${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'wayland', \
        bb.utils.contains('DISTRO_FEATURES',     'x11',     'x11', \
                                                             'fb', d), d)}"

# FIXME: Dirty hack to allow use of Vivante GPU libGL binary
do_install:append:imxgpu3d () {
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

do_install:append:imxgpu () {
    rm -rf ${D}${includedir}/KHR
}
