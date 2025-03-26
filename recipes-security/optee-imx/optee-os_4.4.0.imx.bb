# Copyright 2017-2024 NXP

require optee-os-fslc-imx.inc

SRC_URI += " \
    file://0001-core-Define-section-attributes-for-clang.patch \
    file://0002-optee-enable-clang-support.patch \
"
SRCBRANCH = "lf-6.6.52_2.2.0"
SRCREV = "60beb308810f9561a67fdb435388a64c85eb6dcb"
