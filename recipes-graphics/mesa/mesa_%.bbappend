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
