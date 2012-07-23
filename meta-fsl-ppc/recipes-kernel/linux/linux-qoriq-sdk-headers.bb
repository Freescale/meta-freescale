DESCRIPTION = "Linux kernel headers for Freescale platforms"
SECTION = "devel"
LICENSE = "GPLv2"

INHIBIT_DEFAULT_DEPS = "1"
PROVIDES = "linux-libc-headers ${PN}"
RPROVIDES_${PN}-dev += "linux-libc-headers-dev"
RPROVIDES_${PN}-dbg += "linux-libc-headers-dbg"
RDEPENDS_${PN}-dev = ""
RRECOMMENDS_${PN}-dbg = "linux-libc-headers-dev (= ${EXTENDPKGV})"

require recipes-kernel/linux/linux-qoriq-sdk.inc

inherit kernel-arch

do_configure() {
        oe_runmake allnoconfig
}

do_compile () {
}

do_install() {
        oe_runmake headers_install INSTALL_HDR_PATH=${D}${exec_prefix}
        # Kernel should not be exporting this header
        rm -f ${D}${exec_prefix}/include/scsi/scsi.h

        # The ..install.cmd conflicts between various configure runs
        find ${D}${includedir} -name ..install.cmd | xargs rm -f
}
