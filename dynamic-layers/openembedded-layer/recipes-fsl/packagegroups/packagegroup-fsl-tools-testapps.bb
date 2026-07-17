# Copyright (C) 2012-2014, 2016 Freescale Semiconductor
# Copyright (C) 2015, 2016 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "FSL Community packagegroup - tools/testapps"
DESCRIPTION = "Packagegroup used by FSL Community to provide a set of packages and utilities \
               for hardware test."
SECTION = "console/utils"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

SOC_TOOLS_TEST = ""
SOC_TOOLS_TEST:imx-nxp-bsp = "imx-test"
SOC_TOOLS_TEST:imxgpu = "imx-test imx-gpu-viv-demos"
SOC_TOOLS_TEST:qoriq = "ceetm optee-test-qoriq"

# Entries are ordered alphabetically (conditional ${@...} and ${SOC_TOOLS_TEST}
# first); the linter's tokenizer mis-sorts this mix, so the check is suppressed.
# nooelint: oelint.vars.dependsordered
RDEPENDS:${PN} = "\
    ${@bb.utils.contains('DISTRO_FEATURES', 'sysvinit', 'fsl-rc-local', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'weston-examples', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'gtk+3-demo', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'v4l-utils', '', d)} \
    ${SOC_TOOLS_TEST} \
    alsa-tools \
    alsa-utils \
    dosfstools \
    e2fsprogs-mke2fs \
    ethtool \
    evtest \
    fbset \
    i2c-tools \
    iproute2 \
    memtester \
    mtd-utils \
    mtd-utils-ubifs \
    python3-core \
    python3-datetime \
    python3-json \
"

RDEPENDS_IMX_TO_REMOVE = ""
RDEPENDS_IMX_TO_REMOVE:imxgpu3d = ""

RDEPENDS:${PN}:remove = "${RDEPENDS_IMX_TO_REMOVE}"
