DESCRIPTION = "Linux kernel headers for Freescale platforms"
SECTION = "devel"
LICENSE = "GPLv2"

PKGSUFFIX = ""
PKGSUFFIX_virtclass-nativesdk = "-nativesdk"

INHIBIT_DEFAULT_DEPS = "1"
PROVIDES = "linux-libc-headers${PKGSUFFIX} ${PN}"
RPROVIDES_${PN}-dev += "linux-libc-headers${PKGSUFFIX}-dev"
RPROVIDES_${PN}-dbg += "linux-libc-headers${PKGSUFFIX}-dbg"
RDEPENDS_${PN}-dev = ""
RRECOMMENDS_${PN}-dbg = "linux-libc-headers${PKGSUFFIX}-dev (= ${EXTENDPKGV})"

require recipes-kernel/linux/linux-qoriq-sdk.inc

set_arch() {
	case ${TARGET_ARCH} in
		arm*)     ARCH=arm ;;
		i*86*)    ARCH=i386 ;;
		ia64*)    ARCH=ia64 ;;
		mips*)    ARCH=mips ;;
		powerpc*) ARCH=powerpc ;;
		x86_64*)  ARCH=x86_64 ;;
	esac
}

do_configure () {
	set_arch
	cp ${KERNEL_DEFCONFIG} ${B}/.config
	oe_runmake oldconfig ARCH=${ARCH}
}
do_configure[vardepsexclude] += "KERNEL_DEFCONFIG"

do_compile () {
}

do_install() {
	set_arch
	cd ${S}
	oe_runmake headers_install INSTALL_HDR_PATH=${D}${exec_prefix} ARCH=${ARCH}

	# The ..install.cmd conflicts between various configure runs
	find ${D}${includedir} -name ..install.cmd | xargs rm -f
}

BBCLASSEXTEND = "nativesdk"
