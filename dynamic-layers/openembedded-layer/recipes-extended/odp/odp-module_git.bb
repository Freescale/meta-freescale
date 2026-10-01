require odp.inc

inherit module

do_compile:prepend () {
    export KERNEL_PATH="${STAGING_KERNEL_DIR}"
    export KERNEL_CFG_PATH="${STAGING_KERNEL_BUILDDIR}"
    cd ${S}/kern
}

do_install () {
    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/odp
    install -m 755 ${S}/kern/odpfsl_kni.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/odp
}

PKG:${PN} = "kernel-module-${PN}"
