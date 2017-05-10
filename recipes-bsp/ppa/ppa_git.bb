SUMMARY = "Primary Protected Application"
LICENSE = "Freescale-EULA"
LIC_FILES_CHKSUM = "file://ppa/EULA.txt;md5=60037ccba533a5995e8d1a838d85799c"

DEPENDS += "u-boot-mkimage-native dtc-native"

inherit deploy

SRC_URI = "git://git.freescale.com/ppc/sdk/ppa-generic.git;nobranch=1"
SRCREV = "9fcb080dd7415927aa3fbabfcba8982bcb1466d3"

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

PPA_PATH ?= "ppa/soc-ls1043/platform-rdb"
PPA_PATH_ls1046a = "ppa/soc-ls1046/platform-rdb"
PPA_PATH_ls2088a = "ppa/soc-ls2088/platform-rdb"
PPA_PATH_ls1012a = "ppa/soc-ls1012/platform-rdb"
PPA_NAME ?= "ppa-${MACHINE}-${DATETIME}"
PPA_NAME[vardepsexclude] = "DATETIME"

do_compile() {
    export ARMV8_TOOLS_DIR="${STAGING_BINDIR_TOOLCHAIN}"
    export ARMV8_TOOLS_PREFIX="${TARGET_PREFIX}"
    export CROSS_COMPILE="${WRAP_TARGET_PREFIX}"
    cd ${S}/${PPA_PATH}
    oe_runmake rdb-fit
    cd ${S}
}

do_install() {
    install -d ${D}/boot/
    install ${S}/${PPA_PATH}/build/obj/ppa.itb ${D}/boot/${PPA_NAME}.itb
    ln -sfT ${PPA_NAME}.itb ${D}/boot/ppa.itb
}

do_deploy(){
    install -d ${DEPLOYDIR}
    install ${S}/${PPA_PATH}/build/obj/ppa.itb ${DEPLOYDIR}/${PPA_NAME}.itb
    ln -sfT ${PPA_NAME}.itb ${DEPLOYDIR}/ppa.itb
}
addtask deploy before do_build after do_install

PACKAGES += "${PN}-image"
FILES_${PN}-image += "/boot"   

CLEANBROKEN = "1"
PARALLEL_MAKE = ""
COMPATIBLE_MACHINE = "(ls1043a|ls1046a|ls2088a|ls1012a)"
PACKAGE_ARCH = "${MACHINE_ARCH}"
