require recipes-devtools/qemu/qemu.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=441c28d2cf86e15a37fa47e15a72fbac \
                    file://COPYING.LIB;endline=24;md5=c04def7ae38850e7d3ef548588159913"

# This means QEMU v1.7 with FSL specific patches applied
PV = "1.7+fsl"

SRC_URI = "git://git.freescale.com/ppc/sdk/qemu.git;nobranch=1"
SRCREV = "9e38e640275beabf6468a04cec5c403b2ac566ad"

S = "${WORKDIR}/git"

QEMU_TARGETS = "ppc"
PPC_OECONF = '${SDL} --cross-prefix=${TARGET_PREFIX} --disable-werror --disable-vnc --disable-bluez --disable-curl --enable-libusb'
EXTRA_OECONF_e5500-64b = "--target-list=ppc64-softmmu ${PPC_OECONF}"
EXTRA_OECONF_e6500-64b = "--target-list=ppc64-softmmu ${PPC_OECONF}"
EXTRA_OECONF_e6500 = "--target-list=ppc64-softmmu ${PPC_OECONF}"
EXTRA_OECONF_e5500 = "--target-list=ppc64-softmmu ${PPC_OECONF}"
EXTRA_OECONF_e500v2 = "--target-list=ppc-softmmu ${PPC_OECONF}"
EXTRA_OECONF_e500mc = "--target-list=ppc-softmmu ${PPC_OECONF}"

do_configure_prepend() {
        export PKG_CONFIG=${STAGING_DIR_NATIVE}${bindir_native}/pkg-config
}

do_configure_append () {
	grep 'CONFIG_FDT=y' config-host.mak
}

# gets around qemu.inc trying to install powerpc_rom.bin
do_install_prepend() {
	touch ${WORKDIR}/powerpc_rom.bin
}

do_install_append() {
	rm ${WORKDIR}/powerpc_rom.bin
    # Prevent QA warnings about installed ${localstatedir}/run
    if [ -d ${D}${localstatedir}/run ]; then rmdir ${D}${localstatedir}/run; fi
}

INSANE_SKIP_${PN} += "dev-deps"

# This is only meant to be build to run on the target
# for the given arch types listed, otherwise don't let
# the package get built. COMPATIBLE_HOST would not work
# because it was too generic
COMPATIBLE_MACHINE = "a^"
COMPATIBLE_MACHINE_libc-glibc_qoriq-ppc = ".*"
