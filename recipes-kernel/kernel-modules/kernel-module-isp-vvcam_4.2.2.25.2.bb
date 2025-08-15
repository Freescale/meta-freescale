# Copyright (C) 2020-2025 NXP

DESCRIPTION = "Kernel loadable module for ISP"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${S}/../LICENSE;md5=64381a6ea83b48c39fe524c85f65fb44"

SRC_URI = "${ISP_KERNEL_SRC};branch=${SRCBRANCH}"
ISP_KERNEL_SRC ?= "git://github.com/nxp-imx/isp-vvcam.git;protocol=https"
SRCBRANCH = "lf-6.12.y_2.0.0"
SRCREV = "dc686fbb8b5b2f4cbc58d83756f65ad970b03ac2"


S = "${UNPACKDIR}/${BP}/vvcam/v4l2"

inherit module

MODULES_MODULE_SYMVERS_LOCATION = "dwe"

DEBUG_PREFIX_MAP:prepend = " \
    -fmacro-prefix-map=${UNPACKDIR}/${BP}/vvcam=/usr/src/debug/${PN}/${EXTENDPE}${PV}-${PR} \
    -fdebug-prefix-map=${UNPACKDIR}/${BP}/vvcam=/usr/src/debug/${PN}/${EXTENDPE}${PV}-${PR} "

COMPATIBLE_MACHINE = "(mx8mp-nxp-bsp)"
