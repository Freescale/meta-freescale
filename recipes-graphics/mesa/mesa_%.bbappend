PACKAGECONFIG_remove_mx5 = "egl gles"
PROVIDES_remove_mx5 = "virtual/libgles1 virtual/libgles2 virtual/egl"

PACKAGECONFIG_remove_mx6q  = "egl gles"
PACKAGECONFIG_remove_mx6dl = "egl gles"
PACKAGECONFIG_remove_mx6sx = "egl gles"
PACKAGECONFIG_remove_mx6sl = "egl gles"

# i.MX6SL uses mesa software rendering

PROVIDES_remove_mx6q = "virtual/libgles1 virtual/libgles2 virtual/egl virtual/libgl"
PROVIDES_remove_mx6dl = "virtual/libgles1 virtual/libgles2 virtual/egl virtual/libgl"
PROVIDES_remove_mx6sx = "virtual/libgles1 virtual/libgles2 virtual/egl virtual/libgl"
PROVIDES_remove_mx6sl = "virtual/libgles1 virtual/libgles2 virtual/egl"

USE_VIV_LIBGL = "no"
USE_VIV_LIBGL_mx6q = "yes"
USE_VIV_LIBGL_mx6dl = "yes"
USE_VIV_LIBGL_mx6sx = "yes"

# FIXME: Dirty hack to allow use of Vivante GPU libGL binary
do_install_append_mx6 () {
    if [ "${USE_VIV_LIBGL}" = "yes" ]; then
        rm -f ${D}${libdir}/libGL.*
    fi
}
EXTRA_OECONF_mx6 := "${@'${EXTRA_OECONF}'.replace('--enable-glx-tls','--enable-glx')}"
