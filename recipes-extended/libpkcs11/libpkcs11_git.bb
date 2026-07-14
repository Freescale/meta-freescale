SUMMARY = "PKCS#11 cryptographic token library"
DESCRIPTION = "PKCS#11 library implementing cryptographic token operations \
               backed by the NXP QorIQ secure object (secure-obj) subsystem."
HOMEPAGE = "https://github.com/nxp-qoriq/libpkcs11"
SECTION = "libs"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=803852533e29eb1d6d5e55ad3078b625"
DEPENDS = "openssl secure-obj"

SRC_URI = "git://github.com/nxp-qoriq/libpkcs11;protocol=https;nobranch=1 \
           file://0001-fix-multiple-definition-error.patch \
"
SRCREV = "8d85182b7a7cd393ab6dd72930f8d1b69468f741"

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
    install -d ${D}${libdir}
    install -d ${D}${includedir}
    install -d ${D}${bindir}
    install -m 0755 ${S}/out/export/lib/libpkcs11.so ${D}${libdir}
    install -m 0644 ${S}/out/export/include/*.h ${D}${includedir}
    rm -f ${D}${includedir}/pkcs11.h
    install -m 0755 ${S}/out/export/app/* ${D}${bindir}
}

PARALLEL_MAKE = ""

# The library links against sysroot libraries and ships its .so in the main
# package, so the ldflags/dev-deps/dev-elf QA checks do not apply cleanly here.
# nooelint: oelint.vars.insaneskip
INSANE_SKIP:${PN} = "ldflags dev-deps"
# nooelint: oelint.vars.insaneskip
INSANE_SKIP:${PN}-dev = "ldflags dev-elf"
COMPATIBLE_MACHINE = "(qoriq-arm64)"
