DESCRIPTION = "udev rules for Freescale i.MX SOCs"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690"

SRC_URI = " file://10-imx.rules"

do_install () {
	install -d ${D}${sysconfdir}/udev/rules.d
	install -m 0644 ${WORKDIR}/10-imx.rules ${D}${sysconfdir}/udev/rules.d/
}