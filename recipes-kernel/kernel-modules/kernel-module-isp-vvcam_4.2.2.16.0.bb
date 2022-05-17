# Copyright 2020-2021 NXP

DESCRIPTION = "Kernel loadable module for ISP"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/vvcam/LICENSE;md5=64381a6ea83b48c39fe524c85f65fb44"

SRCBRANCH = "lf-5.10.y_2.2.0"
ISP_KERNEL_SRC ?= "git://source.codeaurora.org/external/imx/isp-vvcam.git;protocol=https;branch=master"

SRC_URI = " \
    ${ISP_KERNEL_SRC};branch=${SRCBRANCH} \
    file://0001-dwe_isr.c-fix-error-found-by-gcc12.patch;patchdir=${WORKDIR}/git \
"
SRCREV = "b2321c0c513322aca8187ebf0328b74fe45a0f01"

S = "${WORKDIR}/git/vvcam/v4l2"

inherit module

COMPATIBLE_MACHINE = "(mx8mp-nxp-bsp)"
