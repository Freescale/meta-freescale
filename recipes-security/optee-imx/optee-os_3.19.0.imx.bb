# Copyright (C) 2017-2021 NXP

require optee-os-fslc-imx.inc

SRC_URI += "file://0001-core-Define-section-attributes-for-clang.patch \
            file://0006-allow-setting-sysroot-for-libgcc-lookup.patch \
            file://0007-allow-setting-sysroot-for-clang.patch \
            file://0010-add-note-GNU-stack-section.patch"
SRCBRANCH = "lf-6.1.1_1.0.0"
SRCREV = "ad4e8389bb2c38efe39853925eec571ac778c575"
