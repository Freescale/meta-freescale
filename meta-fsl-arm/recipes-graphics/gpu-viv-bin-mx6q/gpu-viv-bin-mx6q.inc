# Copyright (C) 2012 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "GPU driver and apps for imx6"
SECTION = "libs"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://usr/include/gc_vdk.h;endline=11;md5=c831981a5cbb2673318b77fb2f07014c"
PROVIDES += "virtual/libgal-x11 virtual/egl virtual/libgles1 virtual/libgles2 virtual/dri"

INC_PR = "r5"

inherit fsl-eula-unpack

SRC_URI = "${FSL_MIRROR}/${PN}-${PV}.bin;fsl-eula=true \
           file://0001-change-header-path-to-HAL.patch \
           file://gc_hal_eglplatform-remove-xlib-undefs.patch \
          "

GPU_XLIBS = "libegl-x11-mx6 libegl-x11-mx6-dev libegl-x11-mx6-dbg \
	libgal-x11-mx6 libgal-x11-mx6-dev libgal-x11-mx6-dbg \
	libvivante-x11-mx6 libvivante-x11-mx6-dev libvivante-x11-mx6-dbg \
	libvivante-dri-mx6 libvivante-dri-mx6-dev libvivante-dri-mx6-dbg \
	"

GPU_DFBLIBS = "libegl-dfb-mx6 libegl-dfb-mx6-dev libegl-dfb-mx6-dbg \
	libgal-dfb-mx6 libgal-dfb-mx6-dev libgal-dfb-mx6-dbg \
	libvivante-dfb-mx6 libvivante-dfb-mx6-dev libvivante-dfb-mx6-dbg \
	"

PACKAGES =+ "libclc-mx6 libclc-mx6-dev libclc-mx6-dbg \
	libgles-mx6 libgles-mx6-dev libgles-mx6-dbg \
	libgles2-mx6 libgles2-mx6-dev libgles2-mx6-dbg \
	libgl-mx6 libgl-mx6-dev libgl-mx6-dbg \
	libglslc-mx6 libglslc-mx6-dev libglslc-mx6-dbg \
	libopencl-mx6 libopencl-mx6-dev libopencl-mx6-dbg \
	libopenvg-mx6 libopenvg-mx6-dev libopenvg-mx6-dbg \
	libvdk-mx6 libvdk-mx6-dev libvdk-mx6-dbg \
	libegl-fb-mx6 libegl-fb-mx6-dev libegl-fb-mx6-dbg \
	libgal-fb-mx6 libgal-fb-mx6-dev libgal-fb-mx6-dbg \
	libvivante-fb-mx6 libvivante-fb-mx6-dev libvivante-fb-mx6-dbg \
	${@base_contains("DISTRO_FEATURES", "x11", "${GPU_XLIBS}", "", d)} \
	${@base_contains("DISTRO_FEATURES", "directfb", "${GPU_DFBLIBS}", "", d)} \
	"

KEEP_XLIBS = "${@base_contains("DISTRO_FEATURES", "x11", "yes", "no", d)}"
KEEP_DFBLIBS = "${@base_contains("DISTRO_FEATURES", "directfb", "yes", "no", d)}"

# Inhibit warnings about files being stripped.
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"

# FIXME: The provided binary doesn't provide soname. If in future BSP
# release the libraries are fixed, we can drop this hack.
REALSOLIBS := "${SOLIBS}"
SOLIBS = "${SOLIBSDEV}"

# FIXME: All binaries lack GNU_HASH in elf binary but as we don't have
# the source we cannot fix it. Disable the insane check for now.
python __anonymous() {
    packages = d.getVar('PACKAGES', True).split()
    for p in packages:
        d.setVar("INSANE_SKIP_%s" % p, "ldflags")
}

