inherit kernel qoriq_build_64bit_kernel
require recipes-kernel/linux/linux-dtb.inc

DESCRIPTION = "Linux kernel for Freescale platforms"
SECTION = "kernel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

KSRC ?= ""
S = '${@base_conditional("KSRC", "", "${WORKDIR}/git", "${KSRC}", d)}'

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

