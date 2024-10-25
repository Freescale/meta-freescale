# Set generic compiler for system manager core
INHIBIT_DEFAULT_DEPS = "1"
DEPENDS = "${SM_COMPILER}"
SM_COMPILER ?= "gcc-arm-none-eabi-native"
PROVIDES += "virtual/imx-system-manager"

inherit deploy

PACKAGE_ARCH = "${MACHINE_ARCH}"

# Set monitor mode for none, one, or two
PACKAGECONFIG[m0] = "M=0,,,,,m1 m2"
PACKAGECONFIG[m1] = ",,,,,m0 m2"
PACKAGECONFIG[m2] = "M=2,,,,,m0 m1"

SYSTEM_MANAGER_CONFIG ?= "INVALID"

LDFLAGS[unexport] = "1"

EXTRA_OEMAKE = " \
    V=y \
    SM_CROSS_COMPILE=arm-none-eabi- \
    ${PACKAGECONFIG_CONFARGS} \
"

do_configure() {
    oe_runmake config=${SYSTEM_MANAGER_CONFIG} clean
    oe_runmake config=${SYSTEM_MANAGER_CONFIG} cfg
}

do_compile() {
    oe_runmake config=${SYSTEM_MANAGER_CONFIG}
}

do_install[noexec] = "1"

addtask deploy after do_compile
do_deploy() {
    install -D -p -m 0644 \
        ${B}/build/${SYSTEM_MANAGER_CONFIG}/${SYSTEM_MANAGER_FIRMWARE_BASENAME}.bin \
        ${DEPLOYDIR}/${SYSTEM_MANAGER_FIRMWARE_BASENAME}-${SYSTEM_MANAGER_CONFIG}.bin
}

COMPATIBLE_MACHINE = "(mx95-generic-bsp)"
