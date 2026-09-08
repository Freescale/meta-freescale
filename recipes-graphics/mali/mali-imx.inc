SUMMARY = "Graphics libraries and driver for i.MX Mali GPU"
DESCRIPTION = "Arm Mali GPU user-space graphics libraries and driver for i.MX SoCs."
HOMEPAGE = "https://www.nxp.com/"
SECTION = "libs"
DEPENDS += "\
    libdrm \
    vulkan-loader \
    wayland \
    wayland-protocols"
PROVIDES = "\
    virtual/egl \
    virtual/libgbm \
    virtual/libgles1 \
    virtual/libgles2 \
    virtual/libgles3"

# The packaged binaries have been stripped of debug info, so disable
# operations accordingly.
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_SYSROOT_STRIP = "1"

PACKAGES =+ "\
    ${PN}-libegl     ${PN}-libegl-dev \
    ${PN}-libgbm     ${PN}-libgbm-dev \
    ${PN}-libgles1   ${PN}-libgles1-dev \
    ${PN}-libgles2   ${PN}-libgles2-dev \
    ${PN}-libgles3   ${PN}-libgles3-dev \
    ${PN}-libvulkan  \
    ${PN}-opencl-icd ${PN}-opencl-icd-dev"

# Since libmali.so is loaded by dlopen, include it in the main package
FILES:${PN} += "\
    ${libdir}/libmali.so \
    ${nonarch_base_libdir}/firmware"
FILES_SOLIBSDEV = ""
# libmali.so is shipped in the main package for dlopen consumers (above), so the
# dev-so QA check does not apply.
# nooelint: oelint.vars.insaneskip
INSANE_SKIP:${PN} = "dev-so"
FILES:${PN}-libegl += "\
    ${libdir}/libEGL${SOLIBS}"
FILES:${PN}-libgbm += "\
    ${libdir}/libgbm${SOLIBS}"
FILES:${PN}-libgles1 += "\
    ${libdir}/libGLESv1_CM${SOLIBS}"
FILES:${PN}-libgles2 += "\
    ${libdir}/libGLESv2${SOLIBS}"
FILES:${PN}-opencl-icd += "\
    ${sysconfdir}/OpenCL"
RPROVIDES:${PN}-opencl-icd = "virtual-opencl-icd"
FILES:${PN}-libvulkan += "\
    ${sysconfdir}/vulkan"
RDEPENDS:${PN}-libvulkan = "vulkan-wsi-layer"
RPROVIDES:${PN}-libvulkan = "virtual-vulkan-icd"

# ${PN}-dev is deliberately curated to just malisc; the headers and dev
# symlinks ship in the per-library -dev packages above, so the standard -dev
# default is intentionally replaced rather than extended.
# nooelint: oelint.var.filesoverride
FILES:${PN}-dev = "\
    ${bindir}/malisc"
FILES:${PN}-libegl-dev += "\
    ${includedir}/EGL \
    ${includedir}/KHR \
    ${libdir}/libEGL${SOLIBSDEV} \
    ${libdir}/pkgconfig/egl.pc"
FILES:${PN}-libgbm-dev += "\
    ${includedir}/gbm.h \
    ${libdir}/libgbm${SOLIBSDEV} \
    ${libdir}/pkgconfig/gbm.pc"

# Consolidate GLES dev packages
PACKAGES =+ "${PN}-libgles-dev"
FILES:${PN}-libgles-dev += "\
    ${includedir}/GLES* \
    ${libdir}/libGLES*${SOLIBSDEV} \
    ${libdir}/pkgconfig/gles*.pc"
DEBIAN_NOAUTONAME:${PN}-libgles-dev = "1"
RREPLACES:${PN}-libgles-dev = "libgles-dev"
RPROVIDES:${PN}-libgles-dev = "libgles-dev"
RCONFLICTS:${PN}-libgles-dev = "libgles-dev"
ALLOW_EMPTY:${PN}-libgles1-dev = "1"
ALLOW_EMPTY:${PN}-libgles2-dev = "1"
ALLOW_EMPTY:${PN}-libgles3-dev = "1"
RDEPENDS:${PN}-libgles1-dev = "${PN}-libgles-dev"
RDEPENDS:${PN}-libgles2-dev = "${PN}-libgles-dev"
RDEPENDS:${PN}-libgles3-dev = "${PN}-libgles-dev"

