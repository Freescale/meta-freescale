SUMMARY = "Open Data Plane Interface Implementation"
DESCRIPTION = "OpenDataPlane (ODP) provides a data plane application programming \
        environment that is easy to use, high performance, and portable between networking SoCs."
HOMEPAGE = "http://www.opendataplane.org"
SECTION = "console/network"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=4ccfa994aa96974cfcd39a59faee20a2"

FILESEXTRAPATHS_prepend := "${THISDIR}/odp:"

SRC_URI = " \
git://git.freescale.com/ppc/sdk/odp.git;nobranch=1 \
git://git.freescale.com/ppc/sdk/qbman_userspace.git;nobranch=1;name=qbman;destsuffix=git/platform/linux-dpaa2/flib/qbman \
git://git.freescale.com/ppc/sdk/flib.git;nobranch=1;name=rta;destsuffix=git/platform/linux-dpaa2/flib/rta \
"
SRC_URI += "file://disable-the-Werror-flag.patch"

SRCREV = "a8935384642f5da8236260dd553c52dbbc7c63ed"
SRCREV_qbman = "0b8648d702f701417cd6cc3cbcdc228e640d20b6"
SRCREV_rta = "bbab28b03ae21c52ce913f8b052acf53bd24ff5d"

S = "${WORKDIR}/git"

COMPATIBLE_MACHINE = "(ls1043a|ls1046a|ls2080a|ls2088a)"
