require odp.inc

inherit module

do_compile:prepend () {
    export KERNEL_PATH="${STAGING_KERNEL_DIR}"
    export KERNEL_CFG_PATH="${STAGING_KERNEL_BUILDDIR}"
    cd ${S}/test/debug/perf_counters
}

do_install () {
    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/odp
    install -m 755 ${S}/test/debug/perf_counters/odpfsl_perfcounters.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/odp
}
