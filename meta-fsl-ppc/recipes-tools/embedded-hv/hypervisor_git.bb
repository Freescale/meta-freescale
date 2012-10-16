DESCRIPTION = "Freescale embedded hypervisor"
SECTION = "embedded-hv"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://README;endline=22;md5=0655bbc3b7d7166c30c87208b4e23cf0"

DEPENDS = "u-boot-mkimage-native"

inherit deploy

S = "${WORKDIR}/git"
SRCREV = "${AUTOREV}"

# TODO: fix dtc to use the already built package
SRC_URI = " \
	git://git.freescale.com/ppc/sdk/hypervisor/hypervisor.git;name=hypervisor \
	git://git.freescale.com/ppc/sdk/hypervisor/kconfig.git;name=kconfig;destsuffix=git/kconfig \
	git://git.freescale.com/ppc/sdk/hypervisor/libos.git;name=libos;destsuffix=git/libos \
	git://git.freescale.com/ppc/sdk/hypervisor/mux_server.git;name=mux_server;destsuffix=git/mux_server \
	git://www.jdl.com/software/dtc.git;name=dtc;destsuffix=dtc \
	file://81-fsl-embedded-hv.rules \
	  "

SRCREV_FORMAT="hypervisor"
SRCREV_dtc = "033089f29099bdfd5c2d6986cdb9fd07b16cfde0"

EXTRA_OEMAKE = 'CROSS_COMPILE=${TARGET_PREFIX} CC="${TARGET_PREFIX}gcc ${TOOLCHAIN_OPTIONS}"'

DEFCONFIG = "defconfig"
DEFCONFIG_powerpc64 = "64bit_defconfig"

do_create_link () {
	cd ${S}/..
	if [ ! -e hv ]; then
		ln -s ${S} hv
	fi
}
addtask create_link before do_compile after do_configure

inherit cml1
do_configure () {
	oe_runmake ${DEFCONFIG}
}

do_compile () {
	oe_runmake
	oe_runmake partman
}

do_install () {
	install -d ${D}/${bindir}
	install ${S}/output/bin/linux/partman ${D}/${bindir}/partman

        install -d ${D}${sysconfdir}/udev/rules.d
        install -m 0644 ${WORKDIR}/81-fsl-embedded-hv.rules ${D}${sysconfdir}/udev/rules.d

	install -d ${D}/boot/hv
	install ${S}/output/.config ${D}/boot/hv/hypervisor.config
	install -m 644 ${S}/output/bin/hv ${S}/output/bin/hv.map \
                ${S}/output/bin/hv.uImage ${S}/output/bin/hv.bin \
                        ${D}/boot/hv/
}

do_deploy () {
	install -d ${DEPLOYDIR}/hv/
	install ${S}/output/.config ${DEPLOYDIR}/hv/hypervisor.config
	install -m 644 ${S}/output/bin/hv ${S}/output/bin/hv.map \
                ${S}/output/bin/hv.uImage ${S}/output/bin/hv.bin \
                        ${DEPLOYDIR}/hv/
}
addtask deploy before do_build after do_install

do_deploy_append() {
	rm -f ${S}/../hv
}

ALLOW_EMPTY_${PN} = "1"
PACKAGES_prepend = "${PN}-image ${PN}-partman"
FILES_${PN}-image = "/boot/"
FILES_${PN}-partman = "${bindir}/partman"
