DESCRIPTION = "Decompression Compression Engine Userspace Utils"
SECTION = "dpaa2"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=956df5ea6cfe0a1dcf2dee7ca37c0cdf"

SRC_URI = "git://github.com/nxp-qoriq/dce;protocol=https;nobranch=1 \
      git://github.com/nxp-qoriq/qbman_userspace;protocol=https;nobranch=1;name=qbman;destsuffix=git/lib/qbman_userspace \
      file://0001-support-user-merge.patch \
"
SRCREV = "88ef2e8c3845532ee64cea4349fd38fb2bd5f807"
SRCREV_qbman = "2f92993a9f34e5221d6b36c63b9e30ef703e9ac3"

SRCREV_FORMAT = "default_qbman"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = 'CROSS_COMPILE=${TARGET_PREFIX} CC="${TARGET_PREFIX}gcc ${TOOLCHAIN_OPTIONS}"'

do_install () {
    oe_runmake install DESTDIR=${D}
}

INSANE_SKIP:${PN} = "ldflags"
COMPATIBLE_MACHINE = "(qoriq-arm64)"
