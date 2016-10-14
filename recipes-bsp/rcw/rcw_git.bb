SUMMARY = "Reset Configuration Word"
DESCRIPTION = "Reset Configuration Word - hardware boot-time parameters for the QorIQ targets"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=3775480a712fc46a69647678acb234cb"

DEPENDS += "change-file-endianess-native tcl-native"

inherit deploy

PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = "git://git.freescale.com/ppc/sdk/rcw.git;branch=sdk-v2.0.x"
SRCREV = "6ae9086528019ab55968da05c25bd319737c8e62"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = "BOARDS=${@d.getVar('MACHINE', True).replace('-64b','')} DESTDIR=${D}/boot/rcw/"

do_install () {
    oe_runmake install
    for f in `find ${D}/boot/rcw/ -name "*qspiboot*"`;do
        f_swap=`echo $f |sed -e 's/qspiboot/qspiboot_swap/'`
        tclsh ${STAGING_BINDIR_NATIVE}/byte_swap.tcl $f $f_swap 8
    done
}

do_deploy () {
    install -d ${DEPLOYDIR}/rcw
    cp -r ${D}/boot/rcw/* ${DEPLOYDIR}/rcw/
}
addtask deploy after do_install

PACKAGES += "${PN}-image"
FILES_${PN}-image += "/boot"
COMPATIBLE_MACHINE = "(qoriq-ppc|ls1021a|ls1043a)"
