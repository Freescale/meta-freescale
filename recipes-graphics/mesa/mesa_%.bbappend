PACKAGECONFIG_remove_mx6 = "egl gles"

PROVIDES_remove_mx6 = "virtual/libgles1 virtual/libgles2 virtual/libopenvg virtual/egl"
PROVIDES_remove_mx6q = "virtual/libgl"
PROVIDES_remove_mx6dl = "virtual/libgl"
PROVIDES_remove_mx6sx = "virtual/libgl"

USE_VIV_LIBGL = "yes"
USE_VIV_LIBGL_mx6sl = "no"

# FIXME: mesa should support 'x11-no-tls' option
python () {
    overrides = d.getVar("OVERRIDES", True).split(":")
    if "mx6" not in overrides:
        return

    extra_oeconf = d.getVar("EXTRA_OECONF", True)
    extra_oeconf = extra_oeconf.replace("--enable-glx-tls", "--enable-glx")
    d.setVar("EXTRA_OECONF", extra_oeconf)
}

# FIXME: Dirty hack to allow use of Vivante GPU libGL binary
do_install_append_mx6 () {
    if [ "${USE_VIV_LIBGL}" = "yes" ]; then
        rm -f ${D}${libdir}/libGL.* \
              ${D}${includedir}/GL/gl.h \
              ${D}${includedir}/GL/glext.h
    fi
}
