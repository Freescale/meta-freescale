SUMMARY = "Linux IPC KERNEL MODULE "
DESCRIPTION = "DSP boot application and ipc test application"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=fa38cd73d71527dc6efb546474f64d10"

inherit module qoriq_build_64bit_kernel

SRC_URI = "git://github.com/nxp-qoriq-yocto-sdk/ipc;protocol=https;nobranch=1"
SRCREV = "74d662707558290f070f9589177db730444bc435"

S = "${WORKDIR}/git"

do_configure[depends] += "virtual/kernel:do_shared_workdir"
do_compile:prepend () {
    cd ${S}/kernel
}

INHIBIT_PACKAGE_STRIP = "1"
