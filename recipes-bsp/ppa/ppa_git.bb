SUMMARY = "Primary Protected Application"
LICENSE = "Freescale-EULA"
LIC_FILES_CHKSUM = "file://EULA;md5=c9ae442cf1f9dd6c13dfad64b0ffe73f"

DEPENDS += "u-boot-mkimage-native"

inherit deploy

SRC_URI = "git://git.freescale.com/ppc/sdk/ls1043-ppa.git;branch=sdk-v2.0.x \
    file://ppa.its \ 
"
SRCREV = "ffda4e6c2cfb535636ac8d667b0d2351d557cc66"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = "CC64="${CC}" LD64="${LD}"  OBJ64="${OBJCOPY}""

PPA_NAME ?= "ppa-${MACHINE}-${DATETIME}"
PPA_NAME[vardepsexclude] = "DATETIME"

do_compile() {
    if [ ! -e ${S}/ppa.its ]; then
        cp ${WORKDIR}/ppa.its ${S}
    fi
    export ARMV8_TOOLS_DIR="${STAGING_BINDIR_TOOLCHAIN}"
    export ARMV8_TOOLS_PREFIX="${TARGET_PREFIX}"
    export FILE_NAMES_DIR="${S}/obj"
    oe_runmake all
    uboot-mkimage -f ppa.its ppa.itb
}

do_install() {
    install -d ${D}/boot/
    install ${S}/ppa.itb ${D}/boot/${PPA_NAME}.itb
    ln -sfT ${PPA_NAME}.itb ${D}/boot/ppa.itb
}

do_deploy(){
    install -d ${DEPLOYDIR} 
    install ${S}/ppa.itb ${DEPLOYDIR}/${PPA_NAME}.itb
    ln -sfT ${PPA_NAME}.itb ${DEPLOYDIR}/ppa.itb
}
addtask deploy before do_build after do_install

PACKAGES += "${PN}-image"
FILES_${PN}-image += "/boot"   
COMPATIBLE_MACHINE = "(ls1043ardb)"
