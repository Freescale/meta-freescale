# Copyright (C) 2020 Jens Rehsack <sno@netbsd.org>
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "FSL Community package group - networking"
DESCRIPTION = "Package group used by FSL Community to add the packages which provide (QorIQ) networking support."
SECTION = "console/network"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

NETWORK_TOOLS = "\
    ethtool \
"

NETWORK_TOOLS:append:qoriq = " \
    ceetm \
    ${@bb.utils.contains('BBFILE_COLLECTIONS', 'openembedded-layer', 'tsntool', '', d)} \
"

# DPDK and its users only support the 64-bit Arm QorIQ SoCs.
NETWORK_TOOLS:append:qoriq-arm64 = " \
    dpdk \
    ovs-dpdk \
    pktgen-dpdk \
"

# Data Place Acceleration Architecture
NETWORK_TOOLS:append:fsl-lsch2 = " \
    eth-config \
"

# 2nd generation Data Place Acceleration Architecture
# ofp is in dynamic-layers/openembedded-layer.
NETWORK_TOOLS:append:ls1088a = " \
    aiopsl \
    gpp-aioptool \
    ${@bb.utils.contains('BBFILE_COLLECTIONS', 'openembedded-layer', 'ofp', '', d)} \
"

NETWORK_TOOLS:append:ls2088a = " \
    aiopsl \
    gpp-aioptool \
    ${@bb.utils.contains('BBFILE_COLLECTIONS', 'openembedded-layer', 'ofp', '', d)} \
"

NETWORK_TOOLS:append:fsl-lsch3 = " \
    dce \
    restool \
    spc \
"

RDEPENDS:${PN} = "\
    ${NETWORK_TOOLS} \
"
