# Copyright (C) 2012-2016 Freescale Semiconductor
# Copyright (C) 2012-2016 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "GPU driver and apps for imx6"
SECTION = "libs"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://gpu-core/usr/include/gc_vdk.h;beginline=5;endline=11;md5=12c028cbbbedb4b8770267131500592c"

DEPENDS += " \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'wayland', \
       bb.utils.contains('DISTRO_FEATURES',     'x11', 'virtual/libx11 libxdamage libxext libxfixes mesa', \
                                                       '', d), d)} \
    libpng \
"

EXTRA_PROVIDES = ""
EXTRA_PROVIDES_imxgpu3d = " virtual/libgl virtual/libgles1 virtual/libgles2"
PROVIDES += "virtual/wayland-egl virtual/libgal-x11 virtual/egl virtual/libopenvg virtual/libg2d ${EXTRA_PROVIDES}"

PE = "1"

inherit fsl-eula-unpack

SRC_URI = "${FSL_MIRROR}/${PN}-${PV}.bin;fsl-eula=true"

PACKAGES =+ "libclc-mx6 libclc-mx6-dev \
	libgl-mx6 libgl-mx6-dev \
	libgles-mx6 libgles-mx6-dev \
	libgles2-mx6 libgles2-mx6-dev \
	libgles3-mx6-dev \
	libglslc-mx6 libglslc-mx6-dev \
	libopencl-mx6 libopencl-mx6-dev \
	libopenvg-mx6 libopenvg-mx6-dev \
	libvdk-mx6 libvdk-mx6-dev \
	libegl-mx6 libegl-mx6-dev \
	libgal-mx6 libgal-mx6-dev \
	libvivante-mx6 libvivante-mx6-dev \
	libvivante-dri-mx6 \
	libvsc-mx6 \
	libwayland-viv-mx6 libwayland-viv-mx6-dev \
	libgc-wayland-protocol-mx6 libgc-wayland-protocol-mx6-dev \
	libwayland-egl-mx6-dev \
	imx-gpu-viv-g2d imx-gpu-viv-g2d-dev \
	imx-gpu-viv-tools \
	imx-gpu-viv-tools-apitrace \
	imx-gpu-viv-demos \
"

python __anonymous () {
        has_vivante_kernel_driver_support = (d.getVar('MACHINE_HAS_VIVANTE_KERNEL_DRIVER_SUPPORT', True) or '0')
        if has_vivante_kernel_driver_support != '1':
                raise bb.parse.SkipPackage('The kernel of machine needs to have Vivante kernel driver support for this recipe to be used.')
}

HAS_X11 = "${@bb.utils.contains("DISTRO_FEATURES", "x11", "yes", "no", d)}"
HAS_WL = "${@bb.utils.contains("DISTRO_FEATURES", "wayland", "yes", "no", d)}"

# Inhibit warnings about files being stripped.
INHIBIT_PACKAGE_STRIP = "1"
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

IMXGPU_imxgpu3d = "3d"
IMXGPU_imxgpu2d = "2d"

