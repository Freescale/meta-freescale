SUMMARY = "Layerscape Debug File System Module"
DESCRIPTION = "This package is the kernel module which is used for \
ls102xa targets debug."
SECTION = "ls-debug"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=94263f12f9416f9fd0493c8f9e8085a3"

inherit module autotools-brokensep

SRC_URI = "git://github.com/nxp-qoriq-yocto-sdk/ls-dbg;branch=nxp/master"
SRCREV = "40501f6659e880d38508cdd34a4df2d348d1c68e"

S = "${WORKDIR}/git"

EXTRA_OECONF += "--with-linux=${STAGING_KERNEL_DIR}"
EXTRA_OEMAKE += 'SYSROOT="${D}"'

COMPATIBLE_MACHINE = "(ls102xa)"