FILES:${PN}-opencl-icd-dev += "\
    ${bindir}/mali_clcc"

# The GPU interface packages keep their ${PN}- prefix: Debian renaming would
# drop it and collide with the generic mesa package names.
DEBIAN_NOAUTONAME:${PN}-libegl = "1"
DEBIAN_NOAUTONAME:${PN}-libegl-dev = "1"
DEBIAN_NOAUTONAME:${PN}-libgbm = "1"
DEBIAN_NOAUTONAME:${PN}-libgbm-dev = "1"
DEBIAN_NOAUTONAME:${PN}-libgles1 = "1"
DEBIAN_NOAUTONAME:${PN}-libgles1-dev = "1"
DEBIAN_NOAUTONAME:${PN}-libgles2 = "1"
DEBIAN_NOAUTONAME:${PN}-libgles2-dev = "1"
DEBIAN_NOAUTONAME:${PN}-libgles3 = "1"
DEBIAN_NOAUTONAME:${PN}-libgles3-dev = "1"

RREPLACES:${PN}-libegl += "libegl libegl1"
RREPLACES:${PN}-libegl-dev += "libegl-dev"
RREPLACES:${PN}-libgbm += "libgbm libgbm1"
RREPLACES:${PN}-libgbm-dev += "libgbm-dev"
RREPLACES:${PN}-libgles1 += "libgles1 libglesv1-cm1"
RREPLACES:${PN}-libgles1-dev += "libgles1-dev"
RREPLACES:${PN}-libgles2 += "libgles2 libglesv2-2"
RREPLACES:${PN}-libgles2-dev += "libgles2-dev"
RREPLACES:${PN}-libgles3 += "libgles3"
RREPLACES:${PN}-libgles3-dev += "libgles3-dev"

# libmali.so is loaded with dlopen, so an explicit runtime dependency is necessary
RDEPENDS:${PN}-libegl += "${PN}"
RDEPENDS:${PN}-libgbm += "${PN}"
RDEPENDS:${PN}-libgles1 += "${PN}"
RDEPENDS:${PN}-libgles2 += "${PN}"
RDEPENDS:${PN}-libgles3 += "${PN}"

RPROVIDES:${PN}-libegl += "libegl libegl1"
RPROVIDES:${PN}-libegl-dev += "libegl-dev"
RPROVIDES:${PN}-libgbm += "libgbm libgbm1"
RPROVIDES:${PN}-libgbm-dev += "libgbm-dev"
RPROVIDES:${PN}-libgles1 += "libgles1 libglesv1-cm1"
RPROVIDES:${PN}-libgles1-dev += "libgles1-dev"
RPROVIDES:${PN}-libgles2 += "libgles2 libglesv2-2"
RPROVIDES:${PN}-libgles2-dev += "libgles2-dev"
RPROVIDES:${PN}-libgles3 += "libgles3"
RPROVIDES:${PN}-libgles3-dev += "libgles3-dev"

RCONFLICTS:${PN}-libegl += "libegl libegl1"
RCONFLICTS:${PN}-libegl-dev += "libegl-dev"
RCONFLICTS:${PN}-libgbm += "libgbm libgbm1"
RCONFLICTS:${PN}-libgbm-dev += "libgbm-dev"
RCONFLICTS:${PN}-libgles1 += "libgles1 libglesv1-cm1"
RCONFLICTS:${PN}-libgles1-dev += "libgles1-dev"
RCONFLICTS:${PN}-libgles2 += "libgles2 libglesv2-2"
RCONFLICTS:${PN}-libgles2-dev += "libgles2-dev"
RCONFLICTS:${PN}-libgles3 += "libgles3"
RCONFLICTS:${PN}-libgles3-dev += "libgles3-dev"

COMPATIBLE_MACHINE = "(mx95-nxp-bsp)"
