# Copyright 2020-2023 NXP

DESCRIPTION = "i.MX Verisilicon Software ISP"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=ca53281cc0caa7e320d4945a896fb837"
DEPENDS = "boost libdrm virtual/libg2d libtinyxml2 jsoncpp patchelf-native"

SRC_URI = " \
    ${FSL_MIRROR}/${BP}-${IMX_SRCREV_ABBREV}.bin;fsl-eula=true \
"
IMX_SRCREV_ABBREV = "8527c7b"
S = "${WORKDIR}/${BP}-${IMX_SRCREV_ABBREV}"

SRC_URI[sha256sum] = "481e49e9da6d7783d6c28385bb68463eac7b9e9fef6ea958950260a8ad6b1e4c"

inherit fsl-eula-unpack cmake systemd use-imx-headers

PACKAGECONFIG = "tuningext"
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

do_install() {
    # FIXME: provided by the basler-camera package, do not install them here additionally
    rm -f ${S}/dewarp/dewarp_config//daA3840_30mc*.json

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
