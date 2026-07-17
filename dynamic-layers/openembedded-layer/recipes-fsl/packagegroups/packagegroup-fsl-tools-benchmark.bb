# Copyright (C) 2012-2016 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "FSL Community package group - tools/benchmark"
DESCRIPTION = "Package group used by FSL Community to provide a set of benchmark applications."
SECTION = "console/utils"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

RDEPENDS:${PN} = "\
    ${@bb.utils.contains('TUNE_FEATURES', 'neon',             'cpuburn-neon', \
       bb.utils.contains('TUNE_FEATURES', 'cortexa53 crypto', 'cpuburn-neon', \
                                                              '', d), d)} \
    bonnie++ \
    dbench \
    fio \
    iozone3 \
    iperf3 \
    lmbench \
    nbench-byte \
    tiobench \
"
