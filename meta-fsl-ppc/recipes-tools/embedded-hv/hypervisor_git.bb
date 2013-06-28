DESCRIPTION = "Freescale embedded hypervisor"
SECTION = "embedded-hv"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://README;endline=22;md5=0655bbc3b7d7166c30c87208b4e23cf0"

PR = "r3"

DEPENDS = "u-boot-mkimage-native"

inherit deploy

S = "${WORKDIR}/git"

# TODO: fix dtc to use the already built package
SRC_URI = " \
	git://git.freescale.com/ppc/sdk/hypervisor/hypervisor.git;name=hypervisor \
	git://git.freescale.com/ppc/sdk/hypervisor/kconfig.git;name=kconfig;destsuffix=git/kconfig \
	git://git.freescale.com/ppc/sdk/hypervisor/libos.git;name=libos;destsuffix=git/libos \
	git://www.jdl.com/software/dtc.git;name=dtc;destsuffix=dtc \
	git://git.freescale.com/ppc/sdk/hypertrk.git;name=hypertrk;destsuffix=git/hypertrk \
	file://81-fsl-embedded-hv.rules \
	  "

SRCREV_FORMAT="hypervisor"
SRCREV = "e6092cdf2a225c66c1ea46b1151eb828da29d139"
SRCREV_kconfig = "a56025d4da992b856796b0eccac2e410d751dbac"
SRCREV_libos = "5268371581f3ef3959be2a53235edfa6a8c6aa7c"
SRCREV_dtc = "033089f29099bdfd5c2d6986cdb9fd07b16cfde0"
SRCREV_hypertrk = "975c98b562186afbd3bbf103ae54b96cf9b3e533"

EXTRA_OEMAKE = 'CROSS_COMPILE=${TARGET_PREFIX} CC="${TARGET_PREFIX}gcc ${TOOLCHAIN_OPTIONS}"'

DEFCONFIG = "defconfig"
DEFCONFIG_powerpc64 = "64bit_defconfig"

COMPATIBLE_HOST_fslmachine = ".*"
COMPATIBLE_HOST ?= "(none)"

inherit cml1
do_configure () {
	oe_runmake ${DEFCONFIG}
}

PKG_HV_HYPERTRK_SUPPORT = "n"
do_compile () {
	if [ "${PKG_HV_HYPERTRK_SUPPORT}" = "y" ]
	then
		oe_runmake silentoldconfig
		export HV_DIR=$PWD
		cd hypertrk
		oe_runmake deploy
		cd ..
	fi

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

INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
ALLOW_EMPTY_${PN} = "1"
PACKAGES_prepend = "${PN}-image ${PN}-partman "
FILES_${PN}-image = "/boot/"
FILES_${PN}-partman = "${bindir}/partman"
