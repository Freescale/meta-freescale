# Copyright 2017-2019 NXP

DEPENDS = "zlib-native openssl-native"

SRCBRANCH = "imx_4.14.98_2.0.0_ga"
SRC_URI = "git://source.codeaurora.org/external/imx/imx-mkimage.git;protocol=https;branch=${SRCBRANCH}"
SRCREV = "dd0234001713623c79be92b60fa88bc07b07f24f"
S = "${WORKDIR}/git"

BOOT_TOOLS = "imx-boot-tools"
SYSROOT_DIRS += "/boot"
