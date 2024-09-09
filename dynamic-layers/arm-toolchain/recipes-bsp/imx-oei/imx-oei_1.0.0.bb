SUMMARY = "i.MX Optional Execution Image"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=59530bdf33659b29e73d4adb9f9f6552"

INHIBIT_DEFAULT_DEPS = "1"
DEPENDS = "gcc-arm-none-eabi-native"

SRC_URI = "${IMX_OEI_SRC};branch=${SRCBRANCH}"
IMX_OEI_SRC ?= "git://github.com/nxp-imx/imx-oei.git;protocol=https"
SRCBRANCH = "master"
SRCREV = "1a572a640ef8d6883e8ca39744cd6d2d5dbed678"

S = "${WORKDIR}/git"

inherit deploy

OEI_CONFIGS ?= "UNDEFINED"
OEI_CORE    ?= "UNDEFINED"
OEI_SOC     ?= "UNDEFINED"
OEI_BOARD   ?= "UNDEFINED"

LDFLAGS[unexport] = "1"

EXTRA_OEMAKE = "\
    board=${OEI_BOARD} \
    DEBUG=1 \
    OEI_CROSS_COMPILE=arm-none-eabi-"

do_configure() {
    for oei_config in ${OEI_CONFIGS}; do
        oe_runmake clean oei=$oei_config
    done
}

do_compile() {
    for oei_config in ${OEI_CONFIGS}; do
        oe_runmake oei=$oei_config
    done
}

do_install() {
    install -d ${D}/firmware
    for oei_config in ${OEI_CONFIGS}; do
        install -m 0644 ${B}/build/${OEI_BOARD}/$oei_config/oei-*.bin ${D}/firmware
    done
}

addtask deploy after do_install
do_deploy() {
    cp -rf ${D}/firmware/* ${DEPLOYDIR}/
}

FILES:${PN} = "/firmware"
SYSROOT_DIRS += "/firmware"

COMPATIBLE_MACHINE = "(mx95-generic-bsp)"
