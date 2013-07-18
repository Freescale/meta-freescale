# Copyright (C) 2012-2013 Freescale Semiconductor
# Copyright (C) 2012-2013 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "GPU driver and apps for imx6"
SECTION = "libs"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://usr/include/gc_vdk.h;endline=11;md5=19f5925343fa3da65596eeaa4ddb5fd3"
DEPENDS += "${@base_contains('DISTRO_FEATURES', 'x11', 'virtual/libx11 libxdamage libxext libxfixes', '', d)} mesa"
PROVIDES += "virtual/libgl virtual/libgal-x11 virtual/egl virtual/libgles1 virtual/libgles2"

INC_PR = "r5"

inherit fsl-eula-unpack

SRC_URI = "${FSL_MIRROR}/${PN}-${PV}.bin;fsl-eula=true \
           file://0001-change-header-path-to-HAL.patch \
           file://gc_hal_eglplatform-remove-xlib-undefs.patch \
           file://fix-conflicting-TLS-definition.patch \
           file://egl.pc \
           file://egl_x11.pc \
           file://glesv1_cm.pc \
           file://glesv1_cm_x11.pc \
           file://glesv2.pc \
           file://glesv2_x11.pc \
          "

PACKAGES =+ "libclc-mx6 libclc-mx6-dev libclc-mx6-dbg \
	libgl-mx6 libgl-mx6-dev libgl-mx6-dbg \
	libgles-mx6 libgles-mx6-dev libgles-mx6-dbg \
	libgles2-mx6 libgles2-mx6-dev libgles2-mx6-dbg \
	libglslc-mx6 libglslc-mx6-dev libglslc-mx6-dbg \
	libopencl-mx6 libopencl-mx6-dev libopencl-mx6-dbg \
	libopenvg-mx6 libopenvg-mx6-dev libopenvg-mx6-dbg \
	libvdk-mx6 libvdk-mx6-dev libvdk-mx6-dbg \
	libegl-mx6 libegl-mx6-dev libegl-mx6-dbg \
	libgal-mx6 libgal-mx6-dev libgal-mx6-dbg \
	libvivante-mx6 libvivante-mx6-dev libvivante-mx6-dbg \
	libvivante-dri-mx6 \
"

USE_X11 = "${@base_contains("DISTRO_FEATURES", "x11", "yes", "no", d)}"
USE_DFB = "${@base_contains("DISTRO_FEATURES", "directfb", "yes", "no", d)}"

# Inhibit warnings about files being stripped.
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

# FIXME: The provided binary doesn't provide soname. If in future BSP
# release the libraries are fixed, we can drop this hack.
REALSOLIBS := "${SOLIBS}"
SOLIBS = "${SOLIBSDEV}"

# For the packages that make up the OpenGL interfaces, inject variables so that
# they don't get Debian-renamed (which would remove the -mx6 suffix).
#
# FIXME: All binaries lack GNU_HASH in elf binary but as we don't have
# the source we cannot fix it. Disable the insane check for now.
python __anonymous() {
    packages = d.getVar('PACKAGES', True).split()
    for p in packages:
        d.appendVar("INSANE_SKIP_%s" % p, " ldflags")

    for p in (("libegl", "libegl1"), ("libgl", "libgl1"),
              ("libgles1", "libglesv1-cm1"), ("libgles2", "libglesv2-2"),
              ("libgles3",)):
        fullp = p[0] + "-mx6"
        pkgs = " ".join(p)
        d.setVar("DEBIAN_NOAUTONAME_" + fullp, "1")
        d.appendVar("RREPLACES_" + fullp, pkgs)
        d.appendVar("RPROVIDES_" + fullp, pkgs)
        d.appendVar("RCONFLICTS_" + fullp, pkgs)

        # For -dev, the first element is both the Debian and original name
        fullp += "-dev"
        pkgs = p[0] + "-dev"
        d.setVar("DEBIAN_NOAUTONAME_" + fullp, "1")
        d.appendVar("RREPLACES_" + fullp, pkgs)
        d.appendVar("RPROVIDES_" + fullp, pkgs)
        d.appendVar("RCONFLICTS_" + fullp, pkgs)
}

