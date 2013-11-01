# Copyright (C) 2012-2013 Freescale Semiconductor
# Copyright (C) 2012-2013 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "GPU driver and apps for imx6"
SECTION = "libs"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://usr/include/gc_vdk.h;endline=11;md5=19f5925343fa3da65596eeaa4ddb5fd3"
DEPENDS += "${@base_contains('DISTRO_FEATURES', 'x11', 'virtual/libx11 libxdamage libxext libxfixes mesa', '', d)}"
PROVIDES += "virtual/wayland-egl virtual/libgl virtual/libgal-x11 virtual/egl virtual/libgles1 virtual/libgles2 virtual/libopenvg"

PE = "1"

inherit fsl-eula-unpack

SRC_URI = "${FSL_MIRROR}/${PN}-${PV}.bin;fsl-eula=true \
           file://egl.pc \
           file://egl_x11.pc \
           file://glesv1_cm.pc \
           file://glesv1_cm_x11.pc \
           file://glesv2.pc \
           file://glesv2_x11.pc \
           file://vg.pc \
           file://vg_x11.pc \
           file://gc_wayland_protocol.pc \
           file://wayland-egl.pc \
           file://wayland-viv.pc \
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
	libvivante-dfb-mx6 \
	libwayland-viv-mx6 libwayland-viv-mx6-dev libwayland-viv-mx6-dbg \
	libgc-wayland-protocol-mx6 libgc-wayland-protocol-mx6-dev libgc-wayland-protocol-mx6-dbg \
	libwayland-egl-mx6-dev \
"

# Skip package if it does not match the machine float-point type in use
python __anonymous () {
        is_machine_hardfp = base_contains("TUNE_FEATURES", "callconvention-hard", True, False, d)
        is_package_hardfp = d.getVar("PACKAGE_FP_TYPE", True) == "hardfp"

        if is_package_hardfp != is_machine_hardfp:
                PN = d.getVar("PN", True)
                PV = d.getVar("PV", True)
                bb.debug(1, "Skipping %s (%s) as machine and package float-point does not match" % (PN, PV))
                raise bb.parse.SkipPackage("Package Float-Point is not compatible with the machine")
}

USE_X11 = "${@base_contains("DISTRO_FEATURES", "x11", "yes", "no", d)}"
USE_DFB = "${@base_contains("DISTRO_FEATURES", "directfb", "yes", "no", d)}"
USE_WL = "${@base_contains("DISTRO_FEATURES", "wayland", "yes", "no", d)}"

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

    # The preference order, based in DISTRO_FEATURES, is x11, wayland, directfb and fb
    if [ "${USE_X11}" = "yes" ]; then
        cp -r ${S}/usr/lib/dri ${D}${libdir}
        backend=x11

        install -m 0644 ${WORKDIR}/egl_x11.pc ${D}${libdir}/pkgconfig/egl.pc
        install -m 0644 ${WORKDIR}/glesv1_cm_x11.pc ${D}${libdir}/pkgconfig/glesv1_cm.pc
        install -m 0644 ${WORKDIR}/glesv2_x11.pc ${D}${libdir}/pkgconfig/glesv2.pc
        install -m 0644 ${WORKDIR}/vg_x11.pc ${D}${libdir}/pkgconfig/vg.pc
        #FIXME: WL & X11 is not currently functional
        #if [ "${USE_WL}" = "yes" ]; then
        #    install -m 0644 ${WORKDIR}/gc_wayland_protocol.pc ${D}${libdir}/pkgconfig/gc_wayland_protocol.pc
        #    install -m 0644 ${WORKDIR}/wayland-egl.pc ${D}${libdir}/pkgconfig/wayland-egl.pc
        #    install -m 0644 ${WORKDIR}/wayland-viv.pc ${D}${libdir}/pkgconfig/wayland-viv.pc
        #fi
    elif [ "${USE_WL}" = "yes" ]; then
        backend=wl

        install -m 0644 ${WORKDIR}/egl.pc ${D}${libdir}/pkgconfig/egl.pc
        install -m 0644 ${WORKDIR}/glesv1_cm.pc ${D}${libdir}/pkgconfig/glesv1_cm.pc
        install -m 0644 ${WORKDIR}/glesv2.pc ${D}${libdir}/pkgconfig/glesv2.pc
        install -m 0644 ${WORKDIR}/vg.pc ${D}${libdir}/pkgconfig/vg.pc
        install -m 0644 ${WORKDIR}/gc_wayland_protocol.pc ${D}${libdir}/pkgconfig/gc_wayland_protocol.pc
        install -m 0644 ${WORKDIR}/wayland-egl.pc ${D}${libdir}/pkgconfig/wayland-egl.pc
        install -m 0644 ${WORKDIR}/wayland-viv.pc ${D}${libdir}/pkgconfig/wayland-viv.pc
    else
        install -m 0644 ${WORKDIR}/egl.pc ${D}${libdir}/pkgconfig/egl.pc
        install -m 0644 ${WORKDIR}/glesv1_cm.pc ${D}${libdir}/pkgconfig/glesv1_cm.pc
        install -m 0644 ${WORKDIR}/glesv2.pc ${D}${libdir}/pkgconfig/glesv2.pc
        install -m 0644 ${WORKDIR}/vg.pc ${D}${libdir}/pkgconfig/vg.pc

        if [ "${USE_DFB}" = "yes" ]; then
            cp -r ${S}/usr/lib/directfb-1.6-0 ${D}${libdir}
            backend=dfb
        else
            # Regular framebuffer
            backend=fb
        fi
    fi

    # We'll only have one backend here so we rename it to generic name
    # and avoid rework in other packages, when possible
    mv ${D}${libdir}/libGL.so.1.2 ${D}${libdir}/libGL.so.1.2.0
    ln -sf libGL.so.1.2.0 ${D}${libdir}/libGL.so.1.2
    ln -sf libGL.so.1.2.0 ${D}${libdir}/libGL.so
    mv ${D}${libdir}/libEGL-${backend}.so ${D}${libdir}/libEGL.so.1.0
    ln -sf libEGL.so.1.0 ${D}${libdir}/libEGL.so.1
    ln -sf libEGL.so.1.0 ${D}${libdir}/libEGL.so
    mv ${D}${libdir}/libGAL-${backend}.so ${D}${libdir}/libGAL.so
    mv ${D}${libdir}/libVIVANTE-${backend}.so ${D}${libdir}/libVIVANTE.so

    for backend in wl x11 fb dfb; do
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

