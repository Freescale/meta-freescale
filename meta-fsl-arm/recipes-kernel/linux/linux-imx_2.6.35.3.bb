# Copyright (C) 2011 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Linux kernel for imx platforms"
LICENSE = "GPL"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"
PR = "r0"

inherit kernel
COMPATIBLE_MACHINE = "(imx53qsb|imx53ard)"

REL="11.05.01"
SRC_URI = "http://opensource.freescale.com/pub/scm/imx/linux-${PV}.tar.bz2;name=source \
           http://opensource.freescale.com/pub/scm/imx/linux-${PV}-imx_${REL}.bz2;name=patchs \
           file://egalax_ts-enable-single-event-support.patch \
           file://defconfig \
          "
SRC_URI[source.md5sum] = "38d0e15a2be7f5e58f46a959ec9ec4d3"
SRC_URI[source.sha256sum] = "19983d5daee9053e49303041365be7c55bbaf08941aea07dc8362d44742c26f6"
SRC_URI[patchs.md5sum] = "e6c24894017f3b5977ecfa07e28dc69e"
SRC_URI[patchs.sha256sum] = "20b6f1adb14613c1eda1d7e353e169753ad45c7544e2bc590bf3b9a998d61984"

do_patch () {
# Apply distributed kernel patchs
    cd ${S}
    tar xf ${WORKDIR}/linux-${PV}-imx_${REL}
    ./patches/patch-kernel.sh
}

S = "${WORKDIR}/linux-${PV}"
