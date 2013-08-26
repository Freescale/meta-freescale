DESCRIPTION = "Barebox - a bootloader that inherits the best of U-Boot and the Linux kernel"
HOMEPAGE = "http://www.barebox.org/"
SECTION = "bootloader"
PROVIDES = "virtual/bootloader"
PRIORITY = "optional"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=057bf9e50e1ca857d0eb97bfe4ba8e5d"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit kernel-arch

EXTRA_OEMAKE = "CROSS_COMPILE=${TARGET_PREFIX}"

BAREBOX_BINARY ?= "barebox.bin"
BAREBOX_IMAGE ?= "barebox-${MACHINE}-${PV}-${PR}.bin"
BAREBOXENV_BIN ?= "bareboxenv-${MACHINE}-${PV}-${PR}.bin"
BAREBOX_SYMLINK ?= "barebox-${MACHINE}.bin"
BAREBOXENV_SYMLINK ?= "bareboxenv-${MACHINE}.bin"

INSANE_SKIP_${PN} = "True"

do_pre_configure() {
	   cp ${WORKDIR}/defconfig ${S}/.config
	   oe_runmake oldconfig
}
addtask pre_configure before do_configure after do_patch

do_compile () {
	if [ "${@base_contains('DISTRO_FEATURES', 'ld-is-gold', 'ld-is-gold', '', d)}" = "ld-is-gold" ] ; then
		sed -i 's/$(CROSS_COMPILE)ld/$(CROSS_COMPILE)ld.bfd/g' Makefile
	fi
	unset LDFLAGS
	unset CFLAGS
	unset CPPFLAGS
	oe_runmake all
}

do_deploy () {
	install -d ${DEPLOY_DIR_IMAGE}
	install ${S}/${BAREBOX_BINARY} ${DEPLOY_DIR_IMAGE}/${BAREBOX_IMAGE}

	cd ${DEPLOY_DIR_IMAGE}
	rm -f ${BAREBOX_SYMLINK}
	ln -sf ${BAREBOX_IMAGE} ${BAREBOX_SYMLINK}

	install -d ${STAGING_BINDIR_NATIVE}
	cd ${S}
	install -m 755 scripts/mkimage ${STAGING_BINDIR_NATIVE}/
	install -m 755 scripts/bareboxenv ${STAGING_BINDIR_NATIVE}/
}

do_deploy[dirs] = "${S}"
addtask deploy before do_build after do_compile

do_env_deploy() {
	install ${S}/common/barebox_default_env ${DEPLOY_DIR_IMAGE}/${BAREBOXENV_BIN}

	cd ${DEPLOY_DIR_IMAGE}
	rm -f ${BAREBOXENV_SYMLINK}
	ln -sf ${BAREBOXENV_BIN} ${BAREBOXENV_SYMLINK}
}
addtask env_deploy before do_build after do_deploy
