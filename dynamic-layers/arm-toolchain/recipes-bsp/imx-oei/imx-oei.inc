INHIBIT_DEFAULT_DEPS = "1"
DEPENDS = "gcc-arm-none-eabi-native"

inherit deploy

PACKAGECONFIG ??= " \
    ${@bb.utils.contains('UBOOT_CONFIG', 'sd-ecc', 'ecc', '', d)}"

PACKAGECONFIG[ecc] = ""
PACKAGECONFIG[tcm] = ""

PACKAGE_ARCH = "${MACHINE_ARCH}"

OEI_CORE    ?= "UNDEFINED"
OEI_SOC     ?= "UNDEFINED"
OEI_BOARD   ?= "UNDEFINED"
OEI_CONFIGS ?= "ddr ${@bb.utils.filter('PACKAGECONFIG', 'tcm', d)}"

LDFLAGS[unexport] = "1"

EXTRA_OEMAKE = "\
    board=${OEI_BOARD} \
    DEBUG=1 \
    OEI_CROSS_COMPILE=arm-none-eabi-"

EXTRA_OEMAKE:append:mx95-nxp-bsp = " r=${IMX_SOC_REV}"

do_configure() {
    if [ "${@bb.utils.filter('PACKAGECONFIG', 'ecc', d)}" ]; then
        ddr_config=${OEI_DDR_CONFIG_ECC}
    else
        ddr_config=${OEI_DDR_CONFIG}
    fi
    for oei_config in ${OEI_CONFIGS}; do
        oe_runmake clean oei=$oei_config DDR_CONFIG=$ddr_config
    done
}

do_compile() {
    if [ "${@bb.utils.filter('PACKAGECONFIG', 'ecc', d)}" ]; then
        ddr_config=${OEI_DDR_CONFIG_ECC}
    else
        ddr_config=${OEI_DDR_CONFIG}
    fi
    for oei_config in ${OEI_CONFIGS}; do
        oe_runmake oei=$oei_config DDR_CONFIG=$ddr_config
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

PROVIDES += "virtual/imx-oei"

COMPATIBLE_MACHINE = "(mx95-generic-bsp|mx943-generic-bsp)"
