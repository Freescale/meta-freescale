SUMMARY = "Primary Protected Application"
LICENSE = "Freescale-EULA"
LIC_FILES_CHKSUM = "file://ppa/EULA.txt;md5=60037ccba533a5995e8d1a838d85799c"

DEPENDS += "u-boot-mkimage-native"

inherit deploy

SRC_URI = "git://git.freescale.com/ppc/sdk/ppa-generic.git;branch=sdk-v2.0.x"
SRCREV = "cb683b7cab76f2d685f54393d3b51dd1d02dac9d"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = "CC64="${CC}" LD64="${LD}"  OBJ64="${OBJCOPY}""

PPA_PATH ?= "ppa/soc-ls1043/platform-rdb"
PPA_PATH_ls1046a = "ppa/soc-ls1046/platform-rdb"
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
COMPATIBLE_MACHINE = "(ls1043a|ls1046a)"
PACKAGE_ARCH = "${MACHINE_ARCH}"
