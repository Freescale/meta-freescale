require odp.inc

inherit autotools-brokensep

PACKAGE_ARCH = "${MACHINE_ARCH}"

DEPENDS = "openssl cunit libxml2"

RDEPENDS:${PN} = "bash libcrypto libssl odp-module odp-counters"

ODP_SOC ?= ""
ODP_SOC:ls1043ardb = "LS1043"
ODP_SOC:ls1046ardb = "LS1046"
ODP_PLATFORM ?= "linux-dpaa2"
ODP_BUILD_TYPE ?= "ls2088"
ODP_BUILD_TYPE:ls1043ardb = "ls1043"
ODP_BUILD_TYPE:ls1046ardb = "ls1046"
ODP_BUILD_TYPE:ls1088ardb = "ls1088"

EXTRA_OECONF = "--with-platform=${ODP_PLATFORM} \
                --enable-test-vald \
                --enable-test-perf \
                --enable-test-cpp \
"

EXTRA_OEMAKE = "CROSS_COMPILE="${TARGET_PREFIX}" \
                SYSROOT="${STAGING_DIR_TARGET}" \
"

CFLAGS += "-Wno-format-truncation -Wno-maybe-uninitialized -Wno-implicit-fallthrough -Wno-cpp -Wno-cast-function-type \
          -Wno-stringop-truncation \
"

PACKAGECONFIG[perf] = "--enable-test-perf,,,"

do_configure:prepend () {
    export SOC=${ODP_SOC}
    ${S}/bootstrap
}

do_compile:prepend () {
    export SOC=${ODP_SOC}
    export ARCH=${TUNE_ARCH}
}

do_install:append () {
    install -d ${D}${includedir}/odp/kni
    install -d ${D}${includedir}/odp/kern
    install -d ${D}${includedir}/odp/flib/mc
    install -d ${D}${includedir}/odp/flib/qbman/include/drivers

    cp -rf ${S}/platform/linux-dpaa2/include/* ${D}${includedir}/odp/
    cp -rf ${S}/platform/linux-dpaa2/kni/*.h ${D}${includedir}/odp/kni/
    cp -rf ${S}/kern/*.h ${D}${includedir}/odp/kern/
    cp -rf ${S}/platform/linux-dpaa2/flib/mc/*.h ${D}${includedir}/odp/flib/mc/

    sed -i -e 's#platform/linux-dpaa2/##g' ${D}${includedir}/odp/kern/*.h
}

FILES:${PN}-staticdev += "${datadir}/opendataplane/*.la"
FILES:${PN} += "/usr/odp/bin /usr/odp/scripts /usr/odp/debug /usr/odp/test/validation /usr/odp/test/performance /usr/odp/test/miscellaneous /usr/odp/test/api_test"
FILES:${PN}-dbg += "/usr/odp/bin/.debug /usr/odp/debug/.debug /usr/odp/test/validation/.debug /usr/odp/test/performance/.debug /usr/odp/test/miscellaneous/.debug /usr/odp/test/api_test/.debug"
