DESCRIPTION = "Pattern matcher tools"
LICENSE = "Freescale-EULA"
LIC_FILES_CHKSUM = "file://EULA;md5=c9ae442cf1f9dd6c13dfad64b0ffe73f"

SRC_URI = "git://git.freescale.com/ppc/sdk/pme_tools.git;branch=sdk-v2.0.x \
    file://0001-pme-tools-fix-build-error.patch \
"
SRCREV = "5b6622e127ac2f3e221cc0395985ac90e2ed9533"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = 'ARCH=${TARGET_ARCH} USE_LTIB=1 LTIB_LIB_PATH=${STAGING_LIBDIR} SYSROOT=${STAGING_DIR_TARGET} CROSS_COMPILE=${HOST_PREFIX} EXTRA_LDFLAGS="${LDFLAGS}"'
EXTRA_CFLAGS = "-Wno-unused-but-set-parameter -Wno-enum-compare -Wno-unused-but-set-variable"
do_compile_prepend() {
    sed -i '/rec_yyget_leng/d' compilers/regularExpression/engine/pmrec.lex
    sed -i '/src_yyget_leng/d' compilers/statefulRule/engine/pmsrc.lex
    sed -i '/srcPreproc_yyget_leng/d' compilers/statefulRule/engine/pmsrc_preproc.lex
    oe_runmake clean
}

do_install() {
    install -m 755 -d ${D}${bindir} -d ${D}${sbindir}
    oe_runmake INSTALL_DIR=${D}/usr install

    # add runtime support files
    install -m 755 -d ${D}/${datadir}/pme-tools/sample_rules
    install -m 644 ltib_supp/sample* ${D}/${datadir}/pme-tools/sample_rules
    install -m 755 -d ${D}/etc/udev/rules.d
    install -m 644 ltib_supp/06-fsl-pme-device.rules ${D}/etc/udev/rules.d
}

PARALLEL_MAKE = ""

COMPATIBLE_MACHINE = "(e500mc|e5500|e5500-64b|e6500|e6500-64b)"
PACKAGE_ARCH = "${MACHINE_SOCARCH}"

