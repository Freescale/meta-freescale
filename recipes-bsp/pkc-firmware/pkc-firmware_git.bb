DESCRIPTION = "U-boot firmware for c293pcie support "
HOMEPAGE = "http://u-boot.sf.net"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=1707d6db1d42237583f50183a5651ecb"

INHIBIT_DEFAULT_DEPS = "1"
DEPENDS = "virtual/${TARGET_PREFIX}gcc libgcc"

inherit deploy

PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = "git://git.freescale.com/ppc/sdk/pkc-firmware.git;branch=sdk-v2.0.x"
SRCREV = "f75d281d83e74758f54d85bacfa74b7c07514413"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = 'CROSS_COMPILE=${TARGET_PREFIX} CC="${TARGET_PREFIX}gcc ${TOOLCHAIN_OPTIONS}"'

do_compile () {
    unset LDFLAGS
    unset CFLAGS
    unset CPPFLAGS
    oe_runmake C293QDS_36BIT_SDCARD
}

do_install(){
    install -d ${D}${sysconfdir}/crypto/
    install ${S}/u-boot.bin ${D}${sysconfdir}/crypto/pkc-firmware.bin
}

do_deploy(){
    install -d ${DEPLOYDIR}/pkc-firmware
    install ${S}/u-boot.bin ${DEPLOYDIR}/pkc-firmware/pkc-firmware.bin
}

addtask deploy after do_install

FILES_{PN} += "/etc/crypto/pkc-firmware.bin"
COMPATIBLE_MACHINE = "(c293pcie)"
CLEANBROKEN = "1"
