SUMMARY = "Linux IPC KERNEL MODULE "
DESCRIPTION = "DSP boot application and ipc test application"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=fa38cd73d71527dc6efb546474f64d10"

require recipes-extended/ipc-ust/ipc.inc

inherit module qoriq_build_64bit_kernel

S = "${WORKDIR}/git"

SOC ?= ""
SOC_b4 = "B4860"
SOC_bsc9131 = "B913x"
SOC_bsc9132 = "B913x"

do_configure[depends] += "virtual/kernel:do_shared_workdir"
do_compile_prepend () {
    cd ${S}/kernel
}

INHIBIT_PACKAGE_STRIP = "1"
