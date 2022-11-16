# Copyright (C) 2014-2018 O.S. Systems Software LTDA.
# Copyright (C) 2014-2016 Freescale Semiconductor

SUMMARY = "Produces a Manufacturing Tool compatible Linux Kernel"
DESCRIPTION = "Linux Kernel provided and supported by the Freescale Community \
that produces a Manufacturing Tool compatible Linux Kernel to be used in updater \
environment"

FILESEXTRAPATHS:prepend := "${THISDIR}/linux-fslc:"
require linux-fslc_${PV}.bb
require linux-mfgtool.inc

KERNEL_IMAGE_BASE_NAME[vardepsexclude] = "DATETIME"
MODULE_IMAGE_BASE_NAME[vardepsexclude] = "DATETIME"
do_package[vardepsexclude] = "DATETIME"
