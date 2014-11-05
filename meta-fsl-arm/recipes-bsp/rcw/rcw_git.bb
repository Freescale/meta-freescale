SUMMARY = "Reset Configuration Word"
DESCRIPTION = "Reset Configuration Word - hardware boot-time parameters for the QorIQ targets"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://rcw.py;beginline=8;endline=28;md5=9ba0b28922dd187b06b6c8ebcfdd208e"

DEPENDS += "u-boot-ls1-tools-native"

inherit deploy

SRCBRANCH = "LS1-SDK"
SRCREV = "38ec2c9111b483203797f7228db5f92549e9fe73"
SRC_URI = "git://git.freescale.com/layerscape/ls1021a/rcw.git;branch=${SRCBRANCH} \
    file://rcw-make-BOARDS-DESTDIR-overidable-in-Makefile.patch \
"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = "BOARDS=${@d.getVar('MACHINE', True).replace('-64b','')} DESTDIR=${D}/boot/rcw/"

do_install () {
    oe_runmake install 
}

do_deploy () {
    install -d ${DEPLOYDIR}/rcw
    cp -r ${D}/boot/rcw/* ${DEPLOYDIR}/rcw/
}
addtask deploy after do_install

PACKAGES += "${PN}-image"
FILES_${PN}-image += "/boot"

ALLOW_EMPTY_${PN} = "1"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(ls102xa)"
