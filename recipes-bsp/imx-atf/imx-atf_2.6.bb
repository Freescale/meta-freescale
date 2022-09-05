# Copyright (C) 2017-2022 NXP

DESCRIPTION = "i.MX ARM Trusted Firmware"
SECTION = "BSP"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"

PV .= "+git${SRCPV}"

SRC_URI = "git://source.codeaurora.org/external/imx/imx-atf.git;protocol=https;branch=${SRCBRANCH} \
           file://0001-Makefile-Suppress-array-bounds-error.patch"
SRCBRANCH = "lf_v2.6"
SRCREV = "c6a19b1a351308cc73443283f6aa56b2eff791b8"

S = "${WORKDIR}/git"

inherit deploy

ATF_PLATFORM ??= "INVALID"

# FIXME: We should return INVALID here but currently only i.MX8M has support to override the UART
# base address in source code.
ATF_BOOT_UART_BASE ?= ""

EXTRA_OEMAKE += " \
    CROSS_COMPILE="${TARGET_PREFIX}" \
    PLAT=${ATF_PLATFORM} \
"

# Let the Makefile handle setting up the CFLAGS and LDFLAGS as it is a standalone application
CFLAGS[unexport] = "1"
LDFLAGS[unexport] = "1"
AS[unexport] = "1"
LD[unexport] = "1"

# Baremetal, just need a compiler
INHIBIT_DEFAULT_DEPS = "1"
DEPENDS = "virtual/${HOST_PREFIX}gcc"

# Bring in clang compiler if using clang as default
DEPENDS:append:toolchain-clang = " clang-cross-${TARGET_ARCH}"

BUILD_OPTEE = "${@bb.utils.contains('MACHINE_FEATURES', 'optee', 'true', 'false', d)}"

# CC and LD introduce arguments which conflict with those otherwise provided by
# this recipe. The heads of these variables excluding those arguments
# are therefore used instead.
def remove_options_tail (in_string):
    from itertools import takewhile
    return ' '.join(takewhile(lambda x: not x.startswith('-'), in_string.split(' ')))

EXTRA_OEMAKE += 'LD="${@remove_options_tail(d.getVar('LD'))}"'

EXTRA_OEMAKE += 'CC="${@remove_options_tail(d.getVar('CC'))}"'

# Set the UART to use during the boot.
EXTRA_OEMAKE += 'IMX_BOOT_UART_BASE=${ATF_BOOT_UART_BASE}'

do_configure[noexec] = "1"

do_compile() {
    # Clear LDFLAGS to avoid the option -Wl recognize issue
    oe_runmake bl31
    if ${BUILD_OPTEE}; then
        oe_runmake clean BUILD_BASE=build-optee
        oe_runmake BUILD_BASE=build-optee SPD=opteed bl31
    fi
}

do_install[noexec] = "1"

addtask deploy after do_compile
do_deploy() {
    install -Dm 0644 ${S}/build/${ATF_PLATFORM}/release/bl31.bin ${DEPLOYDIR}/bl31-${ATF_PLATFORM}.bin
    if ${BUILD_OPTEE}; then
        install -m 0644 ${S}/build-optee/${ATF_PLATFORM}/release/bl31.bin ${DEPLOYDIR}/bl31-${ATF_PLATFORM}.bin-optee
    fi
}

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mx8-generic-bsp)"
