DESCRIPTION = "Decompression Compression Engine Userspace Utils"
SECTION = "dpaa2"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=956df5ea6cfe0a1dcf2dee7ca37c0cdf"

SRC_URI = "git://source.codeaurora.org/external/qoriq/qoriq-components/dce;nobranch=1 \
      git://source.codeaurora.org/external/qoriq/qoriq-components/qbman_userspace;nobranch=1;name=qbman;destsuffix=git/lib/qbman_userspace \
"
SRCREV = "0ec35ad12ad04b5dadfa9f8c8a718c715bde41db"
SRCREV_qbman = "a1af1e1528fe2e1ce0df1e6d9170b6c239c8ab4f"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = 'CROSS_COMPILE=${TARGET_PREFIX} CC="${TARGET_PREFIX}gcc ${TOOLCHAIN_OPTIONS}"'

do_install () {
    oe_runmake install DESTDIR=${D}
}

INSANE_SKIP_${PN} = "ldflags"
COMPATIBLE_MACHINE = "(qoriq-arm64)"
