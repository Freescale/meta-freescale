SUMMARY = "Graphics libraries and driver for i.MX Mali GPU"
SUMMARY:${PN}-dual = "Mali/Mesa dual-driver switcher utility"
DESCRIPTION = "Arm Mali GPU user-space graphics libraries and driver for i.MX SoCs."
DESCRIPTION:${PN}-dual = "Utility to switch between Mali DDK and Mesa GPU drivers at runtime"
HOMEPAGE = "https://www.nxp.com/"
SECTION = "libs"
DEPENDS += "\
    libdrm \
    vulkan-loader \
    wayland \
    wayland-protocols"
# In dual-driver mode, Mesa provides the build-time API, and the Mali DDK
# libraries are only installed beside it.
PROVIDES = "${@'' if d.getVar('IMX_MALI_DUAL_DRIVER') == '1' else '${MALI_IMX_PROVIDES}'}"
MALI_IMX_PROVIDES = "\
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

# Consolidate GLES dev packages
PACKAGES =+ "${PN}-libgles-dev"

# Dual-driver switcher, empty unless in dual-driver mode
PACKAGES =+ "${PN}-dual"

# In dual-driver mode, the libraries go in ${libdir}/mali-imx, beside Mesa's
IMX_MALI_DUAL_SUBDIR = "${@'/mali-imx' if d.getVar('IMX_MALI_DUAL_DRIVER') == '1' else ''}"

# Since libmali.so is loaded by dlopen, include it in the main package
FILES:${PN} += "\
    ${libdir}${IMX_MALI_DUAL_SUBDIR}/libmali.so \
    ${nonarch_base_libdir}/firmware \
    ${nonarch_libdir}/modules-load.d/*.conf"
FILES:${PN}-libegl += "\
    ${libdir}${IMX_MALI_DUAL_SUBDIR}/libEGL${SOLIBS}"
FILES:${PN}-libgbm += "\
    ${libdir}${IMX_MALI_DUAL_SUBDIR}/libgbm${SOLIBS}"
FILES:${PN}-libgles1 += "\
    ${libdir}${IMX_MALI_DUAL_SUBDIR}/libGLESv1_CM${SOLIBS}"
FILES:${PN}-libgles2 += "\
    ${libdir}${IMX_MALI_DUAL_SUBDIR}/libGLESv2${SOLIBS}"
FILES:${PN}-dual += "\
    ${bindir}/mali-imx \
    ${libexecdir}/mali-imx-boot \
    ${sysconfdir}/mali-imx \
    ${systemd_system_unitdir}/mali-imx.service"
FILES:${PN}-opencl-icd += "\
    ${sysconfdir}/OpenCL"
FILES:${PN}-libvulkan += "\
    ${datadir}/vulkan"
# ${PN}-dev is deliberately curated to just malisc; the headers and dev
# symlinks ship in the per-library -dev packages below, so the standard -dev
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
FILES:${PN}-libgles-dev += "\
    ${includedir}/GLES* \
    ${libdir}/libGLES*${SOLIBSDEV} \
    ${libdir}/pkgconfig/gles*.pc"
FILES:${PN}-opencl-icd-dev += "\
    ${bindir}/mali_clcc"

RDEPENDS:${PN} = "${@'${PN}-dual' if d.getVar('IMX_MALI_DUAL_DRIVER') == '1' else ''}"
RDEPENDS:${PN}-dual = "${PN}"
RDEPENDS:${PN}-libvulkan = "vulkan-wsi-layer"
RDEPENDS:${PN}-libgles1-dev = "${PN}-libgles-dev"
RDEPENDS:${PN}-libgles2-dev = "${PN}-libgles-dev"
RDEPENDS:${PN}-libgles3-dev = "${PN}-libgles-dev"

# libmali.so is loaded with dlopen, so an explicit runtime dependency is necessary
RDEPENDS:${PN}-libegl += "${PN}"
RDEPENDS:${PN}-libgbm += "${PN}"
RDEPENDS:${PN}-libgles1 += "${PN}"
RDEPENDS:${PN}-libgles2 += "${PN}"
RDEPENDS:${PN}-libgles3 += "${PN}"

RPROVIDES:${PN}-opencl-icd = "virtual-opencl-icd"
RPROVIDES:${PN}-libvulkan = "virtual-vulkan-icd"
RPROVIDES:${PN}-libgles-dev = "libgles-dev"

# The generic names stay provided: consumers that dlopen a GL backend depend on
# them by name rather than through shlibs, as cogl-1.0 does for libgles2.
# In dual-driver mode Mesa provides them, and the Mesa packages depend on
# these ones, so providing them here too would make a dependency loop.
MALI_IMX_RPROVIDES_GENERIC = "${@'0' if d.getVar('IMX_MALI_DUAL_DRIVER') == '1' else '1'}"
RPROVIDES:${PN}-libegl += "${@oe.utils.conditional('MALI_IMX_RPROVIDES_GENERIC', '1', 'libegl', '', d)}"
RPROVIDES:${PN}-libegl-dev += "${@oe.utils.conditional('MALI_IMX_RPROVIDES_GENERIC', '1', 'libegl-dev', '', d)}"
RPROVIDES:${PN}-libgbm += "${@oe.utils.conditional('MALI_IMX_RPROVIDES_GENERIC', '1', 'libgbm', '', d)}"
RPROVIDES:${PN}-libgbm-dev += "${@oe.utils.conditional('MALI_IMX_RPROVIDES_GENERIC', '1', 'libgbm-dev', '', d)}"
RPROVIDES:${PN}-libgles1 += "${@oe.utils.conditional('MALI_IMX_RPROVIDES_GENERIC', '1', 'libgles1', '', d)}"
RPROVIDES:${PN}-libgles1-dev += "${@oe.utils.conditional('MALI_IMX_RPROVIDES_GENERIC', '1', 'libgles1-dev', '', d)}"
RPROVIDES:${PN}-libgles2 += "${@oe.utils.conditional('MALI_IMX_RPROVIDES_GENERIC', '1', 'libgles2', '', d)}"
RPROVIDES:${PN}-libgles2-dev += "${@oe.utils.conditional('MALI_IMX_RPROVIDES_GENERIC', '1', 'libgles2-dev', '', d)}"
RPROVIDES:${PN}-libgles3 += "${@oe.utils.conditional('MALI_IMX_RPROVIDES_GENERIC', '1', 'libgles3', '', d)}"
RPROVIDES:${PN}-libgles3-dev += "${@oe.utils.conditional('MALI_IMX_RPROVIDES_GENERIC', '1', 'libgles3-dev', '', d)}"

RCONFLICTS:${PN}-libgles-dev = "libgles-dev"

FILES_SOLIBSDEV = ""
# libmali.so is shipped in the main package for dlopen consumers (above), so the
# dev-so QA check does not apply.
# nooelint: oelint.vars.insaneskip
INSANE_SKIP:${PN} = "dev-so"
DEBIAN_NOAUTONAME:${PN}-libgles-dev = "1"
RREPLACES:${PN}-libgles-dev = "libgles-dev"
ALLOW_EMPTY:${PN}-libgles1-dev = "1"
ALLOW_EMPTY:${PN}-libgles2-dev = "1"
ALLOW_EMPTY:${PN}-libgles3-dev = "1"

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

COMPATIBLE_MACHINE = "(mx95-nxp-bsp)"
