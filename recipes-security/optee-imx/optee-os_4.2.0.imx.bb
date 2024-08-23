# Copyright 2017-2024 NXP

require optee-os-fslc-imx.inc

SRC_URI += " \
    file://0001-core-Define-section-attributes-for-clang.patch \
    file://0002-optee-enable-clang-support.patch \
    file://0003-arm32-libutils-libutee-ta-add-.note.GNU-stack-sectio.patch \
    file://0004-core-link-add-no-warn-rwx-segments.patch \
"
SRCBRANCH = "lf-6.6.23_2.0.0"
SRCREV = "c6be5b572452a2808d1a34588fd10e71715e23cf"
