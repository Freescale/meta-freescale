LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

inherit kernel fsl-kernel-localversion
LOCALVERSION ?= "-${SRCBRANCH}"

SRC_URI = "git://git.freescale.com/layerscape/ls1021a/linux.git;branch=${SRCBRANCH} \
    file://defconfig \
"

KERNEL_EXTRA_ARGS += "LOADADDR=${UBOOT_ENTRYPOINT}"

S = "${WORKDIR}/git"
