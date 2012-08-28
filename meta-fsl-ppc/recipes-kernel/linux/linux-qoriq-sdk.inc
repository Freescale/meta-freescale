LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

PV = "3.0.34"
PR = "r5"

SRCREV = "fsl-sdk-v1.2.1"
SRC_URI = "git://git.freescale.com/ppc/sdk/linux.git \
           file://0001-compiler.h-Undef-before-redefining-__attribute_const.patch \
	  "

KSRC ?= ""
S = '${@base_conditional("KSRC", "", "${WORKDIR}/git", "${KSRC}", d)}'

# make everything compatible for the time being
COMPATIBLE_MACHINE_$MACHINE = "$MACHINE"
