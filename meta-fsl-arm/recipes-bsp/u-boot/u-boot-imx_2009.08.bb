# Copyright (C) 2011 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "bootloader for imx platforms"
require recipes-bsp/u-boot/u-boot.inc

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=4c6cde5df68eff615d36789dc18edd3b"
#DEPENDS = "elftosb-native"
PR = "r1"

REL = "11.09.01"
SRC_URI = "ftp://ftp.denx.de/pub/u-boot/u-boot-${PV}.tar.bz2;name=source \
           file://u-boot-v${PV}-imx_${REL}.tar.bz2;name=patchs"
SRC_URI[source.md5sum] = "cd4788ea1c6ac4f9b100b888a1063a6b"
SRC_URI[source.sha256sum] = "858fd04efd5b98e99fd1a074998b1a8ac5fbd07b176de1d20d8eb148492d949d"
SRC_URI[patchs.md5sum] = "e74b1a4727eb700ada1c1705176614b5"
SRC_URI[patchs.sha256sum] = "916e8d9c0e2a2b7d9c7384803b0cd4060071251772a83a696d929fc76f19a960"

do_apply_dist_patchs () {
    # Apply distributed patchs
    cd ${S}
    ln -s ../patches
    ./patches/patch-uboot.sh
}

addtask apply_dist_patchs after do_unpack before do_patch

S = "${WORKDIR}/u-boot-${PV}"
