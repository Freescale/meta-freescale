SUMMARY = "Realtime version of the FSL Community BSP i.MX6 Linux kernel with backported features and fixes"
DESCRIPTION = "Linux kernel based on NXP 4.1.15-1.0.0 GA release, used by FSL Community BSP in order to \
provide support for i.MX6 based platforms and include official Linux kernel stable updates, backported \
features and fixes coming from the vendors, kernel community or FSL Community itself. \
In addition, this kernel has the realtime patch (PREEMPT_RT) applied."

include linux-fslc.inc

PV .= "+git${SRCPV}"

SRCBRANCH = "4.1-1.0.x-imx"
SRCREV = "445b81a703861b3c146ccd074cb5c14a5363c6d3"

SRC_URI += " \
    https://www.kernel.org/pub/linux/kernel/projects/rt/4.1/older/patch-4.1.19-rt22.patch.gz;name=patch-4.1.19-rt22.patch \
    file://0001-fix-build.patch \
    file://0002-no-split-ptlocks.patch \
    file://0003-Work-around-CPU-stalls-in-the-imx-sdma-driver.patch \
"

SRC_URI[patch-4.1.19-rt22.patch.md5sum] = "20a893d189c619ea3e7489f870478e7a"
SRC_URI[patch-4.1.19-rt22.patch.sha256sum] = "81a6429eb03f085e7f58a669eb23719ae14876c14fe244f5aac909c28ee4104e"

python () {
    using_builtin_driver = (d.getVar("MACHINE_USES_VIVANTE_KERNEL_DRIVER_MODULE", True) or "") != "1"
    if not using_builtin_driver:
        raise bb.parse.SkipPackage('You must use the builtin driver with the Linux RT patch as the external module does not yet include support for it. Set "MACHINE_USES_VIVANTE_KERNEL_DRIVER_MODULE" accordingly.')
}


COMPATIBLE_MACHINE = "(mx6|mx7)"
