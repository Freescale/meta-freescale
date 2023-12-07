# Copyright 2017-2022 NXP

DEPENDS = "zlib-native openssl-native"

SRC_URI = "git://github.com/nxp-imx/imx-mkimage.git;protocol=https;branch=${SRCBRANCH} \
           file://0001-iMX8M-soc.mak-use-native-mkimage-from-sysroot.patch \
           file://0001-iMX9-soc.mak-dynamically-set-AHAB_IMG.patch \
"
SRCBRANCH = "lf-6.1.22_2.0.0"
SRCREV = "5cfd218012e080fb907d9cc301fbb4ece9bc17a9"

S = "${WORKDIR}/git"

BOOT_TOOLS = "imx-boot-tools"
SYSROOT_DIRS += "/boot"
