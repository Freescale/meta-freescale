require recipes-kernel/linux/linux-qoriq.inc

SRC_URI = "git://git.freescale.com/ppc/sdk/linux.git;nobranch=1 \
    file://powerpc-Fix-64-bit-builds-with-binutils-2.24.patch \
"
SRCREV = "c29fe1a733308cbe592b3af054a97be1b91cf2dd"

