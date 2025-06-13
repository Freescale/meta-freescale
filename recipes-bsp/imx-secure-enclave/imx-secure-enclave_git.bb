# Copyright (C) 2021-2024 NXP

SUMMARY = "NXP i.MX SECURE ENCLAVE library"
DESCRIPTION = "NXP IMX SECURE ENCLAVE library"
SECTION = "base"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=8636bd68fc00cc6a3809b7b58b45f982"

DEPENDS = "mbedtls openssl"

SRC_URI = "git://github.com/NXP/imx-secure-enclave.git;protocol=https;branch=${SRCBRANCH}"
SRCBRANCH = "lf-6.12.3_1.0.0"
SRCREV = "1130c8bb820881ad037ba3f060e7fa70635fae3c"

inherit systemd

S = "${WORKDIR}/git"

PLAT ?= "UNDEFINED"

PLAT:mx8ulp-nxp-bsp = "ele"
PLAT:mx91-nxp-bsp = "ele"
PLAT:mx93-nxp-bsp = "ele"

PLAT:mx8x-nxp-bsp = "seco"
PLAT:mx95-nxp-bsp = "seco"

TARGET_CC_ARCH += "${LDFLAGS}"

EXTRA_OEMAKE = " \
    PLAT=${PLAT} \
    OPENSSL_PATH=${STAGING_INCDIR} \
    MBEDTLS_PATH=${STAGING_DIR_HOST}${datadir}/mbedtls-source \
"
EXTRA_OEMAKE:append:mx8x-nxp-bsp = " \
    COMPATIBLE_MACHINE=mx8dxl-nxp-bsp \
"

SYSTEMD_AUTO_ENABLE = "disable"
SYSTEMD_SERVICE:${PN} = "nvm_daemon.service"
SYSTEMD_SERVICE:${PN}:mx95-nxp-bsp = ""

do_install() {
	oe_runmake -C ${S} DESTDIR=${D} install_tests
}

do_install:append:mx95-nxp-bsp() {
    # Remove common content that is to be installed by imx-secure-enclave
    for i in common hsm nvm.h; do
        rm -rf ${D}${includedir}/$i
    done
    rm ${D}${datadir}/se/README
    rm ${D}${bindir}/nvmd_conf_setup.sh
}

FILES:${PN} += "${datadir}/se"
RDEPENDS:${PN} += "bash"

COMPATIBLE_MACHINE = "(mx8x-nxp-bsp|mx8ulp-nxp-bsp|mx9-nxp-bsp)"
