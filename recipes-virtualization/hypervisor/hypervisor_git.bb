DESCRIPTION = "Freescale embedded hypervisor"
SECTION = "embedded-hv"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://README;endline=22;md5=0655bbc3b7d7166c30c87208b4e23cf0"

PR = "r3"

DEPENDS = "u-boot-mkimage-native"

inherit deploy

# TODO: fix dtc to use the already built package
SRC_URI = " \
	git://git.freescale.com/ppc/sdk/hypervisor/hypervisor.git;name=hypervisor;branch=sdk-v2.0.x \
	git://git.freescale.com/ppc/sdk/hypervisor/kconfig.git;name=kconfig;destsuffix=git/kconfig;branch=sdk-v2.0.x \
	git://git.freescale.com/ppc/sdk/hypervisor/libos.git;name=libos;destsuffix=git/libos;branch=sdk-v2.0.x \
	git://git.kernel.org/pub/scm/utils/dtc/dtc.git;name=dtc;destsuffix=dtc \
	git://git.freescale.com/ppc/sdk/hypertrk.git;name=hypertrk;destsuffix=git/hypertrk;branch=sdk-v2.0.x \
	file://81-fsl-embedded-hv.rules \
	  "

SRCREV_FORMAT="hypervisor"
SRCREV = "f23ac6da140e1c7e6327093b18e4b6355075de79"
SRCREV_kconfig = "a56025d4da992b856796b0eccac2e410d751dbac"
SRCREV_libos = "64f4424c35b306a8103a68e23adea4274921d699"
SRCREV_dtc = "a6d55e039fd22048687fe061b4609e2807efe764"
SRCREV_hypertrk = "975c98b562186afbd3bbf103ae54b96cf9b3e533"

S = "${WORKDIR}/git"

OUTPUT ?= "output32"
OUTPUT_powerpc64 = "output64"

EXTRA_OEMAKE = 'CROSS_COMPILE=${TARGET_PREFIX} CC="${TARGET_PREFIX}gcc ${TOOLCHAIN_OPTIONS}" HOSTCC="${BUILD_CC}" O="${OUTPUT}"'

DEFCONFIG = "defconfig"
DEFCONFIG_powerpc64 = "64bit_defconfig"

COMPATIBLE_HOST_qoriq-ppc = ".*"
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
	install ${B}/${OUTPUT}/bin/linux/partman ${D}/${bindir}/partman

        install -d ${D}${sysconfdir}/udev/rules.d
        install -m 0644 ${WORKDIR}/81-fsl-embedded-hv.rules ${D}${sysconfdir}/udev/rules.d

	install -d ${D}/boot/hv
	install ${B}/${OUTPUT}/.config ${D}/boot/hv/hypervisor.config
	install -m 644 ${B}/${OUTPUT}/bin/hv ${B}/${OUTPUT}/bin/hv.map \
                ${B}/${OUTPUT}/bin/hv.uImage ${B}/${OUTPUT}/bin/hv.bin \
                        ${D}/boot/hv/
}

do_deploy () {
	install -d ${DEPLOYDIR}/hv/
	install ${B}/${OUTPUT}/.config ${DEPLOYDIR}/hv/hypervisor.config
	install -m 644 ${B}/${OUTPUT}/bin/hv ${B}/${OUTPUT}/bin/hv.map \
                ${B}/${OUTPUT}/bin/hv.uImage ${B}/${OUTPUT}/bin/hv.bin \
                        ${DEPLOYDIR}/hv/
}
addtask deploy before do_build after do_install

do_deploy_append() {
	rm -f ${B}/../hv
}

INSANE_SKIP_${PN} = 'already-stripped'
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
ALLOW_EMPTY_${PN} = "1"
PACKAGES_prepend = "${PN}-image ${PN}-partman "
FILES_${PN}-image = "/boot/"
FILES_${PN}-partman = "${bindir}/partman"
