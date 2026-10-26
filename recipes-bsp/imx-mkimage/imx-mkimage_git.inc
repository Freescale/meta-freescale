# Copyright 2017-2026 NXP

# Canonical DEPENDS for this native tool; the require sits in the .bb so oelint
# sees this base assignment as "after include".
# nooelint: oelint.vars.dependsappend
DEPENDS = "openssl-native zlib-native"

SRC_URI = "${IMX_MKIMAGE_SRC};branch=${SRCBRANCH} \
           file://0001-iMX8M-soc.mak-use-native-mkimage-from-sysroot.patch \
"
IMX_MKIMAGE_SRC ?= "git://github.com/nxp-imx/imx-mkimage.git;protocol=https"
SRCBRANCH = "lf-6.18.37_2.1.0"
SRCREV = "b1f801d30cf30a5d8b8dff36f8e1acafa242d53f"

BOOT_TOOLS = "imx-boot-tools"
SYSROOT_DIRS += "/boot"
