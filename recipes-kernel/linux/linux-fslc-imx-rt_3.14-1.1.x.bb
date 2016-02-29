SUMMARY = "Realtime version of the FSL Community BSP i.MX6 Linux kernel with backported features and fixes"
DESCRIPTION = "Linux kernel based on Freescale 3.14.52-1.1.0 GA release, used by FSL Community BSP in order to \
provide support for i.MX6 based platforms and include official Linux kernel stable updates, backported \
features and fixes coming from the vendors, kernel community or FSL Community itself. \
In addition, this kernel has the realtime patch (PREEMPT_RT) applied."

include linux-fslc.inc

PV .= "+git${SRCPV}"

SRCBRANCH = "3.14-1.1.x-imx"
SRCREV = "327d5c9063b715c91a88655533d5e477a0afe218"

SRC_URI += " \
    https://www.kernel.org/pub/linux/kernel/projects/rt/3.14/patch-3.14.61-rt62.patch.gz;name=patch-3.14.61-rt62.patch \
    file://0001-fix-build.patch \
    file://0003-no-split-ptlocks.patch \
    file://0004-imx-sdma-channel-use-raw-spinlock.patch \
"

SRC_URI[patch-3.14.61-rt62.patch.md5sum] = "d275057ffe5e6dac3c3d8704773c0aee"
SRC_URI[patch-3.14.61-rt62.patch.sha256sum] = "48df9b6e76f24aa1d6fcd5ab150d26830da35c630acba73bf8c81dd341c31951"

python () {
    using_builtin_driver = (d.getVar("MACHINE_USES_VIVANTE_KERNEL_DRIVER_MODULE", True) or "") != "1"
    if not using_builtin_driver:
        raise bb.parse.SkipPackage('You must use the builtin driver with the Linux RT patch as the external module does not yet include support for it. Set "MACHINE_USES_VIVANTE_KERNEL_DRIVER_MODULE" accordingly.')
}


COMPATIBLE_MACHINE = "(mx6|mx7)"
