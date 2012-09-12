LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

PV = "3.0.34"
PR = "r6"

SRCREV = "058c67be272af9ab95b88c941f31fb3eda05a4d3"
SRC_URI = "git://git.freescale.com/ppc/sdk/linux.git \
           file://0001-compiler.h-Undef-before-redefining-__attribute_const.patch \
	   file://0001-powerpc-Fix-build-dependencies-for-c-files-requiring.patch \
	  "

KSRC ?= ""
S = '${@base_conditional("KSRC", "", "${WORKDIR}/git", "${KSRC}", d)}'

# make everything compatible for the time being
COMPATIBLE_MACHINE_$MACHINE = "$MACHINE"

python () {
	promote_kernel = d.getVar('BUILD_64BIT_KERNEL')

	if promote_kernel != 0:
		d.setVar('KERNEL_CC_append', ' -m64')
		d.setVar('KERNEL_LD_append', ' -melf64ppc')
}
