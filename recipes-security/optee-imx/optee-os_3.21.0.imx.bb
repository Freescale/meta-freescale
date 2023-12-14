# Copyright (C) 2017-2021 NXP

require optee-os-fslc-imx.inc

SRC_URI += " \
    file://0001-core-Define-section-attributes-for-clang.patch \
    file://0002-optee-enable-clang-support.patch \
    file://0003-arm32-libutils-libutee-ta-add-.note.GNU-stack-sectio.patch \
    file://0004-core-link-add-no-warn-rwx-segments.patch \
"
SRCBRANCH = "lf-6.1.36_2.1.0"
SRCREV = "4e32281904b15af9ddbdf00f73e1c08eae21c695"
