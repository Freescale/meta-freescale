# Copyright (C) 2020-2024 NXP

DESCRIPTION = "i.MX Verisilicon Software ISP"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=a93b654673e1bc8398ed1f30e0813359"
DEPENDS = "boost libdrm virtual/libg2d libtinyxml2 jsoncpp patchelf-native"

SRC_URI = " \
    ${FSL_MIRROR}/${BP}-${IMX_SRCREV_ABBREV}.bin;fsl-eula=true \
    ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', '${ISP_SYSTEMD_PATCH}', '', d)} \
    file://0002-appshell-cmake-bump-min-version-to-3.5.patch \
    file://0003-appshell-cmake-drop-deprecated-use-of-target_link_li.patch \
    file://0004-units-targets.cmake-fix-check-if-a-target-exists.patch \
    file://0005-units-cmake-fix-use-of-add_dependencies.patch \
"
ISP_SYSTEMD_PATCH = "file://0001-isp-imx-start_isp-don-t-report-error-if-no-camera-is.patch"

IMX_SRCREV_ABBREV = "3cac1fb"
S = "${UNPACKDIR}/${BP}-${IMX_SRCREV_ABBREV}"

SRC_URI[sha256sum] = "8fa5094da6438505287f4dcc8033dad3057ab81bf98c858884f7c3a2e521b252"

inherit fsl-eula-unpack cmake pkgconfig systemd use-imx-headers

PACKAGECONFIG = ""
# Note: building with tuningext fails with boost 1.87.
# (update to 1.87 with walnascar)
PACKAGECONFIG[tuningext] = "-DTUNINGEXT=1,-DTUNINGEXT=0"

# Build the sub-folder appshell
OECMAKE_SOURCEPATH = "${S}/appshell"

# Use make instead of ninja
OECMAKE_GENERATOR = "Unix Makefiles"

# Workaround for linking issues seen with gold linker
LDFLAGS:append = "${@bb.utils.contains('DISTRO_FEATURES', 'ld-is-gold', ' -fuse-ld=bfd ', '', d)}"

SYSTEMD_SERVICE:${PN} = "imx8-isp.service"

EXTRA_OECMAKE += " \
    -DSDKTARGETSYSROOT=${STAGING_DIR_HOST} \
    -DCMAKE_BUILD_TYPE=release \
    -DISP_VERSION=ISP8000NANO_V1802 \
    -DPLATFORM=ARM64 \
    -DQTLESS=1 \
    -DFULL_SRC_COMPILE=1 \
    -DWITH_DRM=1 \
    -DWITH_DWE=1 \
    -DSUBDEV_V4L2=1 \
    -DPARTITION_BUILD=0 \
    -D3A_SRC_BUILD=0 \
    -DIMX_G2D=ON \
    -Wno-dev \
"

do_configure_disable:prepend () {
    # FIXME: should be rebuild.
    patchelf --replace-needed libjsoncpp.so.25 libjsoncpp.so.26 ${S}/mediacontrol/install/bin/isp_media_server
    patchelf --replace-needed libjsoncpp.so.25 libjsoncpp.so.26 ${S}/mediacontrol/install/lib/libmedia_server.so
    patchelf --replace-needed libjsoncpp.so.25 libjsoncpp.so.26 ${S}/tuningext/install/tuningext

    # FIXME: Should be rebuild.
    patchelf --replace-needed libtinyxml2.so.10 libtinyxml2.so.11 ${S}/appshell/shell_libs/ispcore/ARM64/libcam_device.so
}

do_install() {
    # The Makefile unconditionally installs tuningext even if it is not built
    if ${@bb.utils.contains('PACKAGECONFIG','tuningext','false','true',d)}; then
        touch ${B}/generated/release/bin/tuningext
    fi

    oe_runmake -f ${S}/Makefile install INSTALL_DIR=${D} SOURCE_DIR=${S}

    if ${@bb.utils.contains('PACKAGECONFIG','tuningext','false','true',d)}; then
        rm ${D}/opt/imx8-isp/bin/tuningext
    fi

    if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
        install -d ${D}${systemd_system_unitdir}
        install -m 0644 ${S}/imx/imx8-isp.service ${D}${systemd_system_unitdir}
    fi

}

# The build contains a mix of versioned and unversioned libraries, so
# the default packaging configuration needs some modification so that
# unversioned .so libraries go to the main package and versioned .so
# symlinks go to -dev.
FILES_SOLIBSDEV = ""
FILES:${PN} += "/opt ${libdir}/lib*${SOLIBSDEV}"
FILES:${PN}-dev += "${FILES_SOLIBS_VERSIONED}"
FILES_SOLIBS_VERSIONED = " \
    ${libdir}/libcppnetlib-client-connections.so \
    ${libdir}/libcppnetlib-server-parsers.so \
    ${libdir}/libcppnetlib-uri.so \
    ${libdir}/libos08a20.so \
"

RDEPENDS:${PN} = "libdrm"

COMPATIBLE_MACHINE = "(mx8mp-nxp-bsp)"
