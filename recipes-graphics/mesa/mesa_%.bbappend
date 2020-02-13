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
PACKAGECONFIG_append_use-mainline-bsp = " gallium"
PACKAGECONFIG_append_use-mainline-bsp_armv7a = " etnaviv freedreno kmsro vc4"
PACKAGECONFIG_append_use-mainline-bsp_armv7ve = " etnaviv freedreno kmsro vc4"

# Define the osmesa block in PACKAGECONFIG for target, this block is
# not defined in the master recipe, effectively causing the osmesa
# feature to be disabled and -Dosmesa=none set.
PACKAGECONFIG_append_mx8 = " osmesa"

# Solve 'Problem encountered: OSMesa classic requires dri (classic) swrast.'
# by defining the dri swrast for mx8mm machine
DRIDRIVERS_append_mx8 = "swrast"

# Solve 'ERROR: Problem encountered: Only one swrast provider can be built'
# by excluding gallium support, dri is used together with 'classic' mesa backend.
PACKAGECONFIG_remove_mx8 = "gallium"

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
