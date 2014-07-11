DESCRIPTION = "pkc host driver"
SECTION = "pkc-host"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://Makefile;endline=30;md5=6a26ed8e76a8ea2e019c525369ed0f03"

inherit  module qoriq_build_64bit_kernel

SRC_URI = "git://git.freescale.com/ppc/sdk/pkc-host.git;nobranch=1"
SRCREV = "cae512c94e2a26cc6b0d6393d307cdea2d7282c9"

S = "${WORKDIR}/git"

EXTRA_OEMAKE='KERNEL_DIR="${STAGING_KERNEL_DIR}" PREFIX="${D}"'

do_install() {
        install -d ${D}/lib/modules/c2x0
        install -d ${D}/etc/crypto
        install -d ${D}/${bindir}
        cp ${S}/*.ko ${D}/lib/modules/c2x0
        cp ${S}/crypto.cfg ${D}/etc/crypto
        cp ${S}/images/pkc-firmware.bin ${D}/etc/crypto
        cp ${S}/perf/mini_calc/mini_calc ${D}/${bindir}
        cp ${S}/apps/cli/cli ${D}/${bindir}
        cp ${S}/perf/c29x_driver_perf_profile.sh ${D}/${bindir}
}


FILES_${PN} += "${bindir}/mini_calc ${bindir}/cli ${bindir}/c29x_driver_perf_profile.sh /etc/crypto/crypto.cfg /etc/crypto/pkc-firmware.bin"
