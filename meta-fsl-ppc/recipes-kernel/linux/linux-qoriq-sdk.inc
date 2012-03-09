LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

PV = "3.0.18"
PR = "r2"

SRCREV = "1e3e8ed15249d73a066ddfe2e80882935c35dcb7"
SRC_URI = "git://git.freescale.com/ppc/sdk/linux.git \
		file://add-no-unused-but-set-variable.patch \
		file://add-no-error-array-bounds.patch \
	  "

KSRC ?= ""
S = ${@base_conditional("KSRC", "", "${WORKDIR}/git", "${KSRC}", d)}

# make everything compatible for the time being
COMPATIBLE_MACHINE_$MACHINE = $MACHINE

# only powerpc and powerpc64 are compatible
COMPATIBLE_HOST = "(powerpc|powerpc64)"
