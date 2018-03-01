DESCRIPTION = "Frame Manager User Space Library"
SECTION = "fman"
LICENSE = "BSD & GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=3f16fa8e677e45af3127c5c4bafc3c00"

PR = "r1"

SRC_URI = "git://source.codeaurora.org/external/qoriq/qoriq-components/fmlib;nobranch=1"
SRCREV = "95f34b1fe585ba0eced11433c88a593a16baf281"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = "DESTDIR=${D} PREFIX=${prefix} LIB_DEST_DIR=${libdir} \
        CROSS_COMPILE=${TARGET_PREFIX} KERNEL_SRC=${STAGING_KERNEL_DIR}"

FMLIB_TARGET = "libfm-arm"
do_compile () {
    oe_runmake ${FMLIB_TARGET}.a
}

do_install () {
    oe_runmake install-${FMLIB_TARGET}
}

do_compile[depends] += "virtual/kernel:do_shared_workdir"

ALLOW_EMPTY_${PN} = "1"

PACKAGE_ARCH = "${MACHINE_ARCH}"

