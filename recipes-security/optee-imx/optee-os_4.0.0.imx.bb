# Copyright (C) 2017-2021 NXP

require optee-os-fslc-imx.inc

SRC_URI += " \
    file://0001-core-Define-section-attributes-for-clang.patch \
    file://0002-optee-enable-clang-support.patch \
    file://0003-arm32-libutils-libutee-ta-add-.note.GNU-stack-sectio.patch \
    file://0004-core-link-add-no-warn-rwx-segments.patch \
"
SRCBRANCH = "lf-6.1.55_2.2.0"
SRCREV = "a303fc80f7c4bd713315687a1fa1d6ed136e78ee"
