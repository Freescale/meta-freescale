# Copyright 2020 NXP

DESCRIPTION = "i.MX Verisilicon Software ISP"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://${WORKDIR}/${PN}-${PV}/COPYING;md5=cf3f9b8d09bc3926b1004ea71f7a248a"


inherit fsl-eula-unpack cmake systemd

SRC_URI = "${FSL_MIRROR}/${BPN}-${PV}.bin;fsl-eula=true \
	   file://0001-BufferManager.h-add-missing-header-file.patch;patchdir=.. \
	   file://0001-imx8-isp.service-do-not-prevent-restart-of-service.patch;patchdir=.. \
	   file://0001-run.sh-fix-bashism.patch;patchdir=.. \
	   file://0001-start_isp.sh-fix-test-to-be-generic.patch;patchdir=.. \
"

SRC_URI[md5sum] = "2246009411af32b1e02fbb0b242a0fc0"
SRC_URI[sha256sum] = "7a245abb5fee23319f59b970db0bb014a2932fd6d5bd44f16f7dd31bbe25003a"

S = "${WORKDIR}/${PN}-${PV}/appshell"

DEPENDS = "python3 libdrm"

OECMAKE_GENERATOR = "Unix Makefiles"

SYSTEMD_SERVICE_${PN} = "imx8-isp.service"

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
    -Wno-dev \
"

do_configure_prepend() {
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
    cp -r ${WORKDIR}/${PN}-${PV}/mediacontrol/case/ ${D}/opt/imx8-isp/bin
    cp -r ${WORKDIR}/build/generated/release/lib/*.so* ${D}/${libdir}
    cp -r ${WORKDIR}/build/generated/release/include/* ${D}/${includedir}

    cp ${WORKDIR}/${PN}-${PV}/imx/run.sh ${D}/opt/imx8-isp/bin
    cp ${WORKDIR}/${PN}-${PV}/imx/start_isp.sh ${D}/opt/imx8-isp/bin

    chmod +x ${D}/opt/imx8-isp/bin/run.sh
    chmod +x ${D}/opt/imx8-isp/bin/start_isp.sh

    if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
      install -d ${D}${systemd_system_unitdir}
      install -m 0644 ${WORKDIR}/${PN}-${PV}/imx/imx8-isp.service ${D}${systemd_system_unitdir}
    fi
}

RDEPENDS_${PN} = "libdrm libpython3"

PACKAGES = "${PN} ${PN}-dev ${PN}-dbg"

FILES_${PN} = "${libdir} /opt ${systemd_system_unitdir}/imx8-isp.service"
FILES_${PN}-dbg += "${libdir}/.debug"

INSANE_SKIP_${PN} += "rpaths dev-deps dev-so"
INSANE_SKIP_${PN}-dev += "rpaths dev-elf"
