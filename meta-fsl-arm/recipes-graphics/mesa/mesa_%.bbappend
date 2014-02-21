PACKAGECONFIG_remove_mx5 = "egl gles"
PROVIDES_remove_mx5 = "virtual/libgles1 virtual/libgles2 virtual/egl"

PACKAGECONFIG_remove_mx6 = "egl gles"
PROVIDES_remove_mx6 = "virtual/libgl virtual/libgles1 virtual/libgles2 virtual/egl"

# FIXME: Dirty hack to allow use of Vivante GPU libGL binary
do_install_append_mx6 () {
    rm -f ${D}${libdir}/libGL.*
}
