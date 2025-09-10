# Copyright (C) 2020-2024 NXP

DESCRIPTION = "Basler camera binary drivers"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=a93b654673e1bc8398ed1f30e0813359"

IMX_SRCREV_ABBREV = "dd86758"

inherit fsl-eula-unpack

SRC_URI = "${FSL_MIRROR}/${BPN}-${PV}-${IMX_SRCREV_ABBREV}.bin;fsl-eula=true"

SRC_URI[sha256sum] = "aa86adeb0c53c3306f7e1c004ffa78ebb7db3e9136c78759e4029b4b5e1b1a64"

S = "${WORKDIR}/${BPN}-${PV}-${IMX_SRCREV_ABBREV}"

do_compile[noexec] = "1"

do_install() {
    oe_runmake install INSTALL_DIR=${D}
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
