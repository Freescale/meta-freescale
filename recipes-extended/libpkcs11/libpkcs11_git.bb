DESCRIPTION = "PKCS library"
LICENSE = "GPL-2.0-only & BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=803852533e29eb1d6d5e55ad3078b625"

SRC_URI = "git://github.com/nxp-qoriq/libpkcs11;protocol=https;nobranch=1 \
    file://0001-fix-multiple-definition-error.patch \
"
SRCREV = "8d85182b7a7cd393ab6dd72930f8d1b69468f741"

DEPENDS = "openssl secure-obj"

S = "${WORKDIR}/git"

WRAP_TARGET_PREFIX ?= "${TARGET_PREFIX}"
export CROSS_COMPILE_HOST = "${CROSS_COMPILE}"
export CROSS_COMPILE_TA = "${CROSS_COMPILE}"

CFLAGS += "-fPIC"

EXTRA_OEMAKE = 'CC="${CC}" LD="${CC}" CFLAGS="${CFLAGS}"'

do_compile() {
        export OPENSSL_PATH="${RECIPE_SYSROOT}/usr"
        export CROSS_COMPILE="${WRAP_TARGET_PREFIX}"
        oe_runmake all 
}

do_install(){
    mkdir -p ${D}/${libdir}
    mkdir -p ${D}/${includedir} ${D}/${bindir}
    cp ${S}/out/export/lib/libpkcs11.so  ${D}/${libdir}
    cp ${S}/out/export/include/*.h  ${D}/${includedir}
    rm -f ${D}${includedir}/pkcs11.h
    cp ${S}/out/export/app/*  ${D}/${bindir}
}

PARALLEL_MAKE = ""
INSANE_SKIP:${PN} = "ldflags dev-deps"
INSANE_SKIP:${PN}-dev = "ldflags dev-elf"
COMPATIBLE_MACHINE = "(qoriq-arm64)"
