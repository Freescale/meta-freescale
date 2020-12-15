DESCRIPTION = "Decompression Compression Engine Userspace Utils"
SECTION = "dpaa2"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=956df5ea6cfe0a1dcf2dee7ca37c0cdf"

SRC_URI = "git://source.codeaurora.org/external/qoriq/qoriq-components/dce;nobranch=1 \
      git://source.codeaurora.org/external/qoriq/qoriq-components/qbman_userspace;nobranch=1;name=qbman;destsuffix=git/lib/qbman_userspace \
"
SRCREV = "9db9c08379aa89f45f514f4f3f0a8e8212198758"
SRCREV_qbman = "2f92993a9f34e5221d6b36c63b9e30ef703e9ac3"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = 'CROSS_COMPILE=${TARGET_PREFIX} CC="${TARGET_PREFIX}gcc ${TOOLCHAIN_OPTIONS}"'

do_install () {
    oe_runmake install DESTDIR=${D}
}

INSANE_SKIP_${PN} = "ldflags"
COMPATIBLE_MACHINE = "(qoriq-arm64)"
