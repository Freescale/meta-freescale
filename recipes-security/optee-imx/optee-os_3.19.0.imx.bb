# Copyright (C) 2017-2021 NXP

require optee-os-fslc-imx.inc

SRC_URI += "file://0001-core-Define-section-attributes-for-clang.patch \
            file://0006-allow-setting-sysroot-for-libgcc-lookup.patch \
            file://0007-allow-setting-sysroot-for-clang.patch \
            file://0010-add-note-GNU-stack-section.patch"
SRCBRANCH = "lf-5.15.71_2.2.0"
SRCREV = "00919403f040fad4f8603e605932281ff8451b1d"
