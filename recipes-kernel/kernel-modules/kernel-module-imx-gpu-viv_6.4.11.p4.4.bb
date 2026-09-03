# Copyright (C) 2015-2016 Freescale Semiconductor
# Copyright (C) 2017-2026 NXP

SUMMARY = "Kernel loadable module for Vivante GPU"
DESCRIPTION = "Builds the Vivante GPU kernel driver as a loadable kernel module, \
               allowing flexibility to use a newer graphics release with an older kernel."
HOMEPAGE = "https://github.com/nxp-imx/linux-imx"
SECTION = "kernel"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://src/hal/kernel/gc_hal_kernel.h;beginline=1;endline=53;md5=3d2a1d218039b0a6f5b4b4c3a9f42931"

SRC_URI = "${LINUX_IMX_SRC};subpath=drivers/mxc/gpu-viv;destsuffix=${BB_GIT_DEFAULT_DESTSUFFIX}/src \
           file://Add-makefile.patch"
LINUX_IMX_SRC ?= "git://github.com/nxp-imx/linux-imx.git;protocol=https;branch=${SRCBRANCH}"
SRCBRANCH = "lf-6.18.y"
LOCALVERSION = "-6.18.2-1.0.0"
SRCREV = "b9ab260bee4d15d079e39a373162eb6201c1cb4a"

inherit module

EXTRA_OEMAKE += "CONFIG_MXC_GPU_VIV=m"

KERNEL_MODULE_AUTOLOAD = "galcore"
COMPATIBLE_MACHINE = "(imx-nxp-bsp)"
