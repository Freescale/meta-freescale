require recipes-devtools/qemu/qemu.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=441c28d2cf86e15a37fa47e15a72fbac \
                    file://COPYING.LIB;endline=24;md5=c04def7ae38850e7d3ef548588159913"

# This means QEMU v2.2.0 with FSL specific patches applied
PV = "2.2.0+${SRCPV}"

# NOTE: this recipe requires poky's qemu.inc which assumes version 2.3
# where glx enable config option changed to --enable-opengl. For now we
# restore it, but we should remove the following lines when upgrading
# to qemu 2.3:
PACKAGECONFIG[glx] = "--enable-glx,--disable-glx,mesa"

SRC_URI = "git://git.freescale.com/ppc/sdk/qemu.git;branch=master"
SRCREV = "00ac004143e9fe46944a1885b04268fcd3a95a3a"

S = "${WORKDIR}/git"

QEMU_TARGETS = "arm"

inherit pkgconfig

# Append build host pkg-config paths for native target since the host may provide sdl
do_configure_prepend() {
    export PKG_CONFIG=${STAGING_DIR_NATIVE}${bindir_native}/pkg-config
}

do_configure_append () {
    if ! grep 'CONFIG_FDT=y' config-host.mak; then
         echo "CONFIG_RDMA=y" >> config_host_mak
    fi
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

FILES_${PN} += "/usr/share/qemu/"

# FIXME: Avoid WARNING due missing patch for native/nativesdk
BBCLASSEXTEND = ""
