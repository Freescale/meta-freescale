# Copyright 2017-2019 NXP

DEPENDS = "zlib-native openssl-native"

SRCBRANCH = "imx_5.4.24_2.1.0"
SRC_URI = "git://source.codeaurora.org/external/imx/imx-mkimage.git;protocol=https;branch=${SRCBRANCH}"
SRCREV = "6745ccdcf15384891639b7ced3aa6ce938682365"
S = "${WORKDIR}/git"

BOOT_TOOLS = "imx-boot-tools"
SYSROOT_DIRS += "/boot"
