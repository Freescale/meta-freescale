# Copyright 2021-2025 NXP

SUMMARY = "NXP i.MX SECURE ENCLAVE library"
DESCRIPTION = "Library providing access to the NXP i.MX Secure Enclave \
               (EdgeLock Enclave) on-chip security subsystem, exposing its \
               cryptographic and key-management services to userspace."
HOMEPAGE = "https://github.com/NXP/imx-secure-enclave"
SECTION = "base"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=8636bd68fc00cc6a3809b7b58b45f982"

# Canonical DEPENDS for the library; this .inc is the base definition, so the
# plain assignment is intentional (the require sits at the top of the .bb).
# nooelint: oelint.vars.dependsappend
DEPENDS = "mbedtls openssl"

SRC_URI = "${SECURE_ENCLAVE_LIB_SRC};branch=${SRCBRANCH}"
SECURE_ENCLAVE_LIB_SRC ?= "git://github.com/NXP/imx-secure-enclave.git;protocol=https"
SRCBRANCH = "lf-6.18.37_2.1.0"
SRCREV = "463845060a79dd9a6d39db3b3d8dff60225b9dfd"

PLAT ?= "UNDEFINED"

EXTRA_OEMAKE = "PLAT=${PLAT} OPENSSL_PATH=${STAGING_INCDIR} MBEDTLS_PATH=${STAGING_DIR_HOST}${datadir}/mbedtls-source"

TARGET_CC_ARCH += "${LDFLAGS}"

do_install() {
    oe_runmake -C ${S} DESTDIR=${D} install_tests
}

PACKAGE_ARCH = "${MACHINE_SOCARCH}"

FILES:${PN} += "${datadir}/se"
RDEPENDS:${PN}:append = " bash"
