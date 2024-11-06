DESCRIPTION = "AIOP Tool is a userspace application for performing operations \
on an AIOP Tile using MC interfaces. This application enables the user to \
fetch status of tile, load a valid ELF file and run it on a tile and get and set \
time of day."
SECTION = "dpaa2"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"

SRC_URI = "git://github.com/nxp-qoriq/gpp-aioptool;protocol=https;nobranch=1 \
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
