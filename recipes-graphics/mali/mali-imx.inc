SUMMARY = "Graphics libraries and driver for i.MX Mali GPU"
SECTION = "libs"
DEPENDS = " \
    libdrm \
    vulkan-loader \
    wayland \
    wayland-protocols"
PROVIDES = " \
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

PACKAGES =+ " \
    ${PN}-libegl     ${PN}-libegl-dev \
    ${PN}-libgbm     ${PN}-libgbm-dev \
    ${PN}-libgles1   ${PN}-libgles1-dev \
    ${PN}-libgles2   ${PN}-libgles2-dev \
    ${PN}-libgles3   ${PN}-libgles3-dev \
    ${PN}-libvulkan  \
    ${PN}-opencl-icd ${PN}-opencl-icd-dev"

# Since libmali.so is loaded by dlopen, include it in the main package
FILES:${PN} += " \
    ${libdir}/libmali.so \
    ${nonarch_base_libdir}/firmware"
FILES_SOLIBSDEV = ""
INSANE_SKIP:${PN} = "dev-so"
FILES:${PN}-libegl = " \
    ${libdir}/libEGL${SOLIBS}"
FILES:${PN}-libgbm = " \
    ${libdir}/libgbm${SOLIBS}"
FILES:${PN}-libgles1 = " \
    ${libdir}/libGLESv1_CM${SOLIBS}"
FILES:${PN}-libgles2 = " \
    ${libdir}/libGLESv2${SOLIBS}"
FILES:${PN}-opencl-icd = " \
    ${sysconfdir}/OpenCL"
FILES:${PN}-libvulkan = " \
    ${sysconfdir}/vulkan"

FILES:${PN}-dev = " \
    ${bindir}/malisc"
FILES:${PN}-libegl-dev = " \
    ${includedir}/EGL \
    ${includedir}/KHR \
    ${libdir}/libEGL${SOLIBSDEV} \
    ${libdir}/pkgconfig/egl.pc"
FILES:${PN}-libgbm-dev = " \
    ${includedir}/gbm.h \
    ${libdir}/libgbm${SOLIBSDEV} \
    ${libdir}/pkgconfig/gbm.pc"

# Consolidate GLES dev packages
PACKAGES =+ "${PN}-libgles-dev"
FILES:${PN}-libgles-dev = " \
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

FILES:${PN}-opencl-icd-dev = " \
    ${bindir}/mali_clcc"

python __anonymous() {

    # Avoid Debian-renaming
    for p in (("libegl",    "libegl1"      ),
              ("libgbm",    "libgbm1"      ),
              ("libgles1",  "libglesv1-cm1"),
              ("libgles2",  "libglesv2-2"  ),
              ("libgles3",                 )):
        fullp = "${PN}-" + p[0]
        pkgs = "".join(' %s' % i for i in p)
        d.setVar("DEBIAN_NOAUTONAME:" + fullp, "1")
        d.appendVar("RREPLACES:"  + fullp, pkgs)
        d.appendVar("RPROVIDES:"  + fullp, pkgs)
        d.appendVar("RCONFLICTS:" + fullp, pkgs)
        # libmali.so is loaded with dlopen, so an explicit runtime dependency is necessary
        d.appendVar("RDEPENDS:"   + fullp, "${PN}")

        # For -dev, the first element is both the Debian and original name
        fullp += "-dev"
        pkgs = p[0] + "-dev"
        d.setVar("DEBIAN_NOAUTONAME:" + fullp, "1")
        d.appendVar("RREPLACES:"  + fullp, pkgs)
        d.appendVar("RPROVIDES:"  + fullp, pkgs)
        d.appendVar("RCONFLICTS:" + fullp, pkgs)
}

COMPATIBLE_MACHINE = "(mx95-nxp-bsp)"
