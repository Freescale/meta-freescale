# Copyright (C) 2015, 2017 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "FSL Community BSP i.MX Linux kernel with backported features and fixes"
DESCRIPTION = "Linux kernel based on NXP 4.9.11-1.0.0 GA release, used by FSL Community BSP in order to \
provide support for i.MX based platforms and include official Linux kernel stable updates, backported \
features and fixes coming from the vendors, kernel community or FSL Community itself."

include linux-fslc.inc

PV .= "+git${SRCPV}"

CVE_VERSION = "${KERNEL_VERSION}"

SRCBRANCH = "4.9-1.0.x-imx"
SRCREV = "953c6e30c9701fda69ef08e2476c541dc4fb1453"
SRC_URI += "file://0001-Backport-minimal-compiler_attributes.h-to-support-GC.patch \
            file://0002-include-linux-module.h-copy-__init-__exit-attrs-to-i.patch \
           "

COMPATIBLE_MACHINE = "(mx6|mx7)"
