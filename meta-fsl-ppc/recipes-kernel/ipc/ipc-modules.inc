SUMMARY = "Linux IPC KERNEL MODULE "
DESCRIPTION = "DSP boot application and ipc test application"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=fa38cd73d71527dc6efb546474f64d10"

require recipes-bsp/ipc/ipc.inc

inherit  module qoriq_build_64bit_kernel

S = "${WORKDIR}/git"

do_compile_prepend () {
    cd ${S}/kernel
    case ${MACHINE} in
        bsc9132qds|bsc9131rdb) SOC=B913x;;
        b4860qds|b4420qds) SOC=B4860;;
    esac
}

INHIBIT_PACKAGE_STRIP = "1"

