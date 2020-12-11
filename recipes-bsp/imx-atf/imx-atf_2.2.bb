# Copyright (C) 2017-2020 NXP

DESCRIPTION = "i.MX ARM Trusted Firmware"
SECTION = "BSP"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"

PV .= "+git${SRCPV}"

SRCBRANCH = "imx_5.4.47_2.2.0"
SRC_URI = "git://source.codeaurora.org/external/imx/imx-atf.git;protocol=https;branch=${SRCBRANCH} \
           file://0001-imx-Fix-missing-inclusion-of-cdefs.h.patch \
           file://0001-imx-Fix-multiple-definition-of-ipc_handle.patch \
"
SRCREV = "c949a888e909811db191500c51456391dff61284"

S = "${WORKDIR}/git"

inherit deploy

BOOT_TOOLS = "imx-boot-tools"

ATF_PLATFORM ??= "INVALID"
ATF_PLATFORM_mx8qm   = "imx8qm"
ATF_PLATFORM_mx8x    = "imx8qx"
ATF_PLATFORM_mx8mq   = "imx8mq"
ATF_PLATFORM_mx8dx   = "imx8dx"
ATF_PLATFORM_imx8dxlevk = "imx8dxl"
ATF_PLATFORM_mx8dxlevk-phantom = "imx8qx"

EXTRA_OEMAKE += " \
    CROSS_COMPILE="${TARGET_PREFIX}" \
    PLAT=${ATF_PLATFORM} \
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
    install -Dm 0644 ${S}/build/${ATF_PLATFORM}/release/bl31.bin ${DEPLOYDIR}/${BOOT_TOOLS}/bl31-${ATF_PLATFORM}.bin
    if ${BUILD_OPTEE}; then
       install -m 0644 ${S}/build-optee/${ATF_PLATFORM}/release/bl31.bin ${DEPLOYDIR}/${BOOT_TOOLS}/bl31-${ATF_PLATFORM}.bin-optee
    fi
}
addtask deploy after do_compile

PACKAGE_ARCH = "${MACHINE_SOCARCH}"
COMPATIBLE_MACHINE = "(mx8|use-mainline-bsp)"
