# Copyright 2017-2021 NXP

DEPENDS = "zlib-native openssl-native"

SRCBRANCH = "lf-5.15.5_1.0.0"
SRC_URI = "git://source.codeaurora.org/external/imx/imx-mkimage.git;protocol=https;branch=${SRCBRANCH} \
           file://0001-mkimage_fit_atf-fix-fit-generator-node-naming.patch \
           file://0001-iMX8M-soc.mak-use-native-mkimage-from-sysroot.patch \
           file://0001-Add-support-for-overriding-BL32-and-BL33-not-only-BL.patch \
           file://0001-Add-LDFLAGS-to-link-step.patch \
           file://0001-Add-support-for-overriding-BL31-BL32-and-BL33.patch \
"
SRCREV = "22346a32a88aa752d4bad8f2ed1eb641e18849dc"
S = "${WORKDIR}/git"

BOOT_TOOLS = "imx-boot-tools"
SYSROOT_DIRS += "/boot"
