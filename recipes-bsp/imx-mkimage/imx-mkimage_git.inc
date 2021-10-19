# Copyright 2017-2021 NXP

DEPENDS = "zlib-native openssl-native"

SRCBRANCH = "lf-5.10.52_2.1.0"
SRC_URI = "git://source.codeaurora.org/external/imx/imx-mkimage.git;protocol=https;branch=${SRCBRANCH} \
           file://0001-mkimage_fit_atf-fix-fit-generator-node-naming.patch \
"
SRCREV = "1112c88470f339dc631e2a7117087f416af6d6b5"
S = "${WORKDIR}/git"

BOOT_TOOLS = "imx-boot-tools"
SYSROOT_DIRS += "/boot"
