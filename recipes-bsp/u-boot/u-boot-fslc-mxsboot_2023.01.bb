require u-boot-fslc-common_${PV}.inc

DESCRIPTION = "U-boot bootloader mxsboot tool"
SECTION = "bootloader"

DEPENDS = "swig-native bison-native gnutls-native dtc openssl"

PROVIDES = "u-boot-mxsboot"

EXTRA_OEMAKE:class-target = 'CROSS_COMPILE="${TARGET_PREFIX}" CC="${CC} ${CFLAGS} ${LDFLAGS}" HOSTCC="${BUILD_CC} ${BUILD_CFLAGS} ${BUILD_LDFLAGS}" STRIP=true V=1 CONFIG_MX28=y'
EXTRA_OEMAKE:class-native = 'CC="${BUILD_CC} ${BUILD_CFLAGS} ${BUILD_LDFLAGS}" HOSTCC="${BUILD_CC} ${BUILD_CFLAGS} ${BUILD_LDFLAGS}" STRIP=true V=1 CONFIG_MX28=y'
EXTRA_OEMAKE:class-nativesdk = 'CROSS_COMPILE="${HOST_PREFIX}" CC="${CC} ${CFLAGS} ${LDFLAGS}" HOSTCC="${BUILD_CC} ${BUILD_CFLAGS} ${BUILD_LDFLAGS}" STRIP=true V=1 CONFIG_MX28=y'

do_compile () {
    oe_runmake -C ${S} O=${B} sandbox_defconfig

    # Disable CONFIG_CMD_LICENSE, license.h is not used by tools and
    # generating it requires bin2header tool, which for target build
    # is built with target tools and thus cannot be executed on host.
    sed -i "s/CONFIG_CMD_LICENSE=.*/# CONFIG_CMD_LICENSE is not set/" .config

    oe_runmake -C ${S} O=${B} cross_tools NO_SDL=1
}

do_install () {
    install -Dm 0755 tools/mxsboot ${D}${bindir}/uboot-mxsboot
    ln -sf uboot-mxsboot ${D}${bindir}/mxsboot
}

COMPATIBLE_MACHINE:class-target = "(mxs-generic-bsp)"

BBCLASSEXTEND = "native nativesdk"
