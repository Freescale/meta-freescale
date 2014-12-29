LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

inherit kernel fsl-kernel-localversion
LOCALVERSION ?= "+ls1"

SRC_URI = "git://git.freescale.com/ppc/sdk/linux.git;branch=${SRCBRANCH} \
    file://defconfig \
"

KERNEL_EXTRA_ARGS += "LOADADDR=${UBOOT_ENTRYPOINT}"

S = "${WORKDIR}/git"
