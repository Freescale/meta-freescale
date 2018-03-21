SUMMARY = "Reset Configuration Word"
DESCRIPTION = "Reset Configuration Word - hardware boot-time parameters for the QorIQ targets"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=45a017ee5f4cfe64b1cddf2eb06cffc7"

DEPENDS += "change-file-endianess-native tcl-native"

inherit deploy siteinfo

SRC_URI = "git://source.codeaurora.org/external/qoriq/qoriq-components/rcw;nobranch=1"
SRCREV = "baa4117294631db9e6ac2baa03ed5bdb793d7a81"

S = "${WORKDIR}/git"

export PYTHON = "${USRBINPATH}/python2"

M="${@d.getVar('MACHINE', True).replace('-64b','').replace('-32b','').replace('-${SITEINFO_ENDIANNESS}','')}"

do_install () {
    if [ -f ${S}/${M}/Makefile ]; then
        oe_runmake BOARDS=${M} DESTDIR=${D}/boot/rcw/ install
    else
        install -d ${D}/boot/rcw
        cp -a ${S}/${M} ${D}/boot/rcw/
        chown -R root:root ${D}
    fi
    for f in `find ${D}/boot/rcw/ -name "*qspiboot*"`;do
        if echo $f |grep -q "qspiboot_sben"; then
            continue
        fi
        f_swap=`echo $f |sed -e 's/qspiboot/qspiboot_swap/'`
        tclsh ${STAGING_BINDIR_NATIVE}/byte_swap.tcl $f $f_swap 8
        mv -f $f_swap $f
    done
}

do_deploy () {
    install -d ${DEPLOYDIR}/rcw
    cp -r ${D}/boot/rcw/* ${DEPLOYDIR}/rcw/
}
addtask deploy after do_install

PACKAGES += "${PN}-image"
FILES_${PN}-image += "/boot"

COMPATIBLE_MACHINE = "(qoriq)"
PACKAGE_ARCH = "${MACHINE_ARCH}"
