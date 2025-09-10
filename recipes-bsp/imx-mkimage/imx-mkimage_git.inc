# Copyright 2017-2025 NXP

DEPENDS = "zlib-native openssl-native"

SRC_URI = "git://github.com/nxp-imx/imx-mkimage.git;protocol=https;branch=${SRCBRANCH} \
           file://0001-iMX8M-soc.mak-use-native-mkimage-from-sysroot.patch \
"
SRCBRANCH = "lf-6.12.20_2.0.0"
SRCREV = "4c2e5b25232f5aa003976ddca9d1d2fb9667beb1"

S = "${WORKDIR}/git"

BOOT_TOOLS = "imx-boot-tools"
SYSROOT_DIRS += "/boot"
