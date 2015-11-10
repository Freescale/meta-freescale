require recipes-kernel/linux/linux-qoriq.inc

SRC_URI = "git://git.freescale.com/ppc/sdk/linux.git;nobranch=1 \
    file://modify-defconfig-t1040-nr-cpus.patch \
    file://net-sctp-CVE-2014-0101.patch \
    file://0001-ARM-8158-LLVMLinux-use-static-inline-in-ARM-ftrace.patch \
    file://0001-ARM-LLVMLinux-Change-extern-inline-to-static-inline.patch \
    file://0003-use-static-inline-in-ARM-lifeboot.h.patch \
    file://0001-powerpc-Align-TOC-to-256-bytes.patch \
"

SRCREV = "f488de6741d5ba805b9fe813d2ddf32368d3a888"
