# Copyright (C) 2012-2013 Freescale Semiconductor
# Copyright (C) 2012-2013 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "GPU driver and apps for imx6"
SECTION = "libs"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://usr/include/gc_vdk.h;endline=11;md5=c831981a5cbb2673318b77fb2f07014c"
PROVIDES += "virtual/libgal-x11 virtual/egl virtual/libgles1 virtual/libgles2"

INC_PR = "r5"

inherit fsl-eula-unpack

SRC_URI = "${FSL_MIRROR}/${PN}-${PV}.bin;fsl-eula=true \
           file://0001-change-header-path-to-HAL.patch \
           file://gc_hal_eglplatform-remove-xlib-undefs.patch \
           file://fix-conflicting-TLS-definition.patch \
          "

PACKAGES =+ "libclc-mx6 libclc-mx6-dev libclc-mx6-dbg \
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

    if [ "${USE_X11}" = "yes" ]; then
        cp -r ${S}/usr/lib/dri ${D}${libdir}
        find ${D}${libdir} -name '*-dfb.so' -exec rm '{}' ';'
        find ${D}${libdir} -name '*-fb.so' -exec rm '{}' ';'
    else
        if [ "${USE_DFB}" = "yes" ]; then
            find ${D}${libdir} -name '*-x11.so' -exec rm '{}' ';'
            find ${D}${libdir} -name '*-fb.so' -exec rm '{}' ';'
        else
            # Regular framebuffer
            find ${D}${libdir} -name '*-x11.so' -exec rm '{}' ';'
            find ${D}${libdir} -name '*-dfb.so' -exec rm '{}' ';'
        fi
    fi

    # We'll only have one backend here so we rename it to generic name
    # and avoid rework in other packages, when possible
    rm ${D}${libdir}/libEGL.so ${D}${libdir}/libGAL.so \
       ${D}${libdir}/libVIVANTE.so

    renamed=
    for backend in x11 fb dfb; do
        for f in $(find ${D}${libdir} -name "*-$backend.so"); do
            if [ -n "$renamed" ] && [ "$renamed" != "$backend" ]; then
                bberror "More than one GPU backend is installed ($backend and $renamed)."
                exit 1
            fi

            renamed=$backend
            mv $f $(echo $f | sed "s,-$backend\.so,.so,g")
         done
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

FILES_libegl-mx6 = "${libdir}/libEGL${SOLIBS}"
FILES_libegl-mx6-dev = "${libdir}/libEGL${SOLIBSDEV}"
FILES_libegl-mx6-dbg = "${libdir}/.debug/libEGL${SOLIBS}"

FILES_libgal-mx6 = "${libdir}/libGAL${SOLIBS}"
FILES_libgal-mx6-dev = "${libdir}/libGAL${SOLIBSDEV}"
FILES_libgal-mx6-dbg = "${libdir}/.debug/libGAL${SOLIBS}"

FILES_libgles-mx6 = "${libdir}/libGLESv1*${SOLIBS} ${libdir}/libGLES_*${SOLIBS}"
FILES_libgles-mx6-dev = "${includedir}/GLES ${libdir}/libGLESv1*${SOLIBS} ${libdir}/libGLES_*${SOLIBSDEV}"
FILES_libgles-mx6-dbg = "${libdir}/.debug/libGLESv1*${SOLIBS} ${libdir}/.debug/libGLES_*${SOLIBS}"

FILES_libgles2-mx6 = "${libdir}/libGLESv2${SOLIBS}"
FILES_libgles2-mx6-dev = "${includedir}/GLES2 ${libdir}/libGLESv2${SOLIBSDEV}"
FILES_libgles2-mx6-dbg = "${libdir}/.debug/libGLESv2${SOLIBS}"
RDEPENDS_libgles2-mx6 = "libglslc-mx6"

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

FILES_libvivante-mx6 = "${libdir}/libVIVANTE${SOLIBS}"
FILES_libvivante-mx6-dev = "${libdir}/libVIVANTE${SOLIBSDEV}"
FILES_libvivante-mx6-dbg = "${libdir}/.debug/libVIVANTE${SOLIBS}"

FILES_libvivante-dri-mx6 = "${libdir}/dri/vivante_dri.so"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mx6)"
