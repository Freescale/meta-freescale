# Copyright (C) 2012-2016 Freescale Semiconductor
# Copyright (C) 2012-2018 O.S. Systems Software LTDA.
# Copyright 2017-2019 NXP
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "GPU driver and apps for i.MX"
SECTION = "libs"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=80c0478f4339af024519b3723023fe28"

DEPENDS += " \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'wayland', \
       bb.utils.contains('DISTRO_FEATURES',     'x11', 'virtual/libx11 libxdamage libxext libxfixes', \
                                                       '', d), d)} \
"
DEPENDS += " \
    ${@bb.utils.contains('DISTRO_FEATURES',     'x11', 'libxcb libxshmfence libxxf86vm', \
                                                       '', d)} \
"
DEPENDS_append_imxdrm = " libdrm wayland"
DEPENDS_append_mx8 = " patchelf-native"

# imx-gpu-viv does not provide everything it needs to for virtual/libgl
# on x11 backend or on Wayland backend with XWayland support.
# We depend on mesa to fill in what is missing.
DEPENDS += "${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'mesa', '', d)}"

EXTRA_PROVIDES = ""
EXTRA_PROVIDES_append_imxgpu3d = " \
    virtual/libgl \
    virtual/libgles1 \
    virtual/libgles2 \
"
EXTRA_PROVIDES_append_mx8 = " \
    virtual/libgbm \
"
EXTRA_PROVIDES_append_mx8qm = " \
    virtual/libopenvx \
"
PROVIDES += " \
    imx-gpu-viv \
    libgal-imx \
    opencl-headers \
    virtual/egl \
    virtual/libopenvg \
    virtual/opencl-headers \
    virtual/opencl-icd \
    ${EXTRA_PROVIDES} \
"

RPROVIDES_${PN}_imxgpu3d += "imx-gpu-viv"

PE = "1"

inherit fsl-eula-unpack distro_features_check

REQUIRED_DISTRO_FEATURES_mx8 = "wayland"

SRC_URI = "${FSL_MIRROR}/${BPN}-${PV}.bin;fsl-eula=true"

# Note : If you add a package here, to prevent a naming conflict see the python_anonymous() futher below
IMX_PACKAGES_GBM     = ""
IMX_PACKAGES_GBM_mx8 = "libgbm-imx libgbm-imx-dev"
PACKAGES =+ "libclc-imx libclc-imx-dev \
	libgl-imx libgl-imx-dev \
	libgles1-imx libgles1-imx-dev \
	libgles2-imx libgles2-imx-dev \
	libgles3-imx-dev \
	libglslc-imx libglslc-imx-dev \
	libopencl-imx libopencl-imx-dev \
	libopenvg-imx libopenvg-imx-dev \
	libvdk-imx libvdk-imx-dev \
	libegl-imx libegl-imx-dev \
	libgal-imx libgal-imx-dev \
	libvivante-dri-imx \
	libvsc-imx \
	${IMX_PACKAGES_GBM} \
	libwayland-viv-imx libwayland-viv-imx-dev \
	libgc-wayland-protocol-imx libgc-wayland-protocol-imx-dev \
	imx-gpu-viv-tools \
	imx-gpu-viv-demos \
	libvulkan-imx libvulkan-imx-dev \
	libopenvx-imx libopenvx-imx-dev \
"
python __anonymous () {
        has_vivante_kernel_driver_support = (d.getVar('MACHINE_HAS_VIVANTE_KERNEL_DRIVER_SUPPORT') or '0')
        if has_vivante_kernel_driver_support != '1':
                raise bb.parse.SkipPackage('The kernel of machine needs to have Vivante kernel driver support for this recipe to be used.')
}

USE_X11 = "${@bb.utils.contains("DISTRO_FEATURES", "x11", "yes", "no", d)}"
USE_WL = "${@bb.utils.contains("DISTRO_FEATURES", "wayland", "yes", "no", d)}"

# Inhibit warnings about files being stripped.
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

# FIXME: The provided binary doesn't provide soname. If in future BSP
# release the libraries are fixed, we can drop this hack.
REALSOLIBS := "${SOLIBS}"
SOLIBS = "${SOLIBSDEV}"

