# Canonical DEPENDS for this baremetal recipe.
# nooelint: oelint.vars.dependsappend
DEPENDS = "gcc-arm-none-eabi-native"
INHIBIT_DEFAULT_DEPS = "1"
PROVIDES += "virtual/imx-oei"

inherit deploy

PACKAGECONFIG ??= "\
    ${@bb.utils.contains('UBOOT_CONFIG', 'sd-ecc', 'ecc', '', d)}"

PACKAGECONFIG[ecc] = ""
PACKAGECONFIG[tcm] = ""

PACKAGE_ARCH = "${MACHINE_ARCH}"

OEI_CONFIGS ?= "ddr ${@bb.utils.filter('PACKAGECONFIG', 'tcm', d)}"
OEI_CORE ?= "UNDEFINED"
OEI_SOC ?= "UNDEFINED"
OEI_BOARD ?= "UNDEFINED"
OEI_DDR_CONFIG ?= ""
OEI_DDR_CONFIG_ECC ?= ""
OEI_DEBUG ?= "0"

LDFLAGS[unexport] = "1"

EXTRA_OEMAKE = "\
    board=${OEI_BOARD} \
    DEBUG=${OEI_DEBUG} \
    OEI_CROSS_COMPILE=arm-none-eabi-"

EXTRA_OEMAKE:append:mx95-generic-bsp = " r=${IMX_SOC_REV}"

do_configure() {
    for oei_config in ${OEI_CONFIGS}; do
        oe_runmake clean oei=$oei_config
    done
}

do_compile() {
    # Use the ECC DDR timing when the ecc PACKAGECONFIG is enabled.
    ddr_config="${OEI_DDR_CONFIG}"
    if echo " ${PACKAGECONFIG} " | grep -q " ecc "; then
        ddr_config="${OEI_DDR_CONFIG_ECC}"
    fi
    for oei_config in ${OEI_CONFIGS}; do
        oe_runmake ${ddr_config:+DDR_CONFIG=$ddr_config} oei=$oei_config
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

# Firmware-only package; the single custom path deliberately replaces defaults.
# nooelint: oelint.var.filesoverride
FILES:${PN} = "/firmware"
SYSROOT_DIRS += "/firmware"

COMPATIBLE_MACHINE = "(mx95-generic-bsp|mx943-generic-bsp)"
