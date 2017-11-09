SUMMARY = "PPFE Linux firmware"
LICENSE = "Freescale-Binary-EULA"
LIC_FILES_CHKSUM = "file://Freescale-Binary-EULA;md5=f1c407c0fccab5cd0bf9b92565f6f19b"

INHIBIT_DEFAULT_DEPS = "1"

inherit deploy

SRC_URI = "git://github.com/qoriq-open-source/engine-pfe-bin.git;nobranch=1"
SRCREV = "97cd13d3070d7199e62881bc495b332194b67edd"

S = "${WORKDIR}/git"

do_install () {
    install -d ${D}/lib/firmware
    install -d ${D}/boot/engine-pfe-bin
    install -m 644 ${S}/Freescale-Binary-EULA ${D}/lib/firmware
    install -m 755 ${S}/ls1012a/slow_path/*.elf ${D}/lib/firmware
    install -m 755 ${S}/ls1012a/u-boot/* ${D}/boot/engine-pfe-bin
}

do_deploy () {
    install -d ${DEPLOYDIR}/engine-pfe-bin
    cp -r ${D}/boot/engine-pfe-bin/* ${DEPLOYDIR}/engine-pfe-bin
}

addtask deploy after do_install

FILES_${PN} += "/lib/firmware /boot/"
INSANE_SKIP_${PN} += "arch already-stripped"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_SYSROOT_STRIP = "1"

COMPATIBLE_MACHINE = "(ls1012a)"
