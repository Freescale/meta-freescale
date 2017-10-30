SUMMARY = "PPFE Linux firmware"
LICENSE = "Freescale-Binary-EULA"
LIC_FILES_CHKSUM = "file://Freescale-Binary-EULA;md5=f1c407c0fccab5cd0bf9b92565f6f19b"

INHIBIT_DEFAULT_DEPS = "1"

SRC_URI = "git://github.com/qoriq-open-source/engine-pfe-bin.git;nobranch=1"
SRCREV = "97cd13d3070d7199e62881bc495b332194b67edd"

S = "${WORKDIR}/git"

do_install () {
    install -d ${D}/lib/firmware
    install -m 644 ${S}/Freescale-Binary-EULA ${D}/lib/firmware
    install -m 755 ${S}/ls1012a/slow_path/*.elf ${D}/lib/firmware
}

FILES_${PN} += "/lib/firmware"
INSANE_SKIP_${PN} += "arch already-stripped"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_SYSROOT_STRIP = "1"

COMPATIBLE_MACHINE = "(ls1012a)"
