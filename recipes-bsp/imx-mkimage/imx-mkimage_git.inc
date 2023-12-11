# Copyright 2017-2022 NXP

DEPENDS = "zlib-native openssl-native"

SRC_URI = "git://github.com/nxp-imx/imx-mkimage.git;protocol=https;branch=${SRCBRANCH} \
           file://0001-iMX8M-soc.mak-use-native-mkimage-from-sysroot.patch \
"
SRCBRANCH = "lf-6.1.36_2.1.0"
SRCREV = "5a0faefc223e51e088433663b6e7d6fbce89bf59"

S = "${WORKDIR}/git"

BOOT_TOOLS = "imx-boot-tools"
SYSROOT_DIRS += "/boot"
