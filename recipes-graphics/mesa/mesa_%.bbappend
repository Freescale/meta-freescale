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
PACKAGECONFIG_append_use-mainline-bsp_mx8 = " etnaviv kmsro vc4"

USE_OSMESA_ONLY ?= "no"

# Etnaviv support state for i.MX8 is unknown, therefore only enable OSMesa and
# disable Gallium for now. If you still want to enable Etnaviv, just set
# USE_OSMESA_ONLY_mx8 = "no" in your bbappend.
USE_OSMESA_ONLY_mx8 ?= "yes"

# Enable OSMesa which also requires dri (classic) swrast
PACKAGECONFIG_append = " ${@oe.utils.conditional('USE_OSMESA_ONLY', 'yes', ' osmesa', '', d)}"
PACKAGECONFIG_remove = " ${@oe.utils.conditional('USE_OSMESA_ONLY', 'yes', 'gallium', '', d)}"
DRIDRIVERS_append = "${@oe.utils.conditional('USE_OSMESA_ONLY', 'yes', 'swrast', '', d)}"

BACKEND = \
    "${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'wayland', \
        bb.utils.contains('DISTRO_FEATURES',     'x11',     'x11', \
                                                             'fb', d), d)}"

# FIXME: Dirty hack to allow use of Vivante GPU libGL binary
do_install_append_imxgpu3d () {
    rm -f ${D}${libdir}/libGL.* \
          ${D}${includedir}/GL/gl.h \
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