do_install () {
    install -d ${D}${libdir}
    install -d ${D}${includedir}

    cp ${S}/usr/lib/*.so ${D}${libdir}
    cp -axr ${S}/usr/include/* ${D}${includedir}
    rm -r ${D}${includedir}/GL
    cp -axr ${S}/opt ${D}

    if [ "${KEEP_XLIBS}" = "no" ]; then
        rm ${D}${libdir}/*-x11.so
    else
        install -d ${D}${libdir}/dri
        cp -ax ${S}/usr/lib/dri/* ${D}${libdir}/dri
    fi
    if [ "${KEEP_DFBLIBS}" = "no" ]; then
        rm ${D}${libdir}/*-dfb.so
    fi

    # FIXME: Drop default library as we need to explicit link to one
    #        of supported backends
    rm ${D}${libdir}/libEGL.so \
       ${D}${libdir}/libGAL.so \
       ${D}${libdir}/libVIVANTE.so

    find ${D}${libdir} -type f -exec chmod 644 {} \;
    find ${D}${includedir} -type f -exec chmod 644 {} \;
}

S = "${WORKDIR}/${PN}-${PV}"

FILES_${PN} += "/opt"
FILES_${PN}-dev = "${includedir}"
FILES_${PN}-dbg = "${libdir}/.debug ${libdir}/dri/.debug \
                   /opt/viv_samples/*/*/.debug"

FILES_libclc-mx6 = "${libdir}/libCLC${SOLIBS}"
FILES_libclc-mx6-dev = "${includedir}/CL ${libdir}/libCLC${SOLIBSDEV}"
FILES_libclc-mx6-dbg = "${libdir}/.debug/libCLC${SOLIBS}"

FILES_libegl-fb-mx6 = "${libdir}/libEGL-fb${SOLIBS}"
FILES_libegl-fb-mx6-dev = "${libdir}/libEGL-fb${SOLIBSDEV}"
FILES_libegl-fb-mx6-dbg = "${libdir}/.debug/libEGL-fb${SOLIBS}"

FILES_libegl-x11-mx6 = "${libdir}/libEGL-x11${SOLIBS}"
FILES_libegl-x11-mx6-dev = "${libdir}/libEGL-x11${SOLIBSDEV}"
FILES_libegl-x11-mx6-dbg = "${libdir}/.debug/libEGL-x11${SOLIBS}"

FILES_libegl-dfb-mx6 = "${libdir}/libEGL-dfb${SOLIBS}"
FILES_libegl-dfb-mx6-dev = "${libdir}/libEGL-dfb${SOLIBSDEV}"
FILES_libegl-dfb-mx6-dbg = "${libdir}/.debug/libEGL-dfb${SOLIBS}"

FILES_libgal-fb-mx6 = "${libdir}/libGAL-fb${SOLIBS}"
FILES_libgal-fb-mx6-dev = "${libdir}/libGAL-fb${SOLIBSDEV}"
FILES_libgal-fb-mx6-dbg = "${libdir}/.debug/libGAL-fb${SOLIBS}"

FILES_libgal-x11-mx6 = "${libdir}/libGAL-x11${SOLIBS}"
FILES_libgal-x11-mx6-dev = "${libdir}/libGAL-x11${SOLIBSDEV}"
FILES_libgal-x11-mx6-dbg = "${libdir}/.debug/libGAL-x11${SOLIBS}"

FILES_libgal-dfb-mx6 = "${libdir}/libGAL-dfb${SOLIBS}"
FILES_libgal-dfb-mx6-dev = "${libdir}/libGAL-dfb${SOLIBSDEV}"
FILES_libgal-dfb-mx6-dbg = "${libdir}/.debug/libGAL-dfb${SOLIBS}"

FILES_libgles-mx6 = "${libdir}/libGLESv1*${SOLIBS} ${libdir}/libGLES_*${SOLIBS}"
FILES_libgles-mx6-dev = "${includedir}/GLES ${libdir}/libGLESv1*${SOLIBS} ${libdir}/libGLES_*${SOLIBSDEV}"
FILES_libgles-mx6-dbg = "${libdir}/.debug/libGLESv1*${SOLIBS} ${libdir}/.debug/libGLES_*${SOLIBS}"

FILES_libgles2-mx6 = "${libdir}/libGLESv2${SOLIBS}"
FILES_libgles2-mx6-dev = "${includedir}/GLES2 ${libdir}/libGLESv2${SOLIBSDEV}"
FILES_libgles2-mx6-dbg = "${libdir}/.debug/libGLESv2${SOLIBS}"
RDEPENDS_libgles2-mx6 = "libglslc-mx6"

FILES_libgl-mx6 = "${libdir}/libGL${REALSOLIBS}"
FILES_libgl-mx6-dev = "${includedir}/GL ${libdir}/libGL${SOLIBSDEV}"
FILES_libgl-mx6-dbg = "${libdir}/.debug/libGL${SOLIBS}"

FILES_libglslc-mx6 = "${libdir}/libGLSLC${SOLIBS}"
FILES_libglslc-mx6-dev = "${includedir}/CL ${libdir}/libGLSLC${SOLIBSDEV}"
FILES_libglslc-mx6-dbg = "${libdir}/.debug/libGLSLC${SOLIBS}"

FILES_libopencl-mx6 = "${libdir}/libOpenCL${SOLIBS}"
FILES_libopencl-mx6-dev = "${includedir}/CL ${libdir}/libOpenCL${SOLIBSDEV}"
FILES_libopencl-mx6-dbg = "${libdir}/.debug/libOpenCL${SOLIBS}"

FILES_libopenvg-mx6 = "${libdir}/libOpenVG*${SOLIBS}"
FILES_libopenvg-mx6-dev = "${includedir}/VG ${libdir}/libOpenVG*${SOLIBSDEV}"
FILES_libopenvg-mx6-dbg = "${libdir}/.debug/libOpenVG*${SOLIBS}"

FILES_libvdk-mx6 = "${libdir}/libVDK${SOLIBS}"
FILES_libvdk-mx6-dev = "${includedir}/*vdk.h ${libdir}/libVDK${SOLIBSDEV}"
FILES_libvdk-mx6-dbg = "${libdir}/.debug/libVDK${SOLIBS}"

FILES_libvivante-fb-mx6 = "${libdir}/libVIVANTE-fb${SOLIBS}"
FILES_libvivante-fb-mx6-dev = "${libdir}/libVIVANTE-fb${SOLIBSDEV}"
FILES_libvivante-fb-mx6-dbg = "${libdir}/.debug/libVIVANTE-fb${SOLIBS}"

FILES_libvivante-x11-mx6 = "${libdir}/libVIVANTE-x11${SOLIBS}"
FILES_libvivante-x11-mx6-dev = "${libdir}/libVIVANTE-x11${SOLIBSDEV}"
FILES_libvivante-x11-mx6-dbg = "${libdir}/.debug/libVIVANTE-x11${SOLIBS}"

FILES_libvivante-dfb-mx6 = "${libdir}/libVIVANTE-dfb${SOLIBS}"
FILES_libvivante-dfb-mx6-dev = "${libdir}/libVIVANTE-dfb${SOLIBSDEV}"
FILES_libvivante-dfb-mx6-dbg = "${libdir}/.debug/libVIVANTE-dfb${SOLIBS}"

FILES_libvivante-dri-mx6 = "${libdir}/dri/vivante_dri${SOLIBS}"
FILES_libvivante-dri-mx6-dev = ""
FILES_libvivante-dri-mx6-dbg = "${libdir}/dri/.debug/vivante_dri${SOLIBS}"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mx6)"