# libEGL.so is used by some demo apps from Freescale
INSANE_SKIP_libegl-mx6 += "dev-so"
FILES_libegl-mx6 = "${libdir}/libEGL${REALSOLIBS} ${libdir}/libEGL${SOLIBSDEV} "
FILES_libegl-mx6-dev = "${includedir}/EGL ${includedir}/KHR ${libdir}/pkgconfig/egl.pc"
FILES_libegl-mx6-dbg = "${libdir}/.debug/libEGL${SOLIBS}"

FILES_libgal-mx6 = "${libdir}/libGAL${SOLIBS}"
FILES_libgal-mx6-dev = "${libdir}/libGAL${SOLIBSDEV}"
FILES_libgal-mx6-dbg = "${libdir}/.debug/libGAL${SOLIBS}"

FILES_libgl-mx6 = "${libdir}/libGL${REALSOLIBS}"
FILES_libgl-mx6-dev = "${libdir}/libGL${SOLIBSDEV}"
FILES_libgl-mx6-dbg = "${libdir}/.debug/libGL.${SOLIBS}"
RDEPENDS_libgl-mx6-dev = "libgl-mesa-dev"

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
RDEPENDS_libopencl-mx6 = "libclc-mx6"

FILES_libopenvg-mx6 = "${libdir}/libOpenVG*${SOLIBS}"
FILES_libopenvg-mx6-dev = "${includedir}/VG ${libdir}/libOpenVG*${SOLIBSDEV} ${libdir}/pkgconfig/vg.pc"
FILES_libopenvg-mx6-dbg = "${libdir}/.debug/libOpenVG*${SOLIBS}"

FILES_libvdk-mx6 = "${libdir}/libVDK${SOLIBS}"
FILES_libvdk-mx6-dev = "${includedir}/*vdk.h ${libdir}/libVDK${SOLIBSDEV}"
FILES_libvdk-mx6-dbg = "${libdir}/.debug/libVDK${SOLIBS}"

FILES_libvivante-mx6 = "${libdir}/libVIVANTE${SOLIBS}"
FILES_libvivante-mx6-dev = "${libdir}/libVIVANTE${SOLIBSDEV}"
FILES_libvivante-mx6-dbg = "${libdir}/.debug/libVIVANTE${SOLIBS}"

FILES_libvivante-dri-mx6 = "${libdir}/dri/vivante_dri.so"

FILES_libvivante-dfb-mx6 = "${libdir}/directfb-1.6-0/gfxdrivers/libdirectfb_gal.so"

INSANE_SKIP_libwayland-viv-mx6 += "dev-so"
FILES_libwayland-viv-mx6 =  "${libdir}/libwayland-viv${REALSOLIBS}  ${libdir}/libwayland-viv${SOLIBS}"
FILES_libwayland-viv-mx6-dev = "${libdir})/libwayland-viv${SOLIBSDEV} ${libdir}/pkgconfig/wayland-viv.pc"
FILES_libwayland-viv-mx6-dbg = "${libdir}/.debug/libwayland-viv${SOLIBS}"

INSANE_SKIP_libgc-wayland-protocol-mx6 += "dev-so"
FILES_libgc-wayland-protocol-mx6 = "${libdir}/libgc_wayland_protocol${REALSOLIBS} ${libdir}/libgc_wayland_protocol${SOLIBS}"
FILES_libgc-wayland-protocol-mx6-dev = "${libdir}/libgc_wayland_protocol${SOLIBSDEV} ${libdir}/pkgconfig/gc_wayland_protocol.pc"
FILES_libgc-wayland-protocol-mx6-dbg = "${libdir}/libgc_wayland_protocol${SOLIBS}"

FILES_libwayland-egl-mx6-dev = "${libdir}/pkgconfig/wayland-egl.pc"

COMPATIBLE_MACHINE = "(mx6)"
