require secure-obj.inc

LIC_FILES_CHKSUM = "file://README;md5=82b72e88f23cded9dd23f0fb1790b8d2"

S = "${WORKDIR}/git"

RDEPENDS_{PN}  += "secure-obj-module"

WRAP_TARGET_PREFIX ?= "${TARGET_PREFIX}"
export SECURE_STORAGE_PATH = "${S}/secure_storage_ta/ta/"
export OPTEE_CLIENT_EXPORT = "${RECIPE_SYSROOT}/usr"
export CROSS_COMPILE_HOST = "${CROSS_COMPILE}"
export CROSS_COMPILE_TA = "${CROSS_COMPILE}"
ARCH_qoriq-arm64 = "aarch64"
ARCH_qoriq-arm = "arm"

do_compile() {
        unset LDFLAGS
        export TA_DEV_KIT_DIR="${RECIPE_SYSROOT}/usr/include/optee/export-user_ta"
        export CROSS_COMPILE="${WRAP_TARGET_PREFIX}"
        export OPENSSL_PATH="${RECIPE_SYSROOT}/usr" 
        for APP in  secure_storage_ta securekey_lib secure_obj-openssl-engine; do        
            cd  ${APP}
            oe_runmake
	    cd ..
        done       
}

do_install() {
	install -d ${D}${bindir}
        install -d ${D}${includedir}
        install -d ${D}${base_libdir}/optee_armtz
        install -d ${D}${libdir}/${ARCH}-linux-gnu/openssl-1.0.0/engines
        cp ${S}/secure_storage_ta/ta/b05bcf48-9732-4efa-a9e0-141c7c888c34.ta ${D}${base_libdir}/optee_armtz
        cp ${S}/securekey_lib/out/export/lib/libsecure_obj.so ${D}${libdir}
        cp ${S}/secure_obj-openssl-engine/libeng_secure_obj.so ${D}${libdir}/${ARCH}-linux-gnu/openssl-1.0.0/engines
        cp ${S}/securekey_lib/out/export/app/*_app ${D}${bindir}
        cp ${S}/securekey_lib/out/export/app/mp_verify ${D}${bindir}
        cp ${S}/secure_obj-openssl-engine/app/sobj_eng_app ${D}${bindir}
        cp ${S}/securekey_lib/out/export/include/*  ${D}${includedir}
        rm -rf ${D}${bindir}/test
}

FILES_${PN} += "${base_libdir}/optee_armtz ${libdir}/${ARCH}-linux-gnu/openssl-1.0.0/engines"
INSANE_SKIP_${PN} = "dev-deps ldflags"
INSANE_SKIP_${PN}-dev = "ldflags dev-elf"
