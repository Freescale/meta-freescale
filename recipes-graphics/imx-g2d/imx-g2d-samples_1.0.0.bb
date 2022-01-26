SUMMARY = "i.MX G2D Samples"
DESCRIPTION = "Set of sample applications for i.MX G2D"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://COPYING;md5=0858ec9c7a80c4a2cf16e4f825a2cc91"

# This package is currently only for GPU-based G2D. Support for DPU-based G2d is coming.
DEPENDS = "imx-gpu-g2d"

GPU_G2D_SAMPLES_SRC ?= "git://github.com/nxpmicro/g2d-samples.git;protocol=https"
SRCBRANCH ?= "imx_1.0"
SRC_URI = "${GPU_G2D_SAMPLES_SRC};branch=${SRCBRANCH}"
SRCREV = "daf64d010666ef2458566573c074e238993f228c"

S = "${WORKDIR}/git"

do_configure[noexec] = "1"

do_install() {
    oe_runmake install DESTDIR=${D} PREFIX=${exec_prefix}
}

FILES:${PN} += "/opt"
