# Copyright (C) 2017-2019 NXP

DESCRIPTION = "i.MX ARM Trusted Firmware"
SECTION = "BSP"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"

PV .= "+git${SRCPV}"

SRCBRANCH = "imx_4.14.98_2.0.0_ga"
SRC_URI = "git://source.codeaurora.org/external/imx/imx-atf.git;protocol=https;branch=${SRCBRANCH} \
           file://0001-Allow-BUILD_STRING-to-be-set-in-.revision-file.patch \
"
SRCREV = "1cb68fa0a0dd8bc00b9871b51d4c4e1d0a827b2d"

S = "${WORKDIR}/git"

inherit deploy

BOOT_TOOLS = "imx-boot-tools"

PLATFORM        ?= "INVALID"
PLATFORM_mx8qm   = "imx8qm"
PLATFORM_mx8qxp  = "imx8qx"
PLATFORM_mx8mq   = "imx8mq"
PLATFORM_mx8mm   = "imx8mm"

EXTRA_OEMAKE += " \
    CROSS_COMPILE="${TARGET_PREFIX}" \
    PLAT=${PLATFORM} \
"

BUILD_OPTEE = "${@bb.utils.contains('MACHINE_FEATURES', 'optee', 'true', 'false', d)}"

do_compile() {
    # Clear LDFLAGS to avoid the option -Wl recognize issue
    unset LDFLAGS
    oe_runmake bl31
    if ${BUILD_OPTEE}; then
       oe_runmake clean BUILD_BASE=build-optee
       oe_runmake BUILD_BASE=build-optee SPD=opteed bl31
    fi
}

do_install[noexec] = "1"

do_deploy() {
    install -Dm 0644 ${S}/build/${PLATFORM}/release/bl31.bin ${DEPLOYDIR}/${BOOT_TOOLS}/bl31-${PLATFORM}.bin
    if ${BUILD_OPTEE}; then
       install -m 0644 ${S}/build-optee/${PLATFORM}/release/bl31.bin ${DEPLOYDIR}/${BOOT_TOOLS}/bl31-${PLATFORM}.bin-optee
    fi
}
addtask deploy after do_compile

PACKAGE_ARCH = "${MACHINE_SOCARCH}"
COMPATIBLE_MACHINE = "(mx8)"
