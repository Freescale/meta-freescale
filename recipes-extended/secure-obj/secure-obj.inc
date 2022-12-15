DESCRIPTION = "Secure Object"
LICENSE = "BSD"

DEPENDS = "openssl optee-os-qoriq optee-client-qoriq"
RDEPENDS:${PN} = "bash libcrypto libssl"

DEPENDS += "python3-pycryptodomex-native"

inherit python3native

LDFLAGS += "${TOOLCHAIN_OPTIONS}"

SRC_URI = "git://github.com/nxp-qoriq/secure_obj;protocol=https;nobranch=1"
SRCREV = "5ff1231f74b4b01744be95a3137a14ad0a483e61"

WRAP_TARGET_PREFIX ?= "${TARGET_PREFIX}"
export SECURE_STORAGE_PATH = "${S}/secure_storage_ta/ta/"
export OPTEE_CLIENT_EXPORT = "${RECIPE_SYSROOT}/usr"

EXTRA_OEMAKE = 'CC="${CC}" LD="${CC}"'

ALLOW_EMPTY:${PN} = "1"
INHIBIT_PACKAGE_STRIP = "1"
PARALLEL_MAKE = ""
INSANE_SKIP:${PN} = "ldflags"
COMPATIBLE_MACHINE = "(qoriq-arm64)"
DEBUG_BUILD = "1"
