require optee-os.nxp.inc

PV:append = "+git${SRCPV}"

PLATFORM_FLAVOR:ls1088ardb-pb   = "ls1088ardb"
PLATFORM_FLAVOR:ls1046afrwy     = "ls1046ardb"
PLATFORM_FLAVOR:lx2162aqds      = "lx2160aqds"

EXTRA_OEMAKE += " \
    PLATFORM=ls \
    CFG_ARM64_core=y \
"

do_compile:append:ls1012afrwy() {
    mv ${B}/core/tee-raw.bin  ${B}/core/tee_512mb.bin
    oe_runmake CFG_DRAM0_SIZE=0x40000000 all
}

do_install:append:qoriq() {
    install -m 644 ${B}/core/tee-raw.bin ${D}${nonarch_base_libdir}/firmware/tee_${MACHINE}.bin
}

do_install:append:ls1012afrwy() {
    install -m 644 ${B}/core/tee_512mb.bin ${D}${nonarch_base_libdir}/firmware/tee_${MACHINE}_512mb.bin
}

INHIBIT_PACKAGE_STRIP = "1"
COMPATIBLE_MACHINE = "(qoriq-arm64)"
