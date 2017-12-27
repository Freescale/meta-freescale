SUMMARY = "Open Data Plane Interface Implementation"
DESCRIPTION = "OpenDataPlane (ODP) provides a data plane application programming \
        environment that is easy to use, high performance, and portable between networking SoCs."
HOMEPAGE = "http://www.opendataplane.org"
SECTION = "console/network"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ca6103dc75397fb6bec596187d6b7829"

FILESEXTRAPATHS_prepend := "${THISDIR}/odp:"

SRC_URI = " \
git://github.com/qoriq-open-source/odp.git;nobranch=1 \
git://github.com/qoriq-open-source/qbman_userspace.git;nobranch=1;name=qbman;destsuffix=git/platform/linux-dpaa2/flib/qbman \
git://github.com/qoriq-open-source/flib.git;nobranch=1;name=rta;destsuffix=git/platform/linux-dpaa2/flib/rta \
"

SRC_URI += "file://0001-Fix-this-build-error.patch"

SRCREV = "55abe6d74255d0de09c9fc013ce2a3d85cac526e"

SRCREV_qbman = "75ff61a7ca6acdbdbb780161b053cbcbc990f1be"
SRCREV_rta = "bbab28b03ae21c52ce913f8b052acf53bd24ff5d"

S = "${WORKDIR}/git"

COMPATIBLE_MACHINE = "(ls1043a|ls1046a|ls2080a|ls2088a|ls1088a)"
