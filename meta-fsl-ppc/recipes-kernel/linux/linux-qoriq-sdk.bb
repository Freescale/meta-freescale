inherit kernel
require recipes-kernel/linux/linux-dtb.inc

DESCRIPTION = "Linux kernel for Freescale platforms"
SECTION = "kernel"
LICENSE = "GPLv2"


require recipes-kernel/linux/linux-qoriq-sdk.inc

PR = "r10"

SRC_URI += "file://fix_getrusage_for_perf.patch \
            file://0001-Enable-the-option-Automount-devtmpfs-at-dev-in-kerne.patch \
           "

do_configure_prepend() {
	# copy desired defconfig so we pick it up for the real kernel_do_configure
	cp ${KERNEL_DEFCONFIG} ${B}/.config
}
