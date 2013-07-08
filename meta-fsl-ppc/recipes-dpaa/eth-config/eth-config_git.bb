DESCRIPTION = "Ethernet Configuration Files"
SECTION = "eth-config"
LICENSE = "Freescale-EULA"
LIC_FILES_CHKSUM = "file://COPYING;md5=cf02dc8eb5ac4a76f3812826520dea87"

PR = "r2"

SRC_URI = "git://git.freescale.com/ppc/sdk/eth-config.git"
SRCREV = "c255231fb606bff18390da3b26e1ee9fca55e4e6"

S = "${WORKDIR}/git"

do_install() {
	install -d ${D}/etc/fmc/config
	install -m 644 ${S}/*.xml ${D}/etc/fmc/config
	install -d ${D}/etc/fmc/config/shared_mac
	install -m 644 ${S}/shared_mac/*.xml ${D}/etc/fmc/config/shared_mac
	install -m 644 ${S}/shared_mac/README ${D}/etc/fmc/config/shared_mac
}
