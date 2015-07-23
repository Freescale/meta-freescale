DESCRIPTION = "QorIQ Debug File System Module"
SECTION = "qoriq-debug"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=e29234dd5d40dc352cc60cc0c93437ba"

inherit module autotools-brokensep qoriq_build_64bit_kernel

SRC_URI = "git://git.freescale.com/ppc/sdk/qoriq-debug.git;nobranch=1"
SRCREV = "20615c1ea332102635f8314cee5787c48c1a4254"

S = "${WORKDIR}/git"

EXTRA_OECONF += "--with-linux=${STAGING_KERNEL_DIR}"
EXTRA_OEMAKE += 'SYSROOT="${D}"'

