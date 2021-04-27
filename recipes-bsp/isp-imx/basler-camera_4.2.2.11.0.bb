# Copyright 2020-2021 NXP

DESCRIPTION = "Basler camera binary drivers"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=3c3fe2b904fd694f28d2f646ee16dddb"

inherit fsl-eula-unpack

SRC_URI = "${FSL_MIRROR}/${BPN}-${PV}.bin;fsl-eula=true"

SRC_URI[md5sum] = "2e0bd011f8d72e1957c4d6da55364be5"
SRC_URI[sha256sum] = "4be5c4f26dc0f3f5e031c2d8ac295ff5768489e7e978aaa901030fd77b5209c2"

do_install() {
    dest_dir=${D}/opt/imx8-isp/bin
    install -d ${D}/${libdir}
    install -d $dest_dir
    cp -r ${S}/opt/imx8-isp/bin/* $dest_dir
    cp -r ${S}/usr/lib/* ${D}/${libdir}
}

SYSTEMD_AUTO_ENABLE = "enable"

FILES_${PN} = "${libdir} /opt"
INSANE_SKIP_${PN} = "file-rdeps already-stripped"

COMPATIBLE_MACHINE = "(imx|use-nxp-bsp)"
