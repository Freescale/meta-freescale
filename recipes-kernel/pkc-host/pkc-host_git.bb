DESCRIPTION = "pkc host driver"
SECTION = "pkc-host"
LICENSE = "BSD & GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=99803d8e9a595c0bdb45ca710f353813"

inherit  module qoriq_build_64bit_kernel
RDEPENDS_${PN} += "cryptodev-module bc"

# Currently pkc-host does not support RSA_KEYGEN, remove this
# if it is fixed.
REQUIRED_DISTRO_FEATURES = "c29x_pkc"

SRC_URI = "git://git.freescale.com/ppc/sdk/pkc-host.git;branch=sdk-v2.0.x"
SRCREV = "e021464d700b3259d7114fb6e5e54a9f9c3ee326"

S = "${WORKDIR}/git"

EXTRA_OEMAKE='KERNEL_DIR="${STAGING_KERNEL_DIR}" PREFIX="${D}"'

do_compile_prepend() {
    sed -i 's#-Werror##g' ${S}/Makefile
}

do_install() {
    oe_runmake INSTALL_MOD_PATH="${D}" modules_install
    install -d ${D}/etc/crypto
    install -d ${D}/${bindir}
    cp ${S}/crypto.cfg ${D}/etc/crypto
    cp ${S}/images/pkc-firmware.bin ${D}/etc/crypto
    cp ${S}/perf/c29x_driver_perf_profile.sh ${D}/${bindir}
}


FILES_${PN} = "${bindir}/cli \
    ${bindir}/c29x_driver_perf_profile.sh \
    /etc/crypto/crypto.cfg \
    /etc/crypto/pkc-firmware.bin \
"

COMPATIBLE_MACHINE = "(qoriq-ppc)"
PACKAGE_ARCH = "${MACHINE_SOCARCH}"

