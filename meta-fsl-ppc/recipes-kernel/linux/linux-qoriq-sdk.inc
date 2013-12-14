LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

PV = "3.8"
INC_PR = "r11"

SRC_URI = "git://git.freescale.com/ppc/sdk/linux.git;nobranch=1"
SRCREV = "3edd71eeb4e4ae27baf539e0a5f0c8e8261bf65b"

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
