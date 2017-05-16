PACKAGECONFIG_remove_imxgpu2d = "egl gles"

PROVIDES_remove_imxgpu2d = "virtual/libgles1 virtual/libgles2 virtual/libopenvg virtual/egl"
PROVIDES_remove_imxgpu3d = "virtual/libgl"

# FIXME: mesa should support 'x11-no-tls' option
python () {
    overrides = d.getVar("OVERRIDES", True).split(":")
    if "imxgpu2d" not in overrides:
        return

    x11flag = d.getVarFlag("PACKAGECONFIG", "x11", False)
    d.setVarFlag("PACKAGECONFIG", "x11", x11flag.replace("--enable-glx-tls", "--enable-glx"))
}

# Enable Etnaviv support
PACKAGECONFIG_append_use-mainline-bsp = " gallium"
GALLIUMDRIVERS_append_use-mainline-bsp = ",etnaviv,imx"

# FIXME: Dirty hack to allow use of Vivante GPU libGL binary
do_install_append_imxgpu3d () {
    rm -f ${D}${libdir}/libGL.* \
          ${D}${includedir}/GL/gl.h \
          ${D}${includedir}/GL/glext.h
}
