# Adapted from linux-fslc-mx6_3.14-1.0.x.bb

SUMMARY = "Realtime version of the FSL Community BSP i.MX6 Linux kernel with backported features and fixes"
DESCRIPTION = "Linux kernel based on Freescale 3.14.28 GA release, used by FSL Community BSP in order to \
provide support for i.MX6 based platforms and include official Linux kernel stable updates, backported \
features and fixes coming from the vendors, kernel community or FSL Community itself. \
In addition, this kernel has the realtime patch (PREEMPT_RT) applied."

include linux-fslc.inc

PV .= "+git${SRCPV}"

SRCBRANCH = "3.14-1.0.x-mx6"
SRCREV = "964e5a3e65936e07f5d5189d233b8f8843687776"

SRC_URI += "\
    https://www.kernel.org/pub/linux/kernel/projects/rt/3.14/older/patch-3.14.51-rt52.patch.gz;name=rt-patch1 \
    file://0001-fix-build.patch \
    file://0002-fix-build-with-rt-enabled.patch \
    file://0003-no-split-ptlocks.patch \
    file://0004-imx-sdma-channel-use-raw-spinlock.patch \
"

SRC_URI[rt-patch1.md5sum] = "236a97a3722c21403bbe60350e9be184"
SRC_URI[rt-patch1.sha256sum] = "a454c6fabbd3a0698feec6a1bb71bc8af8d8f40a424ca071b30bee40c32f2c20"

COMPATIBLE_MACHINE = "(mx6)"
