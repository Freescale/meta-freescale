# Copyright 2017-2021 NXP

DEPENDS = "zlib-native openssl-native"

SRCBRANCH = "lf-5.10.72_2.2.0"
SRC_URI = "git://source.codeaurora.org/external/imx/imx-mkimage.git;protocol=https;branch=${SRCBRANCH} \
           file://0001-mkimage_fit_atf-fix-fit-generator-node-naming.patch \
           file://0001-iMX8M-soc.mak-use-native-mkimage-from-sysroot.patch \
"
SRCREV = "7a277c8a1a21ff921d217889dde6a9f84e6d2168"
S = "${WORKDIR}/git"

BOOT_TOOLS = "imx-boot-tools"
SYSROOT_DIRS += "/boot"
