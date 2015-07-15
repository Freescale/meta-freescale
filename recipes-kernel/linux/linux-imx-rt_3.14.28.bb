# Adapted from linux-imx_3.14.28.bb

SUMMARY = "Linux real-time kernel based on linux-imx"
DESCRIPTION = "Linux kernel that is based on Freescale's linux-imx, \
with added real-time capabilities."

require recipes-kernel/linux/linux-imx.inc
require recipes-kernel/linux/linux-dtb.inc

DEPENDS += "lzop-native bc-native"

SRCBRANCH = "imx_3.14.28_1.0.0_ga"
SRCREV = "91cf351a2afc17ac4a260e4d2ad1e32d00925a1b"
LOCALVERSION = "-1.0.0_ga"

SRC_URI += "\
    file://0001-ARM-imx6q-drop-unnecessary-semicolon.patch \
    file://0002-ARM-clk-imx6q-fix-video-divider-for-rev-T0-1.0.patch \
    file://0003-ARM-imx6sl-Disable-imx6sl-specific-code-when-imx6sl-.patch \
    file://0004-mmc-sdhci-esdhc-imx-Fixup-runtime-PM-conditions-duri.patch \
    file://0005-Revert-net-fec-fix-the-warning-found-by-dma-debug.patch \
    https://www.kernel.org/pub/linux/kernel/projects/rt/3.14/older/patch-3.14.28-rt25.patch.gz;name=rt-patch1 \
    file://0001-fix-build.patch \
    file://0002-fix-build-with-rt-enabled.patch \
    file://0003-no-split-ptlocks.patch \
"

SRC_URI[rt-patch1.md5sum] = "28bfd1e14ccab1ea1fb48f56f982d80c"
SRC_URI[rt-patch1.sha256sum] = "4c9bd426cf559a99e169208df5535fcb18ec98daec73f148c88859a7c3333e52"

COMPATIBLE_MACHINE = "(mx6)"
