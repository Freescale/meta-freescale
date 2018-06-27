SUMMARY = "Primary Protected Application"
LICENSE = "Freescale-EULA"
LIC_FILES_CHKSUM = "file://license.txt;md5=2ecf925c01a48f61c88f78c30fe2ee3b"

DEPENDS += "u-boot-mkimage-native dtc-native"

inherit deploy

SRC_URI = "git://source.codeaurora.org/external/qoriq/qoriq-components/ppa-generic;nobranch=1 \
"
SRCREV = "dcbe27bb5d1f769ca2c061a3d4241aa2d13dc19e"

S = "${WORKDIR}/git"

python () {
    ml = d.getVar("MULTILIB_VARIANTS", True)
    arch = d.getVar("OVERRIDES", True)
    if "fsl-lsch2-32b:" in arch:
        if not "lib64" in ml:
            raise bb.parse.SkipPackage("Building the u-boot for this arch requires multilib to be enabled")
        sys_multilib = d.getVar('TARGET_VENDOR') + 'mllib64-linux'
        sys_original = d.getVar('TARGET_VENDOR') + '-' + d.getVar('TARGET_OS')
        workdir = d.getVar('WORKDIR').replace(sys_original,sys_multilib)
        d.setVar('DEPENDS_append', ' lib64-gcc-cross-aarch64 lib64-libgcc')
        d.setVar('PATH_append', ':' + d.getVar('STAGING_BINDIR_NATIVE') + '/aarch64' + sys_multilib)
        d.setVar('TOOLCHAIN_OPTIONS', '--sysroot=' + workdir + '/lib64-recipe-sysroot')
        d.setVar("WRAP_TARGET_PREFIX", 'aarch64' + sys_multilib + '-')
}

WRAP_TARGET_PREFIX ?= "${TARGET_PREFIX}"
EXTRA_OEMAKE = 'CC64="${WRAP_TARGET_PREFIX}gcc ${TOOLCHAIN_OPTIONS}" LD64="${WRAP_TARGET_PREFIX}ld ${TOOLCHAIN_OPTIONS}"  OBJ64="${WRAP_TARGET_PREFIX}objcopy"'

PPA_NAME = "${PN}-${MACHINE}-${DATETIME}"
PPA_NAME[vardepsexclude] = "DATETIME"

do_install() {
    install -d ${D}/boot/
    install ${S}/ppa/soc-${PPA_PATH}/build/obj/ppa.itb ${D}/boot/${PPA_NAME}.itb
    ln -sfT ${PPA_NAME}.itb ${D}/boot/${PN}.itb
}

do_deploy(){
    install -d ${DEPLOYDIR}
    install ${S}/ppa/soc-${PPA_PATH}/build/obj/ppa.itb ${DEPLOYDIR}/${PPA_NAME}.itb
    ln -sfT ${PPA_NAME}.itb ${DEPLOYDIR}/${PN}.itb
}
addtask deploy before do_build after do_install

PACKAGES += "${PN}-image"
FILES_${PN}-image += "/boot"   

CLEANBROKEN = "1"
PARALLEL_MAKE = ""
PACKAGE_ARCH = "${MACHINE_ARCH}"
