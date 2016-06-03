DESCRIPTION = "A Linux-based utility supporting console multiplexing and demultiplexing"
SECTION = "mux-server"
LICENSE = "LGPL-2.1"
LIC_FILES_CHKSUM = "file://COPYING;md5=4fbd65380cdd255951079008b364516c"

SRC_URI = "git://git.freescale.com/ppc/sdk/hypervisor/mux_server.git;branch=master \
    file://0001-Makefile-Fix-the-warning-during-build.patch \
"
SRCREV = "3e4c6a44a81bb5cf2996830e8034d26850f80efc"

S = "${WORKDIR}/git"

EXTRA_OEMAKE='HOSTCC="${CC}"'

inherit deploy

do_install () {
    install -d ${D}${bindir}
    install -m 755 mux_server ${D}${bindir}
}

do_deploy() {
    install -d ${DEPLOYDIR}/hv
    install -m 755 mux_server ${DEPLOYDIR}/hv/mux_server-${PKGV}-${PKGR}
    ln -sf mux_server-${PKGV}-${PKGR} ${DEPLOYDIR}/hv/mux_server
}
addtask deploy after do_install

BBCLASSEXTEND = "native nativesdk"
