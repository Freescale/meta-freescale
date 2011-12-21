# Copyright (C) 2011 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Linux kernel for imx platforms"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"
PR = "r2"

inherit kernel
COMPATIBLE_MACHINE = "(imx53qsb|imx53ard)"

REL="11.09.01"
SRC_URI = "http://www.kernel.org/pub/linux/kernel/v2.6/linux-${PV}.tar.bz2;name=source \
           file://linux-${PV}-imx_${REL}.bz2;name=patchs \
           file://egalax_ts-enable-single-event-support.patch \
           file://defconfig \
          "
SRC_URI[source.md5sum] = "5ebff49fd65a7bad234ce103b0b3ede0"
SRC_URI[source.sha256sum] = "83149a9ab30f8dfe45c63d735018422909cf0dd3f5b5501e42f7349f4044f5f1"
SRC_URI[patchs.md5sum] = "02b43a4ed6e4d0adc6841ee07fee02f1"
SRC_URI[patchs.sha256sum] = "7d3adf8f83e49b57f3de74e6684bc6ffc823de4f17b817535bb9a94a6a44ce5b"

do_apply_dist_patchs () {
# Apply distributed kernel patchs
    cd ${S}
    tar xf ${WORKDIR}/linux-${PV}-imx_${REL}
    ./patches/patch-kernel.sh
}

addtask apply_dist_patchs after do_unpack before do_patch

S = "${WORKDIR}/linux-${PV}"
