PACKAGECONFIG_remove_mx5 = "egl gles"
PROVIDES_remove_mx5 = "virtual/libgles1 virtual/libgles2 virtual/egl"

PACKAGECONFIG_remove_mx6 = "egl gles"

# i.MX6SL uses mesa software rendering

PROVIDES_remove_mx6 = "virtual/libgles1 virtual/libgles2 virtual/egl"
PROVIDES_remove_mx6q = "virtual/libgl"
PROVIDES_remove_mx6dl = "virtual/libgl"

USE_VIV_LIBGL = "yes"
USE_VIV_LIBGL_mx6sl = "no"

# FIXME: Dirty hack to allow use of Vivante GPU libGL binary
do_install_append_mx6 () {
    if [ "${USE_VIV_LIBGL}" = "yes" ]; then
        rm -f ${D}${libdir}/libGL.*
    fi
}
