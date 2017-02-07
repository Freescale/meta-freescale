SUMMARY = "Realtime version of the FSL Community BSP i.MX6 Linux kernel with backported features and fixes"
DESCRIPTION = "Linux kernel based on NXP 4.1-2.0.0 GA release, used by FSL Community BSP in order to \
provide support for i.MX6 based platforms and include official Linux kernel stable updates, backported \
features and fixes coming from the vendors, kernel community or FSL Community itself. \
In addition, this kernel has the realtime patch (PREEMPT_RT) applied."

include linux-fslc.inc

PV .= "+git${SRCPV}"

SRCBRANCH = "4.1-2.0.x-imx"
SRCREV = "ee67fc7e072df596577e3a4e4fce7b51816d4b0a"

SRC_URI += " \
    https://www.kernel.org/pub/linux/kernel/projects/rt/4.1/older/patch-4.1.37-rt43.patch.gz;name=rt-patch \
    file://0001-fix-build.patch \
    file://0002-no-split-ptlocks.patch \
    file://0003-Work-around-CPU-stalls-in-the-imx-sdma-driver.patch \
    file://0004-export-swait-locked-functions.patch \
"

SRC_URI[rt-patch.md5sum] = "9a97ddbfa51b01a70ae063258fea76ab"
SRC_URI[rt-patch.sha256sum] = "0f77da50f3ef891af67639257b9cfa0f32f69a9cc945947453ee5fd7c1789fb9"


COMPATIBLE_MACHINE = "(mx6|mx7)"
