# Copyright 2017-2023 NXP

DEPENDS = "zlib-native openssl-native"

SRC_URI = "git://github.com/nxp-imx/imx-mkimage.git;protocol=https;branch=${SRCBRANCH} \
           file://0001-iMX8M-soc.mak-use-native-mkimage-from-sysroot.patch \
"
SRCBRANCH = "lf-6.6.52_2.2.0"
SRCREV = "71b8c18af93a5eb972d80fbec290006066cff24f"

S = "${WORKDIR}/git"

BOOT_TOOLS = "imx-boot-tools"
SYSROOT_DIRS += "/boot"
