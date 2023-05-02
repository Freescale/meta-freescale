HOMEPAGE = "http://www.optee.org/"

inherit python3native features_check

REQUIRED_MACHINE_FEATURES = "optee"

DEPENDS = "python3-cryptography-native"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"

OPTEE_ARCH:arm     = "arm32"
OPTEE_ARCH:aarch64 = "arm64"

COMPILER ?= "gcc"
COMPILER:toolchain-clang = "clang"

CFLAGS += "--sysroot=${STAGING_DIR_HOST}"
CXXFLAGS += "--sysroot=${STAGING_DIR_HOST}"

EXTRA_OEMAKE = " \
    COMPILER=${COMPILER} \
    OPENSSL_MODULES=${STAGING_LIBDIR_NATIVE}/ossl-modules \
    OPTEE_CLIENT_EXPORT=${STAGING_DIR_HOST}${exec_prefix} \
    -C ${S} O=${B} \
"