do_install () {
    install -d ${D}${libdir}
    install -d ${D}${includedir}
    install -d ${D}${bindir}

    cp -P ${S}/gpu-core/usr/lib/*.so* ${D}${libdir}
    cp  ${S}/g2d/usr/lib/*.so* ${D}${libdir}
    cp -r ${S}/gpu-core/usr/include/* ${D}${includedir}
    cp -Pr ${S}/g2d/usr/include/* ${D}${includedir}
    cp -r ${S}/gpu-demos/opt ${D}
    cp -r ${S}/gpu-tools/gmem-info/usr/bin/* ${D}${bindir}

    install -d ${D}${libdir}/pkgconfig

    # The preference order, based in DISTRO_FEATURES, is Wayland (with or without X11), X11 and fb
    if [ "${HAS_WL}" = "yes" ]; then
        cp -r ${S}/apitrace/non-x11/usr/lib/* ${D}${libdir}
        cp -r ${S}/apitrace/non-x11/usr/bin/* ${D}${bindir}

        backend=wl

        install -m 0644 ${S}/gpu-core/usr/lib/pkgconfig/egl_wayland.pc ${D}${libdir}/pkgconfig/egl.pc
        install -m 0644 ${S}/gpu-core/usr/lib/pkgconfig/glesv1_cm.pc ${D}${libdir}/pkgconfig/glesv1_cm.pc
        install -m 0644 ${S}/gpu-core/usr/lib/pkgconfig/glesv2.pc ${D}${libdir}/pkgconfig/glesv2.pc
        install -m 0644 ${S}/gpu-core/usr/lib/pkgconfig/vg.pc ${D}${libdir}/pkgconfig/vg.pc
        install -m 0644 ${S}/gpu-core/usr/lib/pkgconfig/gc_wayland_protocol.pc ${D}${libdir}/pkgconfig/gc_wayland_protocol.pc
        install -m 0644 ${S}/gpu-core/usr/lib/pkgconfig/wayland-egl.pc ${D}${libdir}/pkgconfig/wayland-egl.pc
        install -m 0644 ${S}/gpu-core/usr/lib/pkgconfig/wayland-viv.pc ${D}${libdir}/pkgconfig/wayland-viv.pc

        if [ "${HAS_X11}" = "yes" ]; then
            cp -r ${S}/gpu-core/usr/lib/dri ${D}${libdir}
        fi

    elif [ "${HAS_X11}" = "yes" ]; then

        cp -r ${S}/gpu-core/usr/lib/dri ${D}${libdir}
        cp -r ${S}/apitrace/x11/usr/lib/* ${D}${libdir}
        cp -r ${S}/apitrace/x11/usr/bin/* ${D}${bindir}

        backend=x11

        install -m 0644 ${S}/gpu-core/usr/lib/pkgconfig/egl_x11.pc ${D}${libdir}/pkgconfig/egl.pc
        install -m 0644 ${S}/gpu-core/usr/lib/pkgconfig/glesv1_cm_x11.pc ${D}${libdir}/pkgconfig/glesv1_cm.pc
        install -m 0644 ${S}/gpu-core/usr/lib/pkgconfig/glesv2_x11.pc ${D}${libdir}/pkgconfig/glesv2.pc
        install -m 0644 ${S}/gpu-core/usr/lib/pkgconfig/vg_x11.pc ${D}${libdir}/pkgconfig/vg.pc
    else
        install -m 0644 ${S}/gpu-core/usr/lib/pkgconfig/glesv1_cm.pc ${D}${libdir}/pkgconfig/glesv1_cm.pc
        install -m 0644 ${S}/gpu-core/usr/lib/pkgconfig/glesv2.pc ${D}${libdir}/pkgconfig/glesv2.pc
        install -m 0644 ${S}/gpu-core/usr/lib/pkgconfig/vg.pc ${D}${libdir}/pkgconfig/vg.pc

        # Regular framebuffer
        install -m 0644 ${S}/gpu-core/usr/lib/pkgconfig/egl_linuxfb.pc ${D}${libdir}/pkgconfig/egl.pc
        cp -r ${S}/apitrace/non-x11/usr/lib/* ${D}${libdir}
        cp -r ${S}/apitrace/non-x11/usr/bin/* ${D}${bindir}
        backend=fb
    fi

    # Install Vendor ICDs for OpenCL's installable client driver loader (ICDs Loader)
    install -d ${D}${sysconfdir}/OpenCL/vendors/
    install -m 0644 ${S}/gpu-core/etc/Vivante.icd ${D}${sysconfdir}/OpenCL/vendors/Vivante.icd

    # We'll only have one backend here so we rename it to generic name
    # and avoid rework in other packages, when possible
    mv ${D}${libdir}/libGL.so.1.2 ${D}${libdir}/libGL.so.1.2.0
    ln -sf libGL.so.1.2.0 ${D}${libdir}/libGL.so.1.2
    ln -sf libGL.so.1.2.0 ${D}${libdir}/libGL.so.1
    ln -sf libGL.so.1.2.0 ${D}${libdir}/libGL.so
    mv ${D}${libdir}/libEGL-${backend}.so ${D}${libdir}/libEGL.so.1.0
    ln -sf libEGL.so.1.0 ${D}${libdir}/libEGL.so.1
    ln -sf libEGL.so.1.0 ${D}${libdir}/libEGL.so
    mv ${D}${libdir}/libGAL-${backend}.so ${D}${libdir}/libGAL.so
    mv ${D}${libdir}/libVIVANTE-${backend}.so ${D}${libdir}/libVIVANTE.so

    # update libglesv2 as backend dependent
    rm -rf ${D}${libdir}/libGLESv2*
    cp ${S}/gpu-core/usr/lib/libGLESv2-${backend}.so ${D}${libdir}/libGLESv2.so.2.0.0
    ln -sf libGLESv2.so.2.0.0 ${D}${libdir}/libGLESv2.so.2
    ln -sf libGLESv2.so.2.0.0 ${D}${libdir}/libGLESv2.so

    # skip packaging wayland libraries if no support is requested
    if [ "${HAS_WL}" = "no" ]; then
        rm ${D}${libdir}/libgc_wayland_protocol.*
        rm ${D}${libdir}/libwayland-viv.*
    fi

    for backend in wl x11 fb dri; do
        find ${D}${libdir} -name "*-$backend.so" -exec rm '{}' ';'
        find ${D}${libdir} -name "*.$backend.so" -exec rm '{}' ';'
    done

    # FIXME: For 2D only GPU, we need to remove some content which is
    #        3D specific.
    if [ "${IMXGPU}" = "2d" ]; then
        rm -rf ${D}${libdir}/libCLC* ${D}${includedir}/CL \
               \
               ${D}${libdir}/libGL* ${D}${includedir}/GL* \
               \
               ${D}${libdir}/libGLES* ${D}${libdir}/pkgconfig/glesv1_cm.pc ${D}${libdir}/pkgconfig/glesv2.pc \
               \
               ${D}${libdir}/libOpenCL* ${D}${includedir}/CL \
               \
               ${D}${libdir}/libOpenVG.so ${D}${libdir}/libOpenVG.3d.so \
               \
               ${D}${libdir}/libVivanteOpenCL.so \
               \
               ${D}/opt/viv_samples/vdk \
               ${D}/opt/viv_samples/es20 ${D}/opt/viv_samples/cl11

        ln -sf libOpenVG.2d.so ${D}${libdir}/libOpenVG.so
    fi

    find ${D}${libdir} -type f -exec chmod 644 {} \;
    find ${D}${includedir} -type f -exec chmod 644 {} \;

    chown -R root:root "${D}"
}

ALLOW_EMPTY_${PN} = "1"

FILES_libclc-mx6 = "${libdir}/libCLC${SOLIBS}"
FILES_libclc-mx6-dev = "${includedir}/CL ${libdir}/libCLC${SOLIBSDEV}"

# libEGL.so is used by some demo apps from Freescale
INSANE_SKIP_libegl-mx6 += "dev-so"
FILES_libegl-mx6 = "${libdir}/libEGL${REALSOLIBS} ${libdir}/libEGL${SOLIBSDEV} "
FILES_libegl-mx6-dev = "${includedir}/EGL ${includedir}/KHR ${libdir}/pkgconfig/egl.pc"

FILES_libgal-mx6 = "${libdir}/libGAL${SOLIBS} ${libdir}/libGAL_egl${SOLIBS}"
FILES_libgal-mx6-dev = "${libdir}/libGAL${SOLIBSDEV} ${includedir}/HAL"
RDEPENDS_libgal-mx6 += "kernel-module-imx-gpu-viv"
INSANE_SKIP_libgal-mx6 += "build-deps"

FILES_libvsc-mx6 = "${libdir}/libVSC${SOLIBS}"

FILES_libgl-mx6 = "${libdir}/libGL${REALSOLIBS}"
FILES_libgl-mx6-dev = "${libdir}/libGL${SOLIBSDEV}"
# Includes GL headers from mesa
RDEPENDS_libgl-mx6-dev += "libgl-mesa-dev"

# libEGL needs to open libGLESv1.so
INSANE_SKIP_libgles-mx6 += "dev-so"
FILES_libgles-mx6 = "${libdir}/libGLESv1*${REALSOLIBS} ${libdir}/libGLESv1*${SOLIBS} ${libdir}/libGLES_*${REALSOLIBS} ${libdir}/libGLES_*${SOLIBS}"
FILES_libgles-mx6-dev = "${includedir}/GLES ${libdir}/libGLESv1*${SOLIBS} ${libdir}/libGLES_*${SOLIBSDEV} ${libdir}/pkgconfig/glesv1_cm.pc"

# libEGL needs to open libGLESv2.so
INSANE_SKIP_libgles2-mx6 += "dev-so"
FILES_libgles2-mx6 = "${libdir}/libGLESv2${REALSOLIBS} ${libdir}/libGLESv2${SOLIBS}"
FILES_libgles2-mx6-dev = "${includedir}/GLES2 ${libdir}/libGLESv2${SOLIBSDEV} ${libdir}/pkgconfig/glesv2.pc"
RDEPENDS_libgles2-mx6 = "libglslc-mx6"

FILES_libgles3-mx6-dev = "${includedir}/GLES3"
# as long as there is no libgles3-mx6: ship libgles3-mx6-dev along with
# libgles2-mx6-dev - otherwise GLES3 headers have to be added manually
RDEPENDS_libgles2-mx6-dev += "libgles3-mx6-dev"

FILES_libglslc-mx6 = "${libdir}/libGLSLC${SOLIBS}"
FILES_libglslc-mx6-dev = "${includedir}/CL ${libdir}/libGLSLC${SOLIBSDEV}"

FILES_libopencl-mx6 = "${libdir}/libOpenCL${SOLIBS} \
                       ${libdir}/libVivanteOpenCL${SOLIBS} \
                       ${sysconfdir}/OpenCL/vendors/Vivante.icd"
FILES_libopencl-mx6-dev = "${includedir}/CL ${libdir}/libOpenCL${SOLIBSDEV}"
RDEPENDS_libopencl-mx6 = "libclc-mx6"

INSANE_SKIP_libopenvg-mx6 += "dev-so"
FILES_libopenvg-mx6 = "${libdir}/libOpenVG*${SOLIBS}"
FILES_libopenvg-mx6-dev = "${includedir}/VG ${libdir}/libOpenVG*${SOLIBSDEV} ${libdir}/pkgconfig/vg.pc"

FILES_libvdk-mx6 = "${libdir}/libVDK${SOLIBS}"
FILES_libvdk-mx6-dev = "${includedir}/*vdk*.h ${libdir}/libVDK${SOLIBSDEV}"

FILES_libvivante-mx6 = "${libdir}/libVIVANTE${SOLIBS}"
FILES_libvivante-mx6-dev = "${libdir}/libVIVANTE${SOLIBSDEV}"

FILES_libvivante-dri-mx6 = "${libdir}/dri/vivante_dri.so"
RDEPENDS_libvivante-dri-mx6 = "libdrm"

INSANE_SKIP_libwayland-viv-mx6 += "dev-so"
FILES_libwayland-viv-mx6 =  "${libdir}/libwayland-viv${REALSOLIBS}  ${libdir}/libwayland-viv${SOLIBS}"
FILES_libwayland-viv-mx6-dev = "${includedir}/wayland-viv ${libdir})/libwayland-viv${SOLIBSDEV} ${libdir}/pkgconfig/wayland-viv.pc"
RPROVIDES_libwayland-viv-mx6 += "${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'xf86-video-imxfb-vivante', '', d)}"

INSANE_SKIP_libgc-wayland-protocol-mx6 += "dev-so"
FILES_libgc-wayland-protocol-mx6 = "${libdir}/libgc_wayland_protocol${REALSOLIBS} ${libdir}/libgc_wayland_protocol${SOLIBS}"
FILES_libgc-wayland-protocol-mx6-dev = "${libdir}/libgc_wayland_protocol${SOLIBSDEV} ${libdir}/pkgconfig/gc_wayland_protocol.pc"

FILES_libwayland-egl-mx6-dev = "${libdir}/pkgconfig/wayland-egl.pc"

INSANE_SKIP_imx-gpu-viv-g2d += "dev-so"
FILES_imx-gpu-viv-g2d = "${libdir}/libg2d* "
FILES_imx-gpu-viv-g2d-dev = "${includedir}/g2d.h"

FILES_imx-gpu-viv-tools = "${bindir}/gmem_info"

FILES_imx-gpu-viv-tools-apitrace = "${bindir} ${libdir}"

FILES_imx-gpu-viv-demos = "/opt"
INSANE_SKIP_imx-gpu-viv-demos += "rpaths"

COMPATIBLE_MACHINE = "(mx6q|mx6dl|mx6sx|mx6sl)"
