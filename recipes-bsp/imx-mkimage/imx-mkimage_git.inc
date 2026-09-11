# Copyright 2017-2026 NXP

# Canonical DEPENDS for this native tool; the require sits in the .bb so oelint
# sees this base assignment as "after include".
# nooelint: oelint.vars.dependsappend
DEPENDS = "openssl-native zlib-native"

SRC_URI = "${IMX_MKIMAGE_SRC};branch=${SRCBRANCH} \
           file://0001-iMX8M-soc.mak-use-native-mkimage-from-sysroot.patch \
"
IMX_MKIMAGE_SRC ?= "git://github.com/nxp-imx/imx-mkimage.git;protocol=https"
SRCBRANCH = "lf-6.18.20_2.0.0"
SRCREV = "1b577853ae1afe1f26cdef27548da52fb424af48"

BOOT_TOOLS = "imx-boot-tools"
SYSROOT_DIRS += "/boot"
