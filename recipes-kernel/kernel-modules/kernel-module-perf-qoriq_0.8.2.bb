DESCRIPTION = "QorIQ extension to Perf for supporting non core counters"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=e29234dd5d40dc352cc60cc0c93437ba"

SRC_URI = "git://git.freescale.com/ppc/sdk/qoriq-perf.git;branch=master"
SRCREV = "7beb3783edac66bab00c85d99a7b073f569af7fd"

S = "${WORKDIR}/git"

inherit module autotools-brokensep qoriq_build_64bit_kernel

PROCESSOR_REV ?= "B4860_R1"
EXTRA_OECONF += "--with-linux=${STAGING_KERNEL_DIR} \
    --with-processor=${PROCESSOR_REV}"

EXTRA_OEMAKE += 'SYSROOT="${D}"'

COMPATIBLE_MACHINE = "(b4860qds)"
