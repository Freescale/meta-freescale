SUMMARY = "Open Data Plane Interface Implementation"
DESCRIPTION = "OpenDataPlane (ODP) provides a data plane application programming \
        environment that is easy to use, high performance, and portable between networking SoCs."
HOMEPAGE = "http://www.opendataplane.org"
SECTION = "console/network"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ca6103dc75397fb6bec596187d6b7829"

FILESEXTRAPATHS_prepend := "${THISDIR}/odp:"

SRC_URI = " \
git://source.codeaurora.org/external/qoriq/qoriq-components/odp;nobranch=1 \
git://source.codeaurora.org/external/qoriq/qoriq-components/qbman_userspace;nobranch=1;name=qbman;destsuffix=git/platform/linux-dpaa2/flib/qbman \
git://source.codeaurora.org/external/qoriq/qoriq-components/flib;nobranch=1;name=rta;destsuffix=git/platform/linux-dpaa2/flib/rta \
"

SRC_URI += "file://0001-Fix-this-build-error.patch"

SRCREV = "bef6398a09db4a6b909aa0c6accc7c15621ce5ef"

SRCREV_qbman = "097595befc2bb60b733027e94ad2ed16a0e9cae0"
SRCREV_rta = "bbab28b03ae21c52ce913f8b052acf53bd24ff5d"

S = "${WORKDIR}/git"

COMPATIBLE_MACHINE = "(ls1043a|ls1046a|ls2080a|ls2088a|ls1088a)"
