# Copyright 2020-2021 NXP

DESCRIPTION = "i.MX Verisilicon Software ISP"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=417b82f17fc02b88125331ed312f6f1b"
DEPENDS = "python3 libdrm virtual/libg2d"

SRC_URI = " \
    ${FSL_MIRROR}/${BP}.bin;fsl-eula=true \
    file://0001-start_isp.sh-fix-test-to-be-generic.patch \
    file://0001-isp-imx-drop-use-of-__TIME__-__DATE__.patch \
"

SRC_URI[md5sum] = "123feed48302fea2a1be8572e913ae47"
SRC_URI[sha256sum] = "e775975684aab4211f0bf8c5e2c6604ba46b83c5586937000675e044a50d77b8"

inherit fsl-eula-unpack cmake systemd use-imx-headers

# Build the sub-folder appshell
OECMAKE_SOURCEPATH = "${S}/appshell"

# Use make instead of ninja
OECMAKE_GENERATOR = "Unix Makefiles"

SYSTEMD_SERVICE:${PN} = "imx8-isp.service"

EXTRA_OECMAKE += " \
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

do_install() {
    install -d ${D}/${libdir}
    install -d ${D}/${includedir}
    install -d ${D}/opt/imx8-isp/bin

    cp -r ${WORKDIR}/build/generated/release/bin/*_test ${D}/opt/imx8-isp/bin
    cp -r ${WORKDIR}/build/generated/release/bin/*2775* ${D}/opt/imx8-isp/bin
    cp -r ${WORKDIR}/build/generated/release/bin/isp_media_server ${D}/opt/imx8-isp/bin
    cp -r ${WORKDIR}/build/generated/release/bin/vvext ${D}/opt/imx8-isp/bin
    cp -r ${WORKDIR}/${BP}/dewarp/dewarp_config/ ${D}/opt/imx8-isp/bin
    cp -r ${WORKDIR}/build/generated/release/lib/*.so* ${D}/${libdir}
    cp -r ${WORKDIR}/build/generated/release/include/* ${D}/${includedir}

    cp ${WORKDIR}/${BP}/imx/run.sh ${D}/opt/imx8-isp/bin
    cp ${WORKDIR}/${BP}/imx/start_isp.sh ${D}/opt/imx8-isp/bin

    chmod +x ${D}/opt/imx8-isp/bin/run.sh
    chmod +x ${D}/opt/imx8-isp/bin/start_isp.sh

    if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
      install -d ${D}${systemd_system_unitdir}
      install -m 0644 ${WORKDIR}/${BP}/imx/imx8-isp.service ${D}${systemd_system_unitdir}
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

COMPATIBLE_MACHINE = "(mx8mp)"
