# Adapted from linux-imx_3.10.17.bb

SUMMARY = "Linux real-time kernel based on linux-imx"
DESCRIPTION = "Linux kernel that is based on Freescale's linux-imx, \
with added real-time capabilities."

require recipes-kernel/linux/linux-imx.inc
require recipes-kernel/linux/linux-dtb.inc

DEPENDS += "lzop-native bc-native"

SRCBRANCH = "imx_3.10.17_1.0.0_ga"
SRCREV = "33597e348b2d60dd5c71890ef7b7d3d3fd6e4e97"
LOCALVERSION = "-1.0.2_ga"

COMPATIBLE_MACHINE = "(mx6)"

SRC_URI += "\
	https://www.kernel.org/pub/linux/kernel/projects/rt/3.10/older/patch-3.10.17-rt12.patch.bz2;name=rt-patch1 \
	file://0001-fix-build.patch \
	file://0002-fix-build-with-rt-enabled.patch \
	file://0003-no-split-ptlocks.patch \
"

SRC_URI[rt-patch1.md5sum] = "77a28c8b20b01f280dcd860e606a6edd"
SRC_URI[rt-patch1.sha256sum] = "ce219268f08eecccb39ff2b5be83657d53ca67cb1c6b81021494075197190351"
