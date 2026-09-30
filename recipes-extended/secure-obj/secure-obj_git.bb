require secure-obj.inc

SUMMARY = "OP-TEE-backed secure object library"
SECTION = "security"
LIC_FILES_CHKSUM = "file://LICENSE;md5=751419260aa954499f7abaabaa882bbe"

DEPENDS:remove = "python3-pycryptodomex-native"
DEPENDS:append = " python3-cryptography-native optee-os-qoriq-tadevkit"
FILES:${PN} += "${base_libdir}/optee_armtz"
RDEPENDS:${PN} += "secure-obj-module"

WRAP_TARGET_PREFIX ?= "${TARGET_PREFIX}"
export SECURE_STORAGE_PATH = "${S}/secure_storage_ta/ta/"
export OPTEE_CLIENT_EXPORT = "${RECIPE_SYSROOT}/usr"
export CROSS_COMPILE_HOST = "${CROSS_COMPILE}"
export CROSS_COMPILE_TA = "${CROSS_COMPILE}"
ARCH:qoriq-arm64 = "aarch64"
ARCH:qoriq-arm = "arm"
CFLAGS += "${TOOLCHAIN_OPTIONS}"

# The securekey_lib apps compile with plain $(CC), ignoring CFLAGS, so the
# debug prefix map rides on CC to keep build paths out of the binaries.
CC:append = " ${DEBUG_PREFIX_MAP}"

do_compile() {
        unset LDFLAGS
        export TA_DEV_KIT_DIR=${STAGING_INCDIR}/optee/export-user_ta/
        export CROSS_COMPILE="${WRAP_TARGET_PREFIX}"
        export OPENSSL_PATH="${RECIPE_SYSROOT}/usr"
        export OPENSSL_MODULES=${STAGING_LIBDIR_NATIVE}/ossl-modules
        for APP in  secure_storage_ta securekey_lib; do
            cd  ${APP}
            oe_runmake
        cd ..
        done
}

do_install() {
    install -d ${D}${bindir}
    install -d ${D}${includedir}
    install -d ${D}${base_libdir}/optee_armtz
    install -m 0644 ${S}/secure_storage_ta/ta/b05bcf48-9732-4efa-a9e0-141c7c888c34.ta ${D}${base_libdir}/optee_armtz
    install -m 0755 ${S}/securekey_lib/out/export/lib/libsecure_obj.so ${D}${libdir}
    install -m 0755 ${S}/securekey_lib/out/export/app/*_app ${D}${bindir}
    install -m 0755 ${S}/securekey_lib/out/export/app/mp_verify ${D}${bindir}
    install -m 0644 ${S}/securekey_lib/out/export/include/* ${D}${includedir}
    rm -rf ${D}${bindir}/test
}

# The OP-TEE TA and securekey library link against toolchain libs and ship
# dev files in the main package; INSANE_SKIP is unavoidable here.
# nooelint: oelint.vars.insaneskip
INSANE_SKIP:${PN} += "dev-deps"
# Same reason as above, for the -dev package.
# nooelint: oelint.vars.insaneskip
INSANE_SKIP:${PN}-dev = "ldflags dev-elf"
