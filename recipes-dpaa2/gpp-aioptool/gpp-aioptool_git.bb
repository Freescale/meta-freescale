DESCRIPTION = "AIOP Tool is a userspace application for performing operations \
on an AIOP Tile using MC interfaces. This application enables the user to \
fetch status of tile, load a valid ELF file and run it on a tile and get and set \
time of day."
SECTION = "dpaa2"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=386a6287daa6504b7e7e5014ddfb3987"

SRC_URI = "git://github.com/nxp-qoriq/qoriq-components/gpp-aioptool;nobranch=1 \
    file://0001-remove-libio.h.patch \
    file://0001-add-fcommon-to-fix-gcc-10-build-issue.patch \
"
SRCREV = "6ead470dde043f3ca67f1ba19b313dd64ec199e1"

S = "${WORKDIR}/git"

TARGET_CC_ARCH += "${LDFLAGS}"

EXTRA_OEMAKE = 'CC="${CC}" LD="${CC}" KERNEL_PATH="${STAGING_KERNEL_DIR}"'

do_configure[depends] += "virtual/kernel:do_shared_workdir"

do_install () {
    oe_runmake install DESTDIR=${D}
    chown -R root:root ${D}${bindir}/aiop_tool
}

COMPATIBLE_MACHINE = "(ls2088a|ls1088a)"
