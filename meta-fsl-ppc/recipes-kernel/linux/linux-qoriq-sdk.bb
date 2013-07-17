inherit kernel
require recipes-kernel/linux/linux-dtb.inc

DESCRIPTION = "Linux kernel for Freescale platforms"
SECTION = "kernel"
LICENSE = "GPLv2"

require recipes-kernel/linux/linux-qoriq-sdk.inc

SRC_URI += "file://libtraceevent-Remove-hard-coded-include-to-usr-local-include-in-Makefile.patch"

PR = "${INC_PR}.1"

DEPENDS_append = " libgcc"
KERNEL_CC_append = " ${TOOLCHAIN_OPTIONS}"
KERNEL_LD_append = " ${TOOLCHAIN_OPTIONS}"

do_configure_prepend() {
	# copy desired defconfig so we pick it up for the real kernel_do_configure
	cp ${KERNEL_DEFCONFIG} ${B}/.config

	# append sdk version in kernel version if SDK_VERSION is defined
	if [ -n "${SDK_VERSION}" ]; then
		echo "CONFIG_LOCALVERSION=\"-${SDK_VERSION}\"" >> ${S}/.config
	fi
}
