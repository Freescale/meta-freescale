# Copyright 2017-2018 NXP

DESCRIPTION = "i.MX ARM Trusted Firmware"
SECTION = "BSP"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"

PV = "1.5.0+git${SRCPV}"

SRCBRANCH = "imx_4.9.123_imx8mm_ga"
SRC_URI = "git://source.codeaurora.org/external/imx/imx-atf.git;protocol=https;branch=${SRCBRANCH} \
           file://0001-Allow-BUILD_STRING-to-be-set-in-.revision-file.patch \
"
SRCREV = "af3554fc6ea8c17710ffd37a14a25834fad19271"

S = "${WORKDIR}/git"

inherit deploy

BOOT_TOOLS = "imx-boot-tools"

PLATFORM        ?= "INVALID"
PLATFORM_mx8qm   = "imx8qm"
PLATFORM_mx8qxp  = "imx8qxp"
PLATFORM_mx8mq   = "imx8mq"
PLATFORM_mx8mm   = "imx8mm"

EXTRA_OEMAKE += " \
    CROSS_COMPILE="${TARGET_PREFIX}" \
    PLAT=${PLATFORM} \
"

do_compile() {
    # Clear LDFLAGS to avoid the option -Wl recognize issue
    unset LDFLAGS
    oe_runmake bl31
}

do_install[noexec] = "1"

do_deploy() {
    install -Dm 0644 ${S}/build/${PLATFORM}/release/bl31.bin ${DEPLOYDIR}/${BOOT_TOOLS}/bl31-${PLATFORM}.bin
}
addtask deploy after do_compile

PACKAGE_ARCH = "${MACHINE_SOCARCH}"
COMPATIBLE_MACHINE = "(mx8)"
