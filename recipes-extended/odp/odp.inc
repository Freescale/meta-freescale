SUMMARY = "Open Data Plane Interface Implementation"
DESCRIPTION = "OpenDataPlane (ODP) provides a data plane application programming \
        environment that is easy to use, high performance, and portable between networking SoCs."
HOMEPAGE = "http://www.opendataplane.org"
SECTION = "console/network"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=472053b942339f200d9e1d2dcb947e9d"

FILESEXTRAPATHS_prepend := "${THISDIR}/odp:"

SRC_URI = " \
git://github.com/qoriq-open-source/odp.git;nobranch=1 \
git://github.com/qoriq-open-source/qbman_userspace.git;nobranch=1;name=qbman;destsuffix=git/platform/linux-dpaa2/flib/qbman \
git://github.com/qoriq-open-source/flib.git;nobranch=1;name=rta;destsuffix=git/platform/linux-dpaa2/flib/rta \
"

SRC_URI += "file://0001-Fix-this-build-error.patch"

SRCREV = "fdb26cb5c751afadf2299da2a1376e6ca0e61cc7"
SRCREV_qbman = "097595befc2bb60b733027e94ad2ed16a0e9cae0"
SRCREV_rta = "2c9b9dc22699f7c00f24a2134c42901932a6a0b9"

S = "${WORKDIR}/git"

COMPATIBLE_MACHINE = "(ls1043a|ls1046a|ls2080a|ls2088a|ls1088a)"
