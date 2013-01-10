require barebox.inc

PR = "r4"

SRC_URI = "http://barebox.org/download/${PN}-${PV}.tar.bz2 \
	file://defconfig \
	"

SRC_URI[md5sum] = "ce876bb3ba1f60b5ee08f13dc648f702"
SRC_URI[sha256sum] = "5cd1fcbd6596d3fad1b25a79706a9a76508e8c0d2a85e79ef2d91754caa6667b"

SRC_URI_append_imx53qsb = " \
	file://config \
	file://0001-add-i2c-clock-support.patch \
	file://0002-mfd-add-mc34708-driver.patch \
	file://0003-i.MX53-add-silicn-revision-functions.patch \
	file://0004-i.MX53-enable-pull-up-on-I2C0-pins.patch \
	file://0005-mx53-loco-add-i2c-support.patch \
	file://0006-mx53-loco-add-board-revision-support.patch \
	file://0007-mx53-loco-update-defconfig.patch \
	file://0001-imx53qsb-default-config-for-OE-s-sdcard-image.patch \
	"

do_configure_prepend_imx53qsb() {
	cp ${WORKDIR}/config ${S}/arch/arm/boards/freescale-mx53-loco/env
	oe_runmake oldconfig
}

COMPATIBLE_MACHINE = "imx53qsb"
