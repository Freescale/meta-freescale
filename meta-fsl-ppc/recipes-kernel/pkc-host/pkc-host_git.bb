DESCRIPTION = "pkc host driver"
SECTION = "pkc-host"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://Makefile;endline=30;md5=6a26ed8e76a8ea2e019c525369ed0f03"

inherit  module qoriq_build_64bit_kernel
RDEPENDS_${PN} += "cryptodev-module bc"

# Currently pkc-host does not support RSA_KEYGEN, remove this
# if it is fixed.
REQUIRED_DISTRO_FEATURES = "c29x_pkc"

SRC_URI = "git://git.freescale.com/ppc/sdk/pkc-host.git;nobranch=1"
SRCREV = "564f535d596f43eb2901a7ff77bbe529a118c16e"

S = "${WORKDIR}/git"

EXTRA_OEMAKE='KERNEL_DIR="${STAGING_KERNEL_DIR}" PREFIX="${D}"'

do_install() {
        oe_runmake INSTALL_MOD_PATH="${D}" modules_install
        install -d ${D}/etc/crypto
        install -d ${D}/${bindir}
        cp ${S}/crypto.cfg ${D}/etc/crypto
        cp ${S}/images/pkc-firmware.bin ${D}/etc/crypto
        cp ${S}/apps/cli/cli ${D}/${bindir}
        cp ${S}/perf/c29x_driver_perf_profile.sh ${D}/${bindir}
}


FILES_${PN} = "${bindir}/cli \
    ${bindir}/c29x_driver_perf_profile.sh \
    /etc/crypto/crypto.cfg \
    /etc/crypto/pkc-firmware.bin \
"
