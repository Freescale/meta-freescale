# Copyright (C) 2011 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "bootloader for imx platforms"
require recipes-bsp/uboot/u-boot.inc

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=4c6cde5df68eff615d36789dc18edd3b"
#DEPENDS = "elftosb-native"
PR = "r0"

REL = "11.05.01"
SRC_URI = "http://opensource.freescale.com/pub/scm/imx/u-boot-${PV}.tar.bz2;name=source \
           http://opensource.freescale.com/pub/scm/imx/u-boot-v${PV}-imx_${REL}.tar.bz2;name=patchs"
SRC_URI[source.md5sum] = "cd4788ea1c6ac4f9b100b888a1063a6b"
SRC_URI[source.sha256sum] = "858fd04efd5b98e99fd1a074998b1a8ac5fbd07b176de1d20d8eb148492d949d"
SRC_URI[patchs.md5sum] = "951b962a64dbf51da2761b8fab8ed2de"
SRC_URI[patchs.sha256sum] = "94772a4ffc0de00740802c8acbae339051c85d5eb878bbe202c6c25d66ed255c"

do_patch() {
    # Apply distributed patchs
    cd ${S}
    ln -s ../patches
    ./patches/patch-uboot.sh
}

S = "${WORKDIR}/u-boot-${PV}"
