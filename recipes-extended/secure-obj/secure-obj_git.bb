require secure-obj.inc

LIC_FILES_CHKSUM = "file://README;md5=82b72e88f23cded9dd23f0fb1790b8d2"

S = "${WORKDIR}/git"

WRAP_TARGET_PREFIX ?= "${TARGET_PREFIX}"
export SECURE_STORAGE_PATH = "${S}/secure_storage_ta/ta/"
export OPTEE_CLIENT_EXPORT = "${RECIPE_SYSROOT}/usr"
export CROSS_COMPILE_HOST = "${CROSS_COMPILE}"
export CROSS_COMPILE_TA = "${CROSS_COMPILE}"

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
	install -d ${D}${bindir}/secure_obj
        install -d ${D}${libdir}/secure_obj
        install -d ${D}${includedir}
        cp ${S}/secure_storage_ta/ta/b05bcf48-9732-4efa-a9e0-141c7c888c34.ta ${D}${bindir}/secure_obj
        cp ${S}/securekey_lib/out/export/lib/libsecure_obj.so ${D}${libdir}/secure_obj
        cp ${S}/secure_obj-openssl-engine/libeng_secure_obj.so ${D}${libdir}/secure_obj
        cp ${S}/securekey_lib/out/export/app/* ${D}${bindir}/secure_obj
        cp ${S}/securekey_lib/out/export/include/*  ${D}${includedir}
        cp ${S}/secure_obj-openssl-engine/app/sobj_eng_app ${D}${bindir}/secure_obj
}

FILES_${PN} += "${libdir}/secure_obj"
