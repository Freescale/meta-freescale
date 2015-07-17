DESCRIPTION = "A Linux-based utility supporting console multiplexing and demultiplexing"
SECTION = "mux-server"
LICENSE = "LGPL-2.1"
# TODO: add a dedicated COPYING file
LIC_FILES_CHKSUM = "file://mux_server.c;endline=9;md5=e59eeb0812bb88b7af2d932f2dc22aed"

SRC_URI = "git://git.freescale.com/ppc/sdk/hypervisor/mux_server.git;branch=master"
SRCREV = "3e4c6a44a81bb5cf2996830e8034d26850f80efc"

S = "${WORKDIR}/git"

EXTRA_OEMAKE='HOSTCC="${CC}"'

do_install () {
    install -d ${D}${bindir}
    install -m 755 mux_server ${D}${bindir}
}

BBCLASSEXTEND = "native nativesdk"
