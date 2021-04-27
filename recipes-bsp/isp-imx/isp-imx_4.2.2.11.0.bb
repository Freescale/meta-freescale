# Copyright 2020-2021 NXP

DESCRIPTION = "i.MX Verisilicon Software ISP"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=3c3fe2b904fd694f28d2f646ee16dddb"

inherit fsl-eula-unpack cmake systemd

SRC_URI = "${FSL_MIRROR}/${BPN}-${PV}.bin;fsl-eula=true \
	   file://0001-start_isp.sh-fix-test-to-be-generic.patch \
"

SRC_URI[md5sum] = "75f79ba556c47172b9a0cbc3a877e604"
SRC_URI[sha256sum] = "28e2ee909f29a256c4eac87ef8336932d90a88a2a183389ac0868212954af42c"

DEPENDS = "python3 libdrm"

# Build the sub-folder appshell
OECMAKE_SOURCEPATH = "${S}/appshell"

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

COMPATIBLE_MACHINE = "(imx|use-nxp-bsp)"
