# Copyright (C) 2017-2026 NXP

DESCRIPTION = "i.MX ARM Trusted Firmware"
SECTION = "BSP"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"

PV .= "+git${SRCPV}"

SRC_URI = "${ATF_SRC};branch=${SRCBRANCH}"
ATF_SRC ?= "git://github.com/nxp-imx/imx-atf.git;protocol=https"
SRCBRANCH = "lf_v2.12"
SRCREV = "4a2e9ef5f9f185bda68470b46365add008903b8c"

inherit deploy

PACKAGECONFIG ??= " \
    ${@bb.utils.filter('UBOOT_CONFIG', 'crrm', d)} \
    ${@bb.utils.filter('MACHINE_FEATURES', 'optee', d)}"

PACKAGECONFIG[crrm] = "IMX_CRRM=1"
PACKAGECONFIG[debug] = "DEBUG=1,DEBUG=0"
PACKAGECONFIG[optee] = "SPD=opteed"

ATF_PLATFORM ??= "INVALID"

# FIXME: We should return INVALID here but currently only i.MX8M has support to override the UART
# base address in source code.
ATF_BOOT_UART_BASE ?= ""

EXTRA_OEMAKE += " \
    CROSS_COMPILE=${TARGET_PREFIX} \
    PLAT=${ATF_PLATFORM} \
    ${PACKAGECONFIG_CONFARGS} \
    bl31 \
"
# Let the Makefile handle setting up the CFLAGS and LDFLAGS as it is a standalone application
CFLAGS[unexport] = "1"
LDFLAGS[unexport] = "1"
AS[unexport] = "1"
LD[unexport] = "1"

# Baremetal, just need a compiler
INHIBIT_DEFAULT_DEPS = "1"
DEPENDS = "virtual/cross-cc"

# Bring in clang compiler if using clang as default
DEPENDS:append:toolchain-clang = " clang-cross-${TARGET_ARCH}"

# CC and LD introduce arguments which conflict with those otherwise provided by
# this recipe. The heads of these variables excluding those arguments
# are therefore used instead.
def remove_options_tail (in_string):
    from itertools import takewhile
    return ' '.join(takewhile(lambda x: not x.startswith('-'), in_string.split(' ')))

# LD can have linker suffix in its name e.g. aarch64-yoe-linux-ld.lld so we need to
# drop .lld as well along with options from LD
EXTRA_OEMAKE += 'LD="${HOST_PREFIX}ld.bfd"'

EXTRA_OEMAKE += 'CC="${@remove_options_tail(d.getVar('CC'))}"'

# Set the UART to use during the boot.
EXTRA_OEMAKE += 'IMX_BOOT_UART_BASE=${ATF_BOOT_UART_BASE}'

do_configure[noexec] = "1"

do_install[noexec] = "1"

ANNOTATED_NAME        = "bl31-${ATF_PLATFORM}.bin"
ANNOTATED_NAME:append = "${@bb.utils.contains('PACKAGECONFIG',  'crrm',  '-crrm', '', d)}"
ANNOTATED_NAME:append = "${@bb.utils.contains('PACKAGECONFIG', 'optee', '-optee', '', d)}"

addtask deploy after do_compile
do_deploy() {
    OUTPUT_FOLDER="${@bb.utils.contains('PACKAGECONFIG', 'debug', 'debug', 'release', d)}"
    for deploydir in ${DEPLOYDIR} ${DEPLOYDIR}/imx-boot-tools; do
        install -Dm 0644 ${S}/build/${ATF_PLATFORM}/${OUTPUT_FOLDER}/bl31.bin $deploydir/${ANNOTATED_NAME}
    done
    ln -sf ${ANNOTATED_NAME} ${DEPLOYDIR}/bl31.bin
}

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mx8-generic-bsp|mx9-generic-bsp)"
