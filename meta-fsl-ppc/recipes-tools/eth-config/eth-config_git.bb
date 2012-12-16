DESCRIPTION = "Ethernet Configuration Files"
SECTION = "eth-config"
LICENSE = "Freescale EULA"
LIC_FILES_CHKSUM = "file://COPYING;md5=cf02dc8eb5ac4a76f3812826520dea87"

SRC_URI = "git://git.freescale.com/ppc/sdk/eth-config.git"
SRCREV = "847191454aa557d19c19fdcd9e0a145da5d43c49"
SRCREV_e6500 = "c1400695e56aa80f2f4c88c4e0582028248ae06f"
SRCREV_e6500-64b = "c1400695e56aa80f2f4c88c4e0582028248ae06f"

S = "${WORKDIR}/git"

do_install() {
	install -d ${D}/etc/fmc/config
	install -m 644 ${S}/8c-128fq-p.xml ${D}/etc/fmc/config
	install -m 644 ${S}/cfg-8c.xml ${D}/etc/fmc/config
	install -d ${D}/etc/fmc/config/shared_mac
	install -m 644 ${S}/shared_mac/hv2p_config_p4_shared_mac.xml ${D}/etc/fmc/config/shared_mac
	install -m 644 ${S}/shared_mac/hv2p_policy_shared_mac.xml ${D}/etc/fmc/config/shared_mac
	install -m 644 ${S}/shared_mac/hv2p_swparser_shared_mac.xml ${D}/etc/fmc/config/shared_mac
	install -m 644 ${S}/shared_mac/README ${D}/etc/fmc/config/shared_mac
}
