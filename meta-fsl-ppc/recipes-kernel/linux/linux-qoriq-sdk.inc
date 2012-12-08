LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

PV = "3.0.48"
PV_e6500 = "3.0.51"
PV_e6500-64b = "3.0.51"
PR = "r10"

SRCREV = "dd5a5aac3e15b16a4bccbab29c736bda0e7d55e7"
SRCREV_e6500 = "c294ca97427698b749c6fe4b768b9cc1dd37dc04"
SRCREV_e6500-64b = "c294ca97427698b749c6fe4b768b9cc1dd37dc04"
SRC_URI = "git://git.freescale.com/ppc/sdk/linux.git \
           file://0001-compiler.h-Undef-before-redefining-__attribute_const.patch \
	   file://0001-powerpc-Fix-build-dependencies-for-c-files-requiring.patch \
	  "
SRC_URI_e6500 = "git://git.freescale.com/ppc/sdk/linux.git \
           file://0001-compiler.h-Undef-before-redefining-__attribute_const.patch \
"
SRC_URI_e6500-64b = "git://git.freescale.com/ppc/sdk/linux.git \
           file://0001-compiler.h-Undef-before-redefining-__attribute_const.patch \
"

KSRC ?= ""
S = '${@base_conditional("KSRC", "", "${WORKDIR}/git", "${KSRC}", d)}'
# make everything compatible for the time being
COMPATIBLE_MACHINE_$MACHINE = "$MACHINE"

python () {
	ma = d.getVar("DISTRO_FEATURES", True)
	arch = d.getVar("OVERRIDES", True)

	# the : after the arch is to skip the message on 64b
	if not "multiarch" in ma and ("e5500:" in arch or "e6500:" in arch):
		raise bb.parse.SkipPackage("Building the kernel for this arch requires multiarch to be in DISTRO_FEATURES")

	promote_kernel = d.getVar('BUILD_64BIT_KERNEL')

	if promote_kernel == "1":
		d.setVar('KERNEL_CC_append', ' -m64')
		d.setVar('KERNEL_LD_append', ' -melf64ppc')

	error_qa = d.getVar('ERROR_QA', True)
	if 'arch' in error_qa:
		d.setVar('ERROR_QA', error_qa.replace(' arch', ''))

	all_qa = d.getVar('ALL_QA', True)
	if 'arch' in all_qa:
		d.setVar('ALL_QA', all_qa.replace(' arch', ''))
}
