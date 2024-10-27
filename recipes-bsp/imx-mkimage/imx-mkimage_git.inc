# Copyright 2017-2023 NXP

DEPENDS = "zlib-native openssl-native"

SRC_URI = "git://github.com/nxp-imx/imx-mkimage.git;protocol=https;branch=${SRCBRANCH} \
           file://0001-iMX8M-soc.mak-use-native-mkimage-from-sysroot.patch \
"
SRCBRANCH = "lf-6.6.36_2.1.0"
SRCREV = "4622115cbc037f79039c4522faeced4aabea986b"

S = "${WORKDIR}/git"

BOOT_TOOLS = "imx-boot-tools"
SYSROOT_DIRS += "/boot"
