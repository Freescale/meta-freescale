# Adapted from linux-imx_3.10.31.bb

SUMMARY = "Linux real-time kernel based on linux-imx"
DESCRIPTION = "Linux kernel that is based on Freescale's linux-imx, \
with added real-time capabilities."

require recipes-kernel/linux/linux-imx.inc
require recipes-kernel/linux/linux-dtb.inc

DEPENDS += "lzop-native bc-native"

SRCBRANCH = "imx_3.10.31_1.1.0_beta2"
SRCREV = "30ad12bdf93050a5bae1345bd40dba5f2d63f70f"
LOCALVERSION = "-1.1.0_beta2"

SRC_URI += "\
	file://0001-ARM-clk-imx6q-fix-video-divider-for-revision-1.0-of-.patch \
	https://www.kernel.org/pub/linux/kernel/projects/rt/3.10/older/patch-3.10.27-rt25.patch.gz;name=rt-patch1 \
	file://0001-fix-build.patch \
	file://0002-fix-build-with-rt-enabled.patch \
	file://0003-no-split-ptlocks.patch \
"

SRC_URI[rt-patch1.md5sum] = "aa231425f2a43220b5e8dbb057c7e5f9"
SRC_URI[rt-patch1.sha256sum] = "5fbee5cdf260db9b7751651ab141fb59f46d68c7aecfc32e81a2b5d702f47aac"

COMPATIBLE_MACHINE = "(mx6)"