python __anonymous() {
    # FIXME: All binaries lack GNU_HASH in elf binary but as we don't have
    # the source we cannot fix it. Disable the insane check for now.
    packages = d.getVar('PACKAGES').split()
    for p in packages:
        d.appendVar("INSANE_SKIP_%s" % p, " ldflags")

    # For the packages that make up the OpenGL interfaces, inject variables so that
    # they don't get Debian-renamed (which would remove the -imx suffix).
    for p in (("libegl", "libegl1"), ("libgl", "libgl1"),
              ("libgles1", "libglesv1-cm1"), ("libgles2", "libglesv2-2"),
              ("libgles3",) , ("libvulkan",), ("libgbm",)):
        fullp = p[0] + "-imx"
        pkgs = "".join(' %s' % i for i in p)
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

IS_MX6SL = "0"
IS_MX6SL_mx6sl = "1"

IS_MX8 = "0"
IS_MX8_mx8 = "1"

PACKAGE_FP_TYPE = "hardfp"

HAS_GBM     = "false"
HAS_GBM_mx8 = "true"

GLES3_HEADER_REMOVALS        = "gl31.h gl32.h"
GLES3_HEADER_REMOVALS_mx8mq  = "gl32.h"
GLES3_HEADER_REMOVALS_mx8qxp = ""
GLES3_HEADER_REMOVALS_mx8qm  = ""

do_install () {
    install -d ${D}${libdir}
    install -d ${D}${includedir}
    install -d ${D}${bindir}

    cp -P ${S}/gpu-core/usr/lib/*.so* ${D}${libdir}
    cp -r ${S}/gpu-core/usr/include/* ${D}${includedir}
    cp -r ${S}/gpu-demos/opt ${D}
    cp -r ${S}/gpu-tools/gmem-info/usr/bin/* ${D}${bindir}

    # Use vulkan header from vulkan-headers recipe to support vkmark
    rm -rf ${D}${includedir}/vulkan/

    install -d ${D}${libdir}/pkgconfig
    if ${HAS_GBM}; then
        install -m 0644 ${S}/gpu-core/usr/lib/pkgconfig/gbm.pc ${D}${libdir}/pkgconfig/gbm.pc
    fi

    # The preference order, based in DISTRO_FEATURES, is Wayland (with or without X11), X11 and fb
    if [ "${USE_WL}" = "yes" ]; then

        backend=wl

        install -m 0644 ${S}/gpu-core/usr/lib/pkgconfig/egl_wayland.pc ${D}${libdir}/pkgconfig/egl.pc
        install -m 0644 ${S}/gpu-core/usr/lib/pkgconfig/glesv1_cm.pc ${D}${libdir}/pkgconfig/glesv1_cm.pc
        install -m 0644 ${S}/gpu-core/usr/lib/pkgconfig/glesv2.pc ${D}${libdir}/pkgconfig/glesv2.pc
        install -m 0644 ${S}/gpu-core/usr/lib/pkgconfig/vg.pc ${D}${libdir}/pkgconfig/vg.pc

       if [ "${USE_X11}" = "yes" ]; then

        cp -r ${S}/gpu-core/usr/lib/dri ${D}${libdir}

       fi

    elif [ "${USE_X11}" = "yes" ]; then

        cp -r ${S}/gpu-core/usr/lib/dri ${D}${libdir}

        backend=x11

        install -m 0644 ${S}/gpu-core/usr/lib/pkgconfig/gl_x11.pc ${D}${libdir}/pkgconfig/gl.pc
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
    if [ "${USE_WL}" = "yes" ]; then
        # Special case for libVDK on Wayland backend, deliver fb library as well.
        # Need to rename the libraries to avoid the code below that will remove
        # *-fb.so and *-wl.so
        mv ${D}${libdir}/libVDK-fb.so ${D}${libdir}/libVDK-framebuffer.so
        mv ${D}${libdir}/libVDK-wl.so ${D}${libdir}/libVDK-wayland.so
        ln -sf libVDK-wayland.so ${D}${libdir}/libVDK.so
    else
        mv ${D}${libdir}/libVDK-${backend}.so ${D}${libdir}/libVDK.so
    fi

    # update libglesv2 as backend dependent
    rm -rf ${D}${libdir}/libGLESv2*
    cp ${S}/gpu-core/usr/lib/libGLESv2-${backend}.so ${D}${libdir}/libGLESv2.so.2.0.0
    ln -sf libGLESv2.so.2.0.0 ${D}${libdir}/libGLESv2.so.2
    ln -sf libGLESv2.so.2.0.0 ${D}${libdir}/libGLESv2.so

    if [ "${IS_MX8}" = "1" ]; then
        # Change libvulkon.so driver soname, so that it does not clash with the
        # vulkan-loader libvulkan.so
        patchelf --set-soname libvulkan_VSI.so.1 ${D}${libdir}/libvulkan-${backend}.so
        mv ${D}${libdir}/libvulkan-${backend}.so ${D}${libdir}/libvulkan_VSI.so
    fi
    for header in ${GLES3_HEADER_REMOVALS}; do
        rm -f ${D}${includedir}/GLES3/${header}
    done

    # skip packaging wayland libraries if no support is requested
    if [ "${USE_WL}" = "no" ]; then
        find ${D}${libdir} -name "libgc_wayland_protocol.*" -exec rm '{}' ';'
        find ${D}${libdir} -name "libwayland-viv.*" -exec rm '{}' ';'
    fi

    for i in wl x11 fb dri; do
        find ${D}${libdir} -name "*-$i.so" -exec rm '{}' ';'
        find ${D}${libdir} -name "*.$i.so" -exec rm '{}' ';'
    done

    # FIXME: MX6SL does not have 3D support; hack it for now
    if [ "${IS_MX6SL}" = "1" ]; then
        rm -rf ${D}${libdir}/libCLC* ${D}${includedir}/CL \
               \
               ${D}${libdir}/libGL* ${D}${includedir}/GL* ${D}${libdir}/pkgconfig/gl.pc \
               \
               ${D}${libdir}/libGLES* ${D}${libdir}/pkgconfig/gles*.pc \
               \
               ${D}${libdir}/libOpenCL* ${D}${includedir}/CL \
               \
               ${D}${libdir}/libOpenVG.3d.so \
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

# FIXME: Remove the following lines after adding libopenvx package
INSANE_SKIP_imx-gpu-viv-dev += "dev-elf"
INSANE_SKIP_libclc-imx += "dev-deps"

FILES_libclc-imx = "${libdir}/libCLC${SOLIBS}"
FILES_libclc-imx-dev = "${includedir}/CL ${libdir}/libCLC${SOLIBSDEV}"

# libEGL.so is used by some demo apps from Freescale
INSANE_SKIP_libegl-imx += "dev-so"
FILES_libegl-imx = "${libdir}/libEGL${REALSOLIBS} ${libdir}/libEGL${SOLIBSDEV} "
FILES_libegl-imx-dev = "${includedir}/EGL ${includedir}/KHR ${libdir}/pkgconfig/egl.pc"

FILES_libgal-imx = "${libdir}/libGAL${SOLIBS} ${libdir}/libGAL_egl${SOLIBS}"
FILES_libgal-imx-dev = "${libdir}/libGAL${SOLIBSDEV} ${includedir}/HAL"
RDEPENDS_libgal-imx += "kernel-module-imx-gpu-viv"
RPROVIDES_libgal-imx += "libgal-imx"
INSANE_SKIP_libgal-imx += "build-deps"

FILES_libvsc-imx = "${libdir}/libVSC${SOLIBS}"

FILES_libgbm-imx_mx8           = "${libdir}/libgbm${SOLIBS} ${libdir}/gbm_viv${SOLIBS}"
FILES_libgbm-imx-dev_mx8       = "${libdir}/pkgconfig/gbm.pc ${includedir}/gbm.h ${libdir}/libgbm${SOLIBSDEV}"
RDEPENDS_libgbm-imx_append_mx8 = " libdrm"

FILES_libvulkan-imx = "${libdir}/libvulkan_VSI${SOLIBS} ${libdir}/libSPIRV_viv${SOLIBS}"
FILES_libvulkan-imx-dev = "${includedir}/vulkan ${libdir}/libvulkan_VSI${SOLIBSDEV}"

FILES_libopenvx-imx = "${libdir}/libOpenVX${SOLIBS} ${libdir}/libOpenVXC${SOLIBS} ${libdir}/libOpenVXU${SOLIBS}"
FILES_libopenvx-imx-dev = "${includedir}/VX ${libdir}/libopenVX${SOLIBSDEV}"

FILES_libgl-imx = "${libdir}/libGL${REALSOLIBS}"
FILES_libgl-imx-dev = "${libdir}/libGL${SOLIBSDEV} ${includedir}/GL"

# libEGL needs to open libGLESv1.so
INSANE_SKIP_libgles1-imx += "dev-so"
FILES_libgles1-imx = "${libdir}/libGLESv1*${REALSOLIBS} ${libdir}/libGLESv1*${SOLIBS} ${libdir}/libGLES_*${REALSOLIBS} ${libdir}/libGLES_*${SOLIBS}"
FILES_libgles1-imx-dev = "${includedir}/GLES ${libdir}/libGLESv1*${SOLIBS} ${libdir}/libGLES_*${SOLIBSDEV} ${libdir}/pkgconfig/glesv1_cm.pc"
RPROVIDES_libgles1-imx = "libgles-imx"
RPROVIDES_libgles1-imx-dev = "libgles-imx-dev"

# libEGL needs to open libGLESv2.so
INSANE_SKIP_libgles2-imx += "dev-so"
FILES_libgles2-imx = "${libdir}/libGLESv2${REALSOLIBS} ${libdir}/libGLESv2${SOLIBS}"
FILES_libgles2-imx-dev = "${includedir}/GLES2 ${libdir}/libGLESv2${SOLIBSDEV} ${libdir}/pkgconfig/glesv2.pc"
RDEPENDS_libgles2-imx = "libglslc-imx"

FILES_libgles3-imx-dev = "${includedir}/GLES3"
# as long as there is no libgles3: ship libgles3-dev along with
# libgles2-dev - otherwise GLES3 headers have to be added manually
RDEPENDS_libgles2-imx-dev += "libgles3-imx-dev"

FILES_libglslc-imx = "${libdir}/libGLSLC${SOLIBS}"
FILES_libglslc-imx-dev = "${includedir}/CL ${libdir}/libGLSLC${SOLIBSDEV}"

FILES_libopencl-imx = "${libdir}/libOpenCL${SOLIBS} \
                       ${libdir}/libVivanteOpenCL${SOLIBS} \
                       ${libdir}/libLLVM_viv${SOLIBS} \
                       ${sysconfdir}/OpenCL/vendors/Vivante.icd"
FILES_libopencl-imx-dev = "${includedir}/CL ${libdir}/libOpenCL${SOLIBSDEV}"
RDEPENDS_libopencl-imx= "libclc-imx"

INSANE_SKIP_libopenvg-imx += "dev-so"
FILES_libopenvg-imx = "${libdir}/libOpenVG*${SOLIBS}"
FILES_libopenvg-imx-dev = "${includedir}/VG ${libdir}/libOpenVG*${SOLIBSDEV} ${libdir}/pkgconfig/vg.pc"

FILES_libvdk-imx = "${libdir}/libVDK*${SOLIBS}"
FILES_libvdk-imx-dev = "${includedir}/*vdk*.h ${libdir}/libVDK${SOLIBSDEV}"
INSANE_SKIP_libvdk-imx += "dev-so"

FILES_libvivante-dri-imx = "${libdir}/dri/vivante_dri.so"
RDEPENDS_libvivante-dri-imx = "libdrm"

INSANE_SKIP_libwayland-viv-imx += "dev-so"
FILES_libwayland-viv-imx =  "${libdir}/libwayland-viv${REALSOLIBS}  ${libdir}/libwayland-viv${SOLIBS}"
FILES_libwayland-viv-imx-dev = "${includedir}/wayland-viv ${libdir})/libwayland-viv${SOLIBSDEV} ${libdir}/pkgconfig/wayland-viv.pc"
RPROVIDES_libwayland-viv-imx += "${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'xf86-video-imx-vivante', '', d)}"

INSANE_SKIP_libgc-wayland-protocol-imx += "dev-so"
FILES_libgc-wayland-protocol-imx = "${libdir}/libgc_wayland_protocol${REALSOLIBS} ${libdir}/libgc_wayland_protocol${SOLIBS}"
FILES_libgc-wayland-protocol-imx-dev = "${libdir}/libgc_wayland_protocol${SOLIBSDEV} ${libdir}/pkgconfig/gc_wayland_protocol.pc"

FILES_imx-gpu-viv-tools = "${bindir}/gmem_info"

FILES_imx-gpu-viv-demos = "/opt"
INSANE_SKIP_imx-gpu-viv-demos += "rpaths dev-deps"

# COMPATIBLE_MACHINE = "(mx6q|mx6dl|mx6sx|mx6sl|mx8)"
