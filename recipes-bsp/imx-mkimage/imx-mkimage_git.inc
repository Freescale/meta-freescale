# Copyright 2017-2026 NXP
 
DEPENDS = "zlib-native openssl-native"

SRC_URI = "${IMX_MKIMAGE_SRC};branch=${SRCBRANCH} \
           file://0001-iMX8M-soc.mak-use-native-mkimage-from-sysroot.patch \
"
IMX_MKIMAGE_SRC ?= "git://github.com/nxp-imx/imx-mkimage.git;protocol=https"
SRCBRANCH = "lf-6.18.2_1.0.0"
SRCREV = "c0debd7c0b4a125bd3ca66bc68f1915882b2bb62"

BOOT_TOOLS = "imx-boot-tools"
SYSROOT_DIRS += "/boot"
