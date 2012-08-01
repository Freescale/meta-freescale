LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

PV = "3.0.34"
PR = "r3"

SRCREV = "1a395700fb4084c9179a8b20dca76a74e71dd833"
SRC_URI = "git://git.freescale.com/ppc/sdk/linux.git \
	  "

KSRC ?= ""
S = '${@base_conditional("KSRC", "", "${WORKDIR}/git", "${KSRC}", d)}'

# make everything compatible for the time being
COMPATIBLE_MACHINE_$MACHINE = "$MACHINE"
