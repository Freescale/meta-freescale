# Copyright (C) 2014 O.S. Systems Software LTDA.

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

RDEPENDS_${PN}-base = " \
    bash \
    imx-uuc \
    util-linux \
    coreutils \
    dosfstools \
    mmc-utils \
    base-files \
    base-passwd \
    busybox \
    ${@base_contains("MACHINE_FEATURES", "keyboard", "${VIRTUAL-RUNTIME_keymaps}", "", d)} \
    ${MACHINE_ESSENTIAL_EXTRA_RDEPENDS} \
"

RDEPENDS_${PN}-mtd = " \
    ${PN}-base \
    imx-kobs \
    mtd-utils \
    mtd-utils-ubifs \
"

RDEPENDS_${PN}-extfs = " \
    ${PN}-base \
    e2fsprogs-mke2fs \
"
