# Copyright 2017-2018 NXP

DEPENDS = "zlib-native openssl-native"

SRCBRANCH = "imx_4.9.123_imx8mm_ga"
SRC_URI = "git://source.codeaurora.org/external/imx/imx-mkimage.git;protocol=https;branch=${SRCBRANCH}"
SRCREV = "9e289f314eddb9d38cab73780b936acb30bb88b4"
S = "${WORKDIR}/git"

BOOT_TOOLS = "imx-boot-tools"
SYSROOT_DIRS += "/boot"
