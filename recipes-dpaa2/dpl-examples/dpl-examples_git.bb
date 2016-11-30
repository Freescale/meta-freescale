SUMMARY = "Datapath layout examples"
LICENSE = "Freescale-EULA"
LIC_FILES_CHKSUM = "file://EULA;md5=c9ae442cf1f9dd6c13dfad64b0ffe73f"

DEPENDS = "dtc-native"

inherit deploy

SRC_URI = "git://git.freescale.com/ppc/sdk/dpl-examples.git;branch=sdk-v2.0.x"
SRCREV = "2f8a6c2c0607b663afd69aa0477c2047f25031ca"

S = "${WORKDIR}/git"

do_install () {
    install -d ${D}/boot
    install -m 644 ${S}/ls2080a/RDB/*.dtb ${D}/boot
}

do_deploy () {
    install -d ${DEPLOYDIR}/dpl-examples
    install -m 644 ${S}/ls2080a/RDB/*.dtb ${DEPLOYDIR}/dpl-examples
}
addtask deploy before do_build after do_install

PACKAGES += "${PN}-image"
FILES_${PN}-image += "/boot"

COMPATIBLE_MACHINE = "(ls2080ardb)"
PACKAGE_ARCH = "${MACHINE_ARCH}"

