DESCRIPTION = "AIOP Tool is a userspace application for performing operations \
on an AIOP Tile using MC interfaces. This application enables the user to \
fetch status of tile, load a valid ELF file and run it on a tile and get and set \
time of day."
SECTION = "dpaa2"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=386a6287daa6504b7e7e5014ddfb3987"

SRC_URI = "git://source.codeaurora.org/external/qoriq/qoriq-components/gpp-aioptool;nobranch=1 \
"
SRCREV = "43b842b493482385b93e34fa7cab09bd96802af2"

S = "${WORKDIR}/git"

TARGET_CC_ARCH += "${LDFLAGS}"

EXTRA_OEMAKE = 'CC="${CC}" LD="${CC}" KERNEL_PATH="${STAGING_KERNEL_DIR}"'

do_configure[depends] += "virtual/kernel:do_shared_workdir"

do_install () {
    oe_runmake install DESTDIR=${D}
}

COMPATIBLE_MACHINE = "(ls2088a|ls1088a)"
