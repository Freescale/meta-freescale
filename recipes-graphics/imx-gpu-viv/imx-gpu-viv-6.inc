# Copyright (C) 2012-2016 Freescale Semiconductor
# Copyright (C) 2012-2018 O.S. Systems Software LTDA.
# Copyright 2017-2021 NXP
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "GPU driver and apps for i.MX"
SECTION = "libs"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=5a0bf11f745e68024f37b4724a5364fe"

DEPENDS += " \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'wayland', \
       bb.utils.contains('DISTRO_FEATURES',     'x11', 'virtual/libx11 libxdamage libxext libxfixes', \
                                                       '', d), d)} \
"
DEPENDS += " \
    ${@bb.utils.contains('DISTRO_FEATURES',     'x11', 'libxcb libxshmfence libxxf86vm', \
                                                       '', d)} \
"
DEPENDS:append:imxdrm = " libdrm wayland"
DEPENDS:append:mx8-nxp-bsp = " patchelf-native"

# imx-gpu-viv does not provide everything it needs to for virtual/libgl
# on x11 backend or on Wayland backend with XWayland support.
# We depend on mesa to fill in what is missing.
DEPENDS += "${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'mesa', '', d)}"

DEPENDS:append:libc-musl = " gcompat"

PROVIDES += " \
    imx-gpu-viv \
    libgal-imx \
    opencl-headers \
    opencl-clhpp \
    opencl-icd-loader \
    virtual/egl \
    virtual/libopenvg \
    virtual/opencl-icd \
    ${EXTRA_PROVIDES} \
"
EXTRA_PROVIDES = " \
    ${PROVIDES_OPENGLES3} \
    ${PROVIDES_OPENVX} \
"
EXTRA_PROVIDES:append:imxgpu3d = " \
    virtual/libgl \
    virtual/libgles1 \
    virtual/libgles2 \
"
EXTRA_PROVIDES:append:mx8-nxp-bsp = " \
    virtual/libgbm \
"

PROVIDES_OPENGLES3               = ""
PROVIDES_OPENGLES3:mx8-nxp-bsp   = "virtual/libgles3"
PROVIDES_OPENGLES3:mx8mm-nxp-bsp = ""

# Note: OpenVX is fully supported on i.MX 8 QuadMax and 8 QuadPlus.
# However, only limited support is provided on other i.MX 8 machines
# as needed for i.MX machine learning packages.
PROVIDES_OPENVX                  = ""
PROVIDES_OPENVX:mx8-nxp-bsp      = "virtual/libopenvx"
PROVIDES_OPENVX:mx8mm-nxp-bsp    = ""

RPROVIDES:${PN}:imxgpu3d += "imx-gpu-viv"

PE = "1"

inherit fsl-eula-unpack

SRC_URI = "${FSL_MIRROR}/${BPN}-${PV}.bin;fsl-eula=true \
           file://imx_icd.json"

PACKAGECONFIG ?= ""

# Enables valgrind annotations for libgal-imx
PACKAGECONFIG[valgrind] = ""

# Note : If you add a package here, to prevent a naming conflict see the python_anonymous() futher below
PACKAGES =+ " \
	libgl-imx libgl-imx-dev \
	libgles1-imx libgles1-imx-dev \
	libgles2-imx libgles2-imx-dev \
	libgles3-imx-dev \
	libglslc-imx libglslc-imx-dev \
	${PACKAGES_OPENCL} \
	libopenvg-imx libopenvg-imx-dev \
	libvdk-imx libvdk-imx-dev \
	libegl-imx libegl-imx-dev \
	libgal-imx libgal-imx-dev \
	libvsc-imx \
	${PACKAGES_GBM} \
	imx-gpu-viv-tools \
	imx-gpu-viv-demos \
	${PACKAGES_VULKAN} \
	${PACKAGES_OPENVX} \
	libnn-imx \
"

PACKAGES_GBM                  = ""
PACKAGES_GBM:mx8-nxp-bsp      = "libgbm-imx libgbm-imx-dev"

PACKAGES_OPENCL               = "libopencl-imx libopencl-imx-dev libclc-imx libclc-imx-dev"
PACKAGES_OPENCL:mx7-nxp-bsp   = ""

PACKAGES_OPENVX = \
    "${@bb.utils.contains("PROVIDES_OPENVX", "virtual/libopenvx", \
        "libopenvx-imx libopenvx-imx-dev", "", d)}"

PACKAGES_VULKAN               = ""
PACKAGES_VULKAN:imxvulkan     = "libvulkan-imx libvulkan-imx-dev"

python __anonymous () {
        has_vivante_kernel_driver_support = (d.getVar('MACHINE_HAS_VIVANTE_KERNEL_DRIVER_SUPPORT') or '0')
        if has_vivante_kernel_driver_support != '1':
                raise bb.parse.SkipPackage('The kernel of machine needs to have Vivante kernel driver support for this recipe to be used.')
}

