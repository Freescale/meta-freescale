# Copyright 2020-2023 NXP

DESCRIPTION = "Basler camera binary drivers"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=63a38e9f392d8813d6f1f4d0d6fbe657"

inherit fsl-eula-unpack

SRC_URI = "${FSL_MIRROR}/${BPN}-${PV}.bin;fsl-eula=true"

SRC_URI[md5sum] = "cb5e5a4d1efd1d845464ac9a8c6383b8"
SRC_URI[sha256sum] = "6fe10f5118d18a7cb1dd40e713343ce788ad3766104ad2471ee49aed7b55833a"

do_install() {
    # provided by the isp-imx package, do not install them here additionally
    rm -f ${S}/opt/imx8-isp/bin/dewarp_config/sensor_dwe_os08a20_1080P_config.json
    rm -f ${S}/opt/imx8-isp/bin/dewarp_config/sensor_dwe_os08a20_4K_config.json    

    dest_dir=${D}/opt/imx8-isp/bin
    install -d ${D}/${libdir}
    install -d $dest_dir
    cp -r ${S}/opt/imx8-isp/bin/* $dest_dir
    cp -r ${S}/usr/lib/* ${D}/${libdir}
}

SYSTEMD_AUTO_ENABLE = "enable"

FILES:${PN} = "${libdir} /opt"
INSANE_SKIP:${PN} = "already-stripped"
RDEPENDS:${PN} += "isp-imx"

COMPATIBLE_MACHINE = "(mx8mp-nxp-bsp)"
