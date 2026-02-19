# Copyright 2020-23, 2025 NXP
require recipes-security/optee-imx/optee-fslc.inc

SUMMARY = "NXP i.MX Security Middleware Library"
DESCRIPTION = "NXP i.MX Security Middleware Library"
SECTION = "base"
LICENSE = "BSD-3-Clause"
LICENSE = "Apache-2.0 & BSD-3-Clause & Zlib"
LIC_FILES_CHKSUM = "file://LICENSE;md5=6087d19da5441648e33f85ae64cf2a7d \
                    file://psa-arch-tests/LICENSE.md;md5=2a944942e1496af1886903d274dedb13"

DEPENDS = " \
    python3-cryptography-native \
    json-c \
    optee-client \
    optee-os-tadevkit \
    sqlite3 \
"

SMW_LIB_SRC ?= "git://github.com/nxp-imx/imx-smw.git;protocol=https"
PSA_LIB_SRC ?= "git://github.com/ARM-software/psa-arch-tests.git;protocol=https"
PSA_ARCH_TESTS_SRC_PATH = "psa-arch-tests"
SRCBRANCH_smw = "release/version_5.x"
SRCBRANCH_psa = "main"

SRC_URI = "${SMW_LIB_SRC};branch=${SRCBRANCH_smw};name=smw \
           ${PSA_LIB_SRC};branch=${SRCBRANCH_psa};name=psa;destsuffix=${S}/${PSA_ARCH_TESTS_SRC_PATH} \
           "
SRCREV_smw = "8934fdecacb88b227d09690283e7e2578387b7ee"
SRCREV_psa = "463cb95ada820bc6f758d50066cf8c0ed5cc3a02"
SRCREV_FORMAT = "smw_psa"

inherit cmake python3native

PACKAGECONFIG ??= "${PACKAGECONFIG_DRIVERS} ${PACKAGECONFIG_FEATURES}"
PACKAGECONFIG_DRIVERS                = ""
PACKAGECONFIG_DRIVERS:mx8x-nxp-bsp   = "ele-seco"
PACKAGECONFIG_DRIVERS:mx8ulp-nxp-bsp = "ele"
PACKAGECONFIG_DRIVERS:mx91-nxp-bsp   = "ele"
PACKAGECONFIG_DRIVERS:mx93-nxp-bsp   = "ele"
PACKAGECONFIG_DRIVERS:mx943-nxp-bsp  = "ele"
PACKAGECONFIG_DRIVERS:mx95-nxp-bsp   = "ele"

PACKAGECONFIG_FEATURES              = ""
PACKAGECONFIG_FEATURES:mx91-nxp-bsp = "tls"
PACKAGECONFIG_FEATURES:mx93-nxp-bsp = "tls"
PACKAGECONFIG_FEATURES:mx943-nxp-bsp = "tls"

PACKAGECONFIG[ele] = "-DELE_ROOT=${STAGING_DIR_HOST},,imx-secure-enclave,,,ele-seco"
PACKAGECONFIG[ele-seco] = "-DSECO_ROOT=${STAGING_DIR_HOST},,imx-secure-enclave-seco,,,ele"
PACKAGECONFIG[tls] = "-DENABLE_TLS=ON,-DENABLE_TLS=OFF,openssl"

CFLAGS[unexport] = "1"
CPPFLAGS[unexport] = "1"
AS[unexport] = "1"
LD[unexport] = "1"

# setting the linker options
TARGET_LDFLAGS:remove = "${DEBUG_PREFIX_MAP}"

EXTRA_OECMAKE = " \
    -DTA_DEV_KIT_ROOT=${TA_DEV_KIT_DIR} \
    -DTEEC_ROOT=${STAGING_DIR_HOST} \
    -DJSONC_ROOT="${COMPONENTS_DIR}/${TARGET_ARCH}/json-c/usr" \
    -DPSA_ARCH_TESTS_SRC_PATH=${PSA_ARCH_TESTS_SRC_PATH} \
    -DTEE_TA_DESTDIR=${nonarch_base_libdir} \
"

OECMAKE_TARGET_COMPILE += "build_tests"
OECMAKE_TARGET_INSTALL += "install_tests"

PACKAGES =+ "${PN}-tests"

FILES:${PN} += "${nonarch_base_libdir}/optee_armtz/*"

FILES:${PN}-tests = "${bindir}/* ${datadir}/${BPN}/*"

# Work around do_package_qa QA errors
INSANE_SKIP:${PN}-dbg += "buildpaths"
INSANE_SKIP:${PN}-dev += "buildpaths"
INSANE_SKIP:${PN}-tests += "buildpaths"

RDEPENDS:${PN}-tests += "bash cmake"

PACKAGE_ARCH = "${MACHINE_SOCARCH}"

COMPATIBLE_MACHINE = "(imx-nxp-bsp)"
