SUMMARY = "Realtime version of the FSL Community BSP i.MX6 Linux kernel with backported features and fixes"
DESCRIPTION = "Linux kernel based on NXP 4.1-2.0.0 GA release, used by FSL Community BSP in order to \
provide support for i.MX6 based platforms and include official Linux kernel stable updates, backported \
features and fixes coming from the vendors, kernel community or FSL Community itself. \
In addition, this kernel has the realtime patch (PREEMPT_RT) applied."

include linux-fslc.inc

PV .= "+git${SRCPV}"

SRCBRANCH = "4.1-2.0.x-imx"
SRCREV = "176c482f56cd9c523829ceb6bce5c28d3cc6d1c0"

SRC_URI += " \
    https://www.kernel.org/pub/linux/kernel/projects/rt/4.1/older/patch-4.1.35-rt41.patch.gz;name=rt-patch \
    file://0001-fix-build.patch \
    file://0002-no-split-ptlocks.patch \
    file://0003-Work-around-CPU-stalls-in-the-imx-sdma-driver.patch \
"

SRC_URI[rt-patch.md5sum] = "6dd1193203cdf6a1a4758fc8baf07a4a"
SRC_URI[rt-patch.sha256sum] = "427e736022e59f83c9489eda889559fcd4fe4abb5646570ade32f2128f2fa725"

python () {
    using_builtin_driver = (d.getVar("MACHINE_USES_VIVANTE_KERNEL_DRIVER_MODULE", True) or "") != "1"
    if not using_builtin_driver:
        raise bb.parse.SkipPackage('You must use the builtin driver with the Linux RT patch as the external module does not yet include support for it. Set "MACHINE_USES_VIVANTE_KERNEL_DRIVER_MODULE" accordingly.')
}


COMPATIBLE_MACHINE = "(mx6|mx7)"
