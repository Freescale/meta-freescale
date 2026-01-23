LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"
LINUX_VERSION = "6.12.49"

LINUX_QORIQ_BRANCH ?= "lf-6.12.y"
LINUX_QORIQ_SRC ?= "git://github.com/nxp-qoriq/linux.git;protocol=https"
SRC_URI = "${LINUX_QORIQ_SRC};branch=${LINUX_QORIQ_BRANCH}"
SRCREV = "df24f9428e38740256a410b983003a478e72a7c0"

require linux-qoriq.inc
