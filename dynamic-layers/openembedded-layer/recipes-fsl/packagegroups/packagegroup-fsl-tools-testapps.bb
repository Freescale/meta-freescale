# Copyright (C) 2012-2014, 2016 Freescale Semiconductor
# Copyright (C) 2015, 2016 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Packagegroup used by FSL Community to provide a set of packages and utilities \
for hardware test."
SUMMARY = "FSL Community packagegroup - tools/testapps"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

SOC_TOOLS_TEST = ""
SOC_TOOLS_TEST:imx-nxp-bsp = "imx-test"
SOC_TOOLS_TEST:imxgpu  = "imx-test imx-gpu-viv-demos"
SOC_TOOLS_TEST:qoriq = "ceetm optee-test-qoriq"

RDEPENDS:${PN} = " \
    alsa-utils \
    alsa-tools \
    dosfstools \
    evtest \
    e2fsprogs-mke2fs \
    ${@bb.utils.contains('DISTRO_FEATURES', 'sysvinit', 'fsl-rc-local', '', d)} \
    fbset \
    i2c-tools \
    iproute2 \
    memtester \
    python3-core \
    python3-json \
    python3-datetime \
    ${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'v4l-utils', '', d)} \
    ethtool \
    mtd-utils \
    mtd-utils-ubifs \
    ${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'gtk+3-demo', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', \
                         'weston-examples', '', d)} \
    ${SOC_TOOLS_TEST} \
"

RDEPENDS_IMX_TO_REMOVE = ""
RDEPENDS_IMX_TO_REMOVE:imxgpu3d = ""

RDEPENDS:${PN}:remove = "${RDEPENDS_IMX_TO_REMOVE}"