BACKEND = "${@bb.utils.contains("DISTRO_FEATURES", "wayland", "wayland", "fb", d)}"

# The packaged binaries have been stripped of debug info, so disable
# operations accordingly.
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_SYSROOT_STRIP = "1"

# FIXME: The provided binary doesn't provide soname. If in future BSP
# release the libraries are fixed, we can drop this hack.
REALSOLIBS := "${SOLIBS}"
SOLIBS = "${SOLIBSDEV}"

python __anonymous() {
    # FIXME: All binaries lack GNU_HASH in elf binary but as we don't have
    # the source we cannot fix it. Disable the insane check for now.
    packages = d.getVar('PACKAGES').split()
    for p in packages:
        d.appendVar("INSANE_SKIP:%s" % p, " ldflags")

    # For the packages that make up the OpenGL interfaces, inject variables so that
    # they don't get Debian-renamed (which would remove the -imx suffix).
    for p in (("libegl", "libegl1"), ("libgl", "libgl1"),
              ("libgles1", "libglesv1-cm1"), ("libgles2", "libglesv2-2"),
              ("libgles3",), ("libgbm",)):
        fullp = p[0] + "-imx"
        pkgs = "".join(' %s' % i for i in p)
        d.setVar("DEBIAN_NOAUTONAME:" + fullp, "1")
        d.appendVar("RREPLACES:" + fullp, pkgs)
        d.appendVar("RPROVIDES:" + fullp, pkgs)
        d.appendVar("RCONFLICTS:" + fullp, pkgs)

        # For -dev, the first element is both the Debian and original name
        fullp += "-dev"
        pkgs = p[0] + "-dev"
        d.setVar("DEBIAN_NOAUTONAME:" + fullp, "1")
        d.appendVar("RREPLACES:" + fullp, pkgs)
        d.appendVar("RPROVIDES:" + fullp, pkgs)
        d.appendVar("RCONFLICTS:" + fullp, pkgs)
}

IS_MX6SL = "0"
IS_MX6SL:mx6sl-nxp-bsp = "1"

IS_MX8 = "0"
IS_MX8:mx8-nxp-bsp = "1"

PACKAGE_FP_TYPE = "hardfp"

HAS_GBM     = "false"
HAS_GBM:mx8-nxp-bsp = "true"

IMX_SOC        = "IMX_SOC_NOT_SET"
IMX_SOC:mx8qm-nxp-bsp  = "mx8qm"
IMX_SOC:mx8qxp-nxp-bsp = "mx8qxp"
IMX_SOC:mx8dx-nxp-bsp  = "mx8qxp"
IMX_SOC:mx8mp-nxp-bsp  = "mx8mp"
IMX_SOC:mx8mq-nxp-bsp  = "mx8mq"
IMX_SOC:mx8mn-nxp-bsp  = "mx8mn"
IMX_SOC:mx8ulp-nxp-bsp = "mx8ulp"