do_install () {
    install -d ${D}${libdir}
    install -d ${D}${includedir}

    cp -a ${S}/usr/lib/*.so* ${D}${libdir}
    cp -axr ${S}/usr/include/* ${D}${includedir}
    cp -axr ${S}/opt ${D}

    install -d ${D}${libdir}/pkgconfig

    # FIXME: Remove Wayland contents
    rm -r ${D}${includedir}/wayland-viv
    find ${D}${libdir} -name '*-wl.so' -exec rm '{}' ';'
    rm ${D}${libdir}/*wayland*.so*

    if [ "${USE_X11}" = "yes" ]; then
        cp -r ${S}/usr/lib/dri ${D}${libdir}
        backend=x11

        install -m 0644 ${WORKDIR}/egl_x11.pc ${D}${libdir}/pkgconfig/egl.pc
        install -m 0644 ${WORKDIR}/glesv1_cm_x11.pc ${D}${libdir}/pkgconfig/glesv1_cm.pc
        install -m 0644 ${WORKDIR}/glesv2_x11.pc ${D}${libdir}/pkgconfig/glesv2.pc
    else
        install -m 0644 ${WORKDIR}/egl.pc ${D}${libdir}/pkgconfig/egl.pc
        install -m 0644 ${WORKDIR}/glesv1_cm.pc ${D}${libdir}/pkgconfig/glesv1_cm.pc
        install -m 0644 ${WORKDIR}/glesv2.pc ${D}${libdir}/pkgconfig/glesv2.pc

        if [ "${USE_DFB}" = "yes" ]; then
            backend=dfb
        else
            # Regular framebuffer
            backend=fb
        fi
    fi

    # We'll only have one backend here so we rename it to generic name
    # and avoid rework in other packages, when possible
    mv ${D}${libdir}/libEGL-${backend}.so ${D}${libdir}/libEGL.so.1
    ln -sf libEGL.so.1 ${D}${libdir}/libEGL.so
    mv ${D}${libdir}/libGAL-${backend}.so ${D}${libdir}/libGAL.so
    mv ${D}${libdir}/libVIVANTE-${backend}.so ${D}${libdir}/libVIVANTE.so

    for backend in x11 fb dfb; do
        find ${D}${libdir} -name "*-$backend.so" -exec rm '{}' ';'
    done

    find ${D}${libdir} -type f -exec chmod 644 {} \;
    find ${D}${includedir} -type f -exec chmod 644 {} \;
}

S = "${WORKDIR}/${PN}-${PV}"

FILES_${PN} += "/opt"
FILES_${PN}-dev = "${includedir}"
FILES_${PN}-dbg = "${libdir}/.debug /opt/viv_samples/*/*/.debug"

FILES_libclc-mx6 = "${libdir}/libCLC${SOLIBS}"
FILES_libclc-mx6-dev = "${includedir}/CL ${libdir}/libCLC${SOLIBSDEV}"
FILES_libclc-mx6-dbg = "${libdir}/.debug/libCLC${SOLIBS}"

FILES_libegl-mx6 = "${libdir}/libEGL${REALSOLIBS}"
FILES_libegl-mx6-dev = "${libdir}/libEGL${SOLIBSDEV} ${libdir}/pkgconfig/egl.pc"
FILES_libegl-mx6-dbg = "${libdir}/.debug/libEGL${SOLIBS}"

FILES_libgal-mx6 = "${libdir}/libGAL${SOLIBS}"
FILES_libgal-mx6-dev = "${libdir}/libGAL${SOLIBSDEV}"
FILES_libgal-mx6-dbg = "${libdir}/.debug/libGAL${SOLIBS}"

FILES_libgl-mx6 = "${libdir}/libGL${REALSOLIBS}"
FILES_libgl-mx6-dev = "${libdir}/libGL${SOLIBSDEV}"
FILES_libgl-mx6-dbg = "${libdir}/.debug/libGL.${SOLIBS}"

# libEGL needs to open libGLESv1.so
INSANE_SKIP_libgles-mx6 += "dev-so"
FILES_libgles-mx6 = "${libdir}/libGLESv1*${REALSOLIBS} ${libdir}/libGLESv1*${SOLIBS} ${libdir}/libGLES_*${SOLIBS}"
FILES_libgles-mx6-dev = "${includedir}/GLES ${libdir}/libGLESv1*${SOLIBS} ${libdir}/libGLES_*${SOLIBSDEV} ${libdir}/pkgconfig/glesv1_cm.pc"
FILES_libgles-mx6-dbg = "${libdir}/.debug/libGLESv1*${SOLIBS} ${libdir}/.debug/libGLES_*${SOLIBS}"

# libEGL needs to open libGLESv2.so
INSANE_SKIP_libgles2-mx6 += "dev-so"
FILES_libgles2-mx6 = "${libdir}/libGLESv2${REALSOLIBS} ${libdir}/libGLESv2${SOLIBS}"
FILES_libgles2-mx6-dev = "${includedir}/GLES2 ${libdir}/libGLESv2${SOLIBSDEV} ${libdir}/pkgconfig/glesv2.pc"
FILES_libgles2-mx6-dbg = "${libdir}/.debug/libGLESv2${SOLIBS}"
RDEPENDS_libgles2-mx6 = "libglslc-mx6"

FILES_libglslc-mx6 = "${libdir}/libGLSLC${SOLIBS}"
FILES_libglslc-mx6-dev = "${includedir}/CL ${libdir}/libGLSLC${SOLIBSDEV}"
FILES_libglslc-mx6-dbg = "${libdir}/.debug/libGLSLC${SOLIBS}"

FILES_libopencl-mx6 = "${libdir}/libOpenCL${SOLIBS}"
FILES_libopencl-mx6-dev = "${includedir}/CL ${libdir}/libOpenCL${SOLIBSDEV}"
FILES_libopencl-mx6-dbg = "${libdir}/.debug/libOpenCL${SOLIBS}"
RDEPENDS_libopencl-mx6-dev = "libclc-mx6"

FILES_libopenvg-mx6 = "${libdir}/libOpenVG*${SOLIBS}"
FILES_libopenvg-mx6-dev = "${includedir}/VG ${libdir}/libOpenVG*${SOLIBSDEV}"
FILES_libopenvg-mx6-dbg = "${libdir}/.debug/libOpenVG*${SOLIBS}"

FILES_libvdk-mx6 = "${libdir}/libVDK${SOLIBS}"
FILES_libvdk-mx6-dev = "${includedir}/*vdk.h ${libdir}/libVDK${SOLIBSDEV}"
FILES_libvdk-mx6-dbg = "${libdir}/.debug/libVDK${SOLIBS}"

FILES_libvivante-mx6 = "${libdir}/libVIVANTE${SOLIBS}"
FILES_libvivante-mx6-dev = "${libdir}/libVIVANTE${SOLIBSDEV}"
FILES_libvivante-mx6-dbg = "${libdir}/.debug/libVIVANTE${SOLIBS}"

FILES_libvivante-dri-mx6 = "${libdir}/dri/vivante_dri.so"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mx6)"
