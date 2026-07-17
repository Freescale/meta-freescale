# Copyright (C) 2014, 2015 O.S. Systems Software LTDA.

SUMMARY = "Freescale Manufacturing Tool requirements"
DESCRIPTION = "Package group providing the base, MTD and ext filesystem tool \
               sets required by the Freescale/i.MX Manufacturing Tool for \
               programming storage on target devices."
SECTION = "console/utils"
LICENSE = "MIT"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

PACKAGES = "\
    ${PN}-base \
    ${PN}-mtd \
    ${PN}-extfs \
"

# The essential packages for device bootup that may be set in the
# machine configuration file. A weak default is provided here so the
# RDEPENDS reference below resolves when no machine sets it.
# nooelint: oelint.vars.outofcontext
MACHINE_ESSENTIAL_EXTRA_RDEPENDS ?= ""

# Distro can override the following VIRTUAL-RUNTIME providers:
VIRTUAL-RUNTIME_keymaps ?= "keymaps"

# Literal package names are ordered alphabetically, with the conditional
# keymaps entry and the machine RDEPENDS expansion grouped last; the linter's
# tokenizer mis-sorts the ${@...}/${...} entries, so the check is suppressed.
# nooelint: oelint.vars.dependsordered
RDEPENDS:${PN}-base = "\
    base-files \
    base-passwd \
    bash \
    busybox \
    coreutils \
    dosfstools \
    imx-uuc \
    mmc-utils \
    util-linux \
    ${@bb.utils.contains("MACHINE_FEATURES", "keyboard", "${VIRTUAL-RUNTIME_keymaps}", "", d)} \
    ${MACHINE_ESSENTIAL_EXTRA_RDEPENDS} \
"

RDEPENDS:${PN}-mtd = "\
    ${PN}-base \
    imx-kobs \
    mtd-utils \
    mtd-utils-ubifs \
"

RDEPENDS:${PN}-extfs = "\
    ${PN}-base \
    e2fsprogs-e2fsck \
    e2fsprogs-mke2fs \
"
COMPATIBLE_MACHINE = "(imx-generic-bsp)"
