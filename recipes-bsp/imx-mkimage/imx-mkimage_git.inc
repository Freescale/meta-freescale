# Copyright 2017-2019 NXP

DEPENDS = "zlib-native openssl-native"

SRCBRANCH = "lf-5.4.y"
SRC_URI = "git://source.codeaurora.org/external/imx/imx-mkimage.git;protocol=https;branch=${SRCBRANCH}"
SRCREV = "1c2277b95ce59f5d0265f26fec522e2ac2581e59"
S = "${WORKDIR}/git"

BOOT_TOOLS = "imx-boot-tools"
SYSROOT_DIRS += "/boot"
