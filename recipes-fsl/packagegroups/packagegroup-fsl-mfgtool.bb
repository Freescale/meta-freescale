# Copyright (C) 2014, 2015 O.S. Systems Software LTDA.

SUMMARY = "Freescale Manufacturing Tool requirements"
LICENSE = "MIT"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

PACKAGES = " \
    ${PN}-base \
    ${PN}-mtd \
    ${PN}-extfs \
"

# The essential packages for device bootup that may be set in the
# machine configuration file.
MACHINE_ESSENTIAL_EXTRA_RDEPENDS ?= ""

# Distro can override the following VIRTUAL-RUNTIME providers:
VIRTUAL-RUNTIME_keymaps ?= "keymaps"

RDEPENDS:${PN}-base = " \
    bash \
    imx-uuc \
    util-linux \
    coreutils \
    dosfstools \
    mmc-utils \
    base-files \
    base-passwd \
    busybox \
    ${@bb.utils.contains("MACHINE_FEATURES", "keyboard", "${VIRTUAL-RUNTIME_keymaps}", "", d)} \
    ${MACHINE_ESSENTIAL_EXTRA_RDEPENDS} \
"

RDEPENDS:${PN}-mtd = " \
    ${PN}-base \
    imx-kobs \
    mtd-utils \
    mtd-utils-ubifs \
"

RDEPENDS:${PN}-extfs = " \
    ${PN}-base \
    e2fsprogs-mke2fs \
    e2fsprogs-e2fsck \
"
COMPATIBLE_MACHINE = "(imx-generic-bsp)"
