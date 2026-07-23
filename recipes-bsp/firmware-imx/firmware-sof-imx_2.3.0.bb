# Copyright (C) 2020-2022 NXP
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Sound Open Firmware for i.MX"
DESCRIPTION = "Sound Open Firmware (SOF) audio DSP firmware for i.MX SoCs."
HOMEPAGE = "https://www.sofproject.org"
SECTION = "base"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENCE;md5=0f00d99239d922ffd13cabef83b33444"

SRC_URI = "${FSL_MIRROR}/sof-imx-${PV}.tar.gz"
SRC_URI[sha256sum] = "eb86c90aec92b3b376e7afca6aa5db4767b5d8868553595907ba37665329f16b"

S = "${UNPACKDIR}/sof-imx-${PV}"

inherit allarch

do_install() {
    # Install sof and sof-tplg folder
    install -d ${D}${nonarch_base_libdir}/firmware/imx/
    cp -r sof* ${D}${nonarch_base_libdir}/firmware/imx/
}

# Package the installed SOF firmware/topology tree explicitly (filesoverride).
# nooelint: oelint.var.filesoverride
FILES:${PN} = "${nonarch_base_libdir}/firmware/imx"
