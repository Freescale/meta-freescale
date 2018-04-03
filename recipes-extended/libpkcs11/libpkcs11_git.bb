DESCRIPTION = "PKCS library"
LICENSE = "GPLv2 & BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=803852533e29eb1d6d5e55ad3078b625"

SRC_URI = "git://source.codeaurora.org/external/qoriq/qoriq-components/libpkcs11;nobranch=1"
SRCREV = "dd1bc90b0f2171d4dddd9d7d0fc5a2acfb067ab8"

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
    cp ${S}/out/export/app/pkcs11_app  ${D}/${bindir}
}

PARALLEL_MAKE = ""
INSANE_SKIP_${PN} = "ldflags dev-deps"
INSANE_SKIP_${PN}-dev = "ldflags dev-elf"
COMPATIBLE_MACHINE = "(ls1043a|ls1046a|ls2088a|ls1088a)"
