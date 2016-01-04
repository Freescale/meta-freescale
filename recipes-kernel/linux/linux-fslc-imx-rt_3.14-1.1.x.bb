SUMMARY = "Realtime version of the FSL Community BSP i.MX6 Linux kernel with backported features and fixes"
DESCRIPTION = "Linux kernel based on Freescale 3.14.52-1.1.0 GA release, used by FSL Community BSP in order to \
provide support for i.MX6 based platforms and include official Linux kernel stable updates, backported \
features and fixes coming from the vendors, kernel community or FSL Community itself. \
In addition, this kernel has the realtime patch (PREEMPT_RT) applied."

include linux-fslc.inc

PV .= "+git${SRCPV}"

SRCBRANCH = "3.14-1.1.x-imx"
SRCREV = "327d5c9063b715c91a88655533d5e477a0afe218"

SRC_URI += "\
    https://www.kernel.org/pub/linux/kernel/projects/rt/3.14/patch-3.14.58-rt59.patch.gz;name=rt-patch1 \
    file://0001-fix-build.patch \
    file://0003-no-split-ptlocks.patch \
    file://0004-imx-sdma-channel-use-raw-spinlock.patch \
"

SRC_URI[rt-patch1.md5sum] = "d4b380eab31878e607e92fec3e150e8f"
SRC_URI[rt-patch1.sha256sum] = "56c8a9fc6b1c11883ee40b4edc529d19cf9ba0855dd0d0878581900f51d60064"

python () {
    using_builtin_driver = (d.getVar("MACHINE_USES_VIVANTE_KERNEL_DRIVER_MODULE", True) or "") != "1"
    if not using_builtin_driver:
        raise bb.parse.SkipPackage('You must use the builtin driver with the Linux RT patch as the external module does not yet include support for it. Set "MACHINE_USES_VIVANTE_KERNEL_DRIVER_MODULE" accordingly.')
}


COMPATIBLE_MACHINE = "(mx6|mx7)"
