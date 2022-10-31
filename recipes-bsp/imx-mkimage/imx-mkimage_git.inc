# Copyright 2017-2022 NXP

DEPENDS = "zlib-native openssl-native"

SRC_URI = "git://github.com/nxp-imx/imx-mkimage.git;protocol=https;branch=${SRCBRANCH} \
           file://0001-iMX8M-soc.mak-use-native-mkimage-from-sysroot.patch \
"
SRCBRANCH = "lf-5.15.52_2.1.0"
SRCREV = "37648c8ba5094420faa8206d8cb8e0c6f17d72fc"

S = "${WORKDIR}/git"

BOOT_TOOLS = "imx-boot-tools"
SYSROOT_DIRS += "/boot"
