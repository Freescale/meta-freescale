# Copyright 2020-2021 NXP

DESCRIPTION = "i.MX Verisilicon Software ISP"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=03bcadc8dc0a788f66ca9e2b89f56c6f"
DEPENDS = "python3 libdrm virtual/libg2d libtinyxml2-8"

SRC_URI = " \
    ${FSL_MIRROR}/${BP}.bin;fsl-eula=true \
    file://0001-start_isp.sh-fix-NR_DEVICE_TREE_BASLER-variable.patch \
"

SRC_URI[md5sum] = "f490153dbec234a04416ad41834806b2"
SRC_URI[sha256sum] = "83b24b9d1f7a40a506a45a1c5c2ef2ca2313fe9b23fde591e3b7dce77ea359ca"

inherit fsl-eula-unpack cmake systemd use-imx-headers

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
    -DAPPMODE=V4L2 \
    -DQTLESS=1 \
    -DFULL_SRC_COMPILE=1 \
    -DWITH_DRM=1 \
    -DWITH_DWE=1 \
    -DSERVER_LESS=1 \
    -DSUBDEV_V4L2=1 \
    -DENABLE_IRQ=1 \
    -DPARTITION_BUILD=0 \
    -D3A_SRC_BUILD=0 \
    -DIMX_G2D=ON \
    -Wno-dev \
"

do_configure:prepend() {
    export SDKTARGETSYSROOT=${STAGING_DIR_HOST}
}

do_compile:prepend() {
    ln -sf ${RECIPE_SYSROOT}/${libdir}/libtinyxml2.so.?.?.? ${RECIPE_SYSROOT}/${libdir}/libtinyxml2.so
}

do_install() {
    install -d ${D}/${libdir}
    install -d ${D}/${includedir}
    install -d ${D}/opt/imx8-isp/bin

    cp -r ${B}/generated/release/bin/*_test ${D}/opt/imx8-isp/bin
    cp -r ${B}/generated/release/bin/*2775* ${D}/opt/imx8-isp/bin
    cp -r ${B}/generated/release/bin/isp_media_server ${D}/opt/imx8-isp/bin
    cp -r ${B}/generated/release/bin/vvext ${D}/opt/imx8-isp/bin
    cp -r ${B}/generated/release/lib/*.so* ${D}/${libdir}
    cp -r ${B}/generated/release/include/* ${D}/${includedir}

    cp ${S}/imx/run.sh ${D}/opt/imx8-isp/bin
    cp ${S}/imx/start_isp.sh ${D}/opt/imx8-isp/bin

    chmod +x ${D}/opt/imx8-isp/bin/run.sh
    chmod +x ${D}/opt/imx8-isp/bin/start_isp.sh

    if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
      install -d ${D}${systemd_system_unitdir}
      install -m 0644 ${S}/imx/imx8-isp.service ${D}${systemd_system_unitdir}
    fi
}

# The build contains a mix of versioned and unversioned libraries, so
# the default packaging configuration needs some modifications
FILES_SOLIBSDEV = ""
FILES:${PN} += "/opt ${libdir}/lib*${SOLIBSDEV}"
FILES:${PN}-dev += " \
    ${libdir}/libjsoncpp.so \
    ${libdir}/libos08a20.so \
    ${libdir}/libov2775.so \
"

INSANE_SKIP:${PN} = "rpaths"

RDEPENDS:${PN} = "libdrm libpython3"

COMPATIBLE_MACHINE = "(mx8mp-nxp-bsp)"