LIBVULKAN_API_VERSION   = "1.2.182"

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

    # Install SOC-specific drivers
    if [ -d ${S}/gpu-core/usr/lib/${IMX_SOC} ]; then
        cp -r ${S}/gpu-core/usr/lib/${IMX_SOC}/* ${D}${libdir}
    fi

    install -d ${D}${libdir}/pkgconfig
    if ${HAS_GBM}; then
        install -m 0644 ${S}/gpu-core/usr/lib/pkgconfig/gbm.pc ${D}${libdir}/pkgconfig/gbm.pc
    fi

    install -m 0644 ${S}/gpu-core/usr/lib/pkgconfig/glesv1_cm.pc ${D}${libdir}/pkgconfig/glesv1_cm.pc
    install -m 0644 ${S}/gpu-core/usr/lib/pkgconfig/glesv2.pc    ${D}${libdir}/pkgconfig/glesv2.pc
    install -m 0644 ${S}/gpu-core/usr/lib/pkgconfig/vg.pc        ${D}${libdir}/pkgconfig/vg.pc

    # Install backend-specific drivers
    if [ "${BACKEND}" = "wayland" ]; then
        # Wayland backend
        install -m 0644 ${S}/gpu-core/usr/lib/pkgconfig/egl_wayland.pc ${D}${libdir}/pkgconfig/egl.pc
        cp -r ${S}/gpu-core/usr/lib/wayland/* ${D}${libdir}
    elif [ "${IS_MX8}" != "1" ]; then
        # Framebuffer backend for i.MX 6 and 7
        install -m 0644 ${S}/gpu-core/usr/lib/pkgconfig/egl_linuxfb.pc ${D}${libdir}/pkgconfig/egl.pc
        cp -r ${S}/gpu-core/usr/lib/fb/* ${D}${libdir}
    else
        # Framebuffer backend for i.MX 8 and beyond
        install -m 0644 ${S}/gpu-core/usr/lib/pkgconfig/egl.pc         ${D}${libdir}/pkgconfig/egl.pc
        cp -r ${S}/gpu-core/usr/lib/wayland/* ${D}${libdir}
        set -f
        for f in ${FILES:libvdk-imx} ${FILES:libvdk-imx-dev}; do
            set +f
            rm -rf ${D}/$f
        done
        rm -rf \
            ${D}/opt/viv_samples/es20 \
            ${D}/opt/viv_samples/tiger \
            ${D}/opt/viv_samples/vdk
    fi
    if [ "${BACKEND}" = "wayland" ] && [ "${IS_MX8}" != "1" ]; then
        # Special case for libVDK on Wayland backend, deliver fb library as well.
        cp ${S}/gpu-core/usr/lib/fb/libVDK.so.1.2.0 ${D}${libdir}/libVDK-fb.so.1.2.0
    fi

    if [ -z "${PACKAGES_OPENCL}" ]; then
        set -f
        for f in ${FILES:libopencl-imx} ${FILES:libopencl-imx-dev} ${FILES:libclc-imx} ${FILES:libclc-imx-dev}; do
            set +f
            rm -rf ${D}$f
        done
        rm -rf ${D}/opt/viv_samples/cl11
    else
        # Install Vendor ICDs for OpenCL's installable client driver loader (ICDs Loader)
        install -d ${D}${sysconfdir}/OpenCL/vendors/
        install -m 0644 ${S}/gpu-core/etc/Vivante.icd ${D}${sysconfdir}/OpenCL/vendors/Vivante.icd
    fi

    if [ -z "${PACKAGES_OPENVX}" ]; then
        set -f
        for f in ${FILES:libopenvx-imx} ${FILES:libopenvx-imx-dev}; do
            set +f
            rm -rf ${D}$f
        done
    fi

    if [ "${IS_MX8}" = "1" ]; then
        if [ -z "${PACKAGES_VULKAN}" ]; then
            set -f
            for f in ${FILES:libvulkan-imx} ${FILES:libvulkan-imx-dev}; do
                set +f
                rm -rf ${D}$f
            done
        else
            install -Dm 0644 ${WORKDIR}/imx_icd.json ${D}${sysconfdir}/vulkan/icd.d/imx_icd.json
            sed -i "s,%libdir%,${libdir}," ${D}${sysconfdir}/vulkan/icd.d/imx_icd.json
            sed -i "s,%api_version%,${LIBVULKAN_API_VERSION}," ${D}${sysconfdir}/vulkan/icd.d/imx_icd.json
        fi
    fi

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

do_install:append:libc-musl() {
    # libs
    for f in `find ${D}${libdir} -name '*.so*' -type f`
    do
        patchelf --add-needed libgcompat.so.0 $f
    done
    # binaries
    for f in `find ${D}/opt ${D}${bindir} -name '*' -type f -executable`
    do
        # prune scripts
        if `patchelf --print-interpreter $f 2>&1 > /dev/null`
        then
            patchelf --set-interpreter ${root_prefix}`patchelf --print-interpreter $f` $f
            patchelf --add-needed libgcompat.so.0 $f
        fi
    done
}

ALLOW_EMPTY:${PN} = "1"

FILES:libclc-imx = "${libdir}/libCLC${SOLIBS} ${includedir}/CL/cl_viv_vx_ext.h"
FILES:libclc-imx-dev = "${includedir}/CL ${libdir}/libCLC${SOLIBSDEV}"

# libEGL.so is used by some demo apps from Freescale
INSANE_SKIP:libegl-imx += "dev-so"
FILES:libegl-imx = "${libdir}/libEGL${REALSOLIBS} ${libdir}/libEGL${SOLIBSDEV} "
FILES:libegl-imx-dev = "${includedir}/EGL ${includedir}/KHR ${libdir}/pkgconfig/egl.pc"

FILES:libgal-imx = "${libdir}/libGAL${SOLIBS} ${libdir}/libGAL_egl${SOLIBS}"
FILES:libgal-imx-dev = "${libdir}/libGAL${SOLIBSDEV} ${includedir}/HAL"
RDEPENDS:libgal-imx += "${@bb.utils.contains('PACKAGECONFIG', 'valgrind', 'valgrind', '', d)}"
RPROVIDES:libgal-imx += "libgal-imx"
RRECOMMENDS:libgal-imx += "kernel-module-imx-gpu-viv"
INSANE_SKIP:libgal-imx += "build-deps"

FILES:libvsc-imx = "${libdir}/libVSC${SOLIBS}"

FILES:libgbm-imx           = "${libdir}/libgbm*${REALSOLIBS} ${libdir}/libgbm${SOLIBSDEV} ${libdir}/libgbm_viv${SOLIBSDEV}"
FILES:libgbm-imx-dev       = "${libdir}/pkgconfig/gbm.pc ${includedir}/gbm.h"
RDEPENDS:libgbm-imx:append = " libdrm"
INSANE_SKIP:libgbm-imx += "dev-so"

FILES:libvulkan-imx = "\
    ${libdir}/libvulkan_VSI${REALSOLIBS} \
    ${libdir}/libSPIRV_viv${SOLIBS} \
    ${sysconfdir}/vulkan"
FILES:libvulkan-imx-dev = "${includedir}/vulkan ${libdir}/libvulkan_VSI${SOLIBSDEV}"

FILES:libopenvx-imx = " \
    ${libdir}/libOpenVX${REALSOLIBS} \
    ${libdir}/libOpenVXC${SOLIBS} \
    ${libdir}/libOpenVXU${SOLIBS} \
    ${libdir}/libOvx*${SOLIBS} \
    ${libdir}/libArchModelSw${SOLIBS} \
"
FILES:libopenvx-imx-dev = "${includedir}/VX ${libdir}/libOpenVX${SOLIBSDEV}"
RDEPENDS:libopenvx-imx = "libnn-imx"

# libGL is only targeting X11 backend, and in case if Wayland-only is used -
# package QA complains on missing RDEPENDS, which are only available for X11.
# Skip "file-rdeps" QA for this package.
FILES:libgl-imx = "${libdir}/libGL${REALSOLIBS}"
FILES:libgl-imx-dev = "${libdir}/libGL${SOLIBSDEV} ${includedir}/GL"
INSANE_SKIP:libgl-imx += "file-rdeps"

# libEGL needs to open libGLESv1.so
INSANE_SKIP:libgles1-imx += "dev-so"
FILES:libgles1-imx = "${libdir}/libGLESv1*${REALSOLIBS} ${libdir}/libGLESv1*${SOLIBS} ${libdir}/libGLES_*${REALSOLIBS} ${libdir}/libGLES_*${SOLIBS}"
FILES:libgles1-imx-dev = "${includedir}/GLES ${libdir}/libGLESv1*${SOLIBS} ${libdir}/libGLES_*${SOLIBSDEV} ${libdir}/pkgconfig/glesv1_cm.pc"
RPROVIDES:libgles1-imx = "libgles-imx"
RPROVIDES:libgles1-imx-dev = "libgles-imx-dev"

# libEGL needs to open libGLESv2.so
INSANE_SKIP:libgles2-imx += "dev-so"
FILES:libgles2-imx = "${libdir}/libGLESv2${REALSOLIBS} ${libdir}/libGLESv2${SOLIBS}"
FILES:libgles2-imx-dev = "${includedir}/GLES2 ${libdir}/libGLESv2${SOLIBSDEV} ${libdir}/pkgconfig/glesv2.pc"
RDEPENDS:libgles2-imx = "libglslc-imx"

FILES:libgles3-imx-dev = "${includedir}/GLES3"
# as long as there is no libgles3: ship libgles3-dev along with
# libgles2-dev - otherwise GLES3 headers have to be added manually
RDEPENDS:libgles2-imx-dev += "libgles3-imx-dev"

FILES:libglslc-imx = "${libdir}/libGLSLC${SOLIBS}"
FILES:libglslc-imx-dev = "${includedir}/CL ${libdir}/libGLSLC${SOLIBSDEV}"

FILES:libopencl-imx = "${libdir}/libOpenCL${REALSOLIBS} \
                       ${libdir}/libVivanteOpenCL${SOLIBS} \
                       ${libdir}/libLLVM_viv${SOLIBS} \
                       ${sysconfdir}/OpenCL/vendors/Vivante.icd"
FILES:libopencl-imx-dev = "${includedir}/CL ${libdir}/libOpenCL${SOLIBSDEV}"
RDEPENDS:libopencl-imx= "libclc-imx"

FILES:libopenvg-imx = "${libdir}/libOpenVG*${REALSOLIBS}"
FILES:libopenvg-imx-dev = "${includedir}/VG ${libdir}/libOpenVG*${SOLIBSDEV} ${libdir}/pkgconfig/vg.pc"

FILES:libvdk-imx = "${libdir}/libVDK*${REALSOLIBS}"
FILES:libvdk-imx-dev = "${includedir}/*vdk*.h ${libdir}/libVDK${SOLIBSDEV}"

FILES:imx-gpu-viv-tools = "${bindir}/gmem_info"

FILES:imx-gpu-viv-demos = "/opt"
INSANE_SKIP:imx-gpu-viv-demos += "rpaths dev-deps"

FILES:libnn-imx = "${libdir}/libNN*${SOLIBS}"
