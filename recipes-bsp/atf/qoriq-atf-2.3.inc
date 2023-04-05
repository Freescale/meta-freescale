LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://license.rst;md5=1dd070c98a281d18d9eefd938729b031"

PV = "2.3+git${SRCPV}"

SRC_URI = "git://github.com/nxp-qoriq/atf;nobranch=1 \
           git://github.com/ARMmbed/mbedtls;nobranch=1;destsuffix=git/mbedtls;name=mbedtls \
"
SRCREV = "4e40e24590ab908773ef842cd0e17faf233767d4"
SRCREV_mbedtls = "85da85555e5b086b0250780693c3ee584f63e79f"
SRCREV_FORMAT = "atf"

S = "${WORKDIR}/git"
