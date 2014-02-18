inherit kernel
require recipes-kernel/linux/linux-dtb.inc

DESCRIPTION = "Linux kernel for Freescale platforms"
SECTION = "kernel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

KSRC ?= ""
S = '${@base_conditional("KSRC", "", "${WORKDIR}/git", "${KSRC}", d)}'

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

DEPENDS_append = " libgcc"
KERNEL_CC_append = " ${TOOLCHAIN_OPTIONS}"
KERNEL_LD_append = " ${TOOLCHAIN_OPTIONS}"

SCMVERSION ?= "y"
DELTA_KERNEL_DEFCONFIG ?= ""
do_configure_prepend() {
	# copy desired defconfig so we pick it up for the real kernel_do_configure
	cp ${KERNEL_DEFCONFIG} ${B}/.config

	# add config fragments
	for deltacfg in ${DELTA_KERNEL_DEFCONFIG}; do
		if [ -f "${deltacfg}" ]; then
			${S}/scripts/kconfig/merge_config.sh -m .config ${deltacfg}
		elif [ -f "${S}/arch/powerpc/configs/${deltacfg}" ]; then
		    ${S}/scripts/kconfig/merge_config.sh -m .config \
			    ${S}/arch/powerpc/configs/${deltacfg}
		fi
	done

    #add git revision to the local version
    if [ "${SCMVERSION}" = "y" ]; then
            # append sdk version if SDK_VERSION is defined
            sdkversion=''
            if [ -n "${SDK_VERSION}" ]; then
                sdkversion="-${SDK_VERSION}"
            fi
            head=`git rev-parse --verify --short HEAD 2> /dev/null`
            printf "%s%s%s" $sdkversion +g $head > ${S}/.scmversion
    fi
}

# make everything compatible for the time being
COMPATIBLE_MACHINE_$MACHINE = "$MACHINE"

