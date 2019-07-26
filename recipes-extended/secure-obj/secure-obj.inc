DESCRIPTION = "Secure Object"
LICENSE = "BSD"

DEPENDS = "openssl optee-os-qoriq optee-client-qoriq"
RDEPENDS_${PN} = "bash libcrypto libssl"

DEPENDS += "python-pycrypto-native"

inherit pythonnative

LDFLAGS += "${TOOLCHAIN_OPTIONS}"

SRC_URI = "git://source.codeaurora.org/external/qoriq/qoriq-components/secure_obj;nobranch=1 \
"
SRCREV = "cd3c353df1c3f19577cf3fa08f8a21abd63fb3c1"

WRAP_TARGET_PREFIX ?= "${TARGET_PREFIX}"
export SECURE_STORAGE_PATH = "${S}/secure_storage_ta/ta/"
export OPTEE_CLIENT_EXPORT = "${RECIPE_SYSROOT}/usr"

EXTRA_OEMAKE = 'CC="${CC}" LD="${CC}"'

ALLOW_EMPTY_${PN} = "1"
INHIBIT_PACKAGE_STRIP = "1"
PARALLEL_MAKE = ""
INSANE_SKIP_${PN} = "ldflags"
COMPATIBLE_MACHINE = "(qoriq-arm64)"
DEBUG_BUILD = "1"
