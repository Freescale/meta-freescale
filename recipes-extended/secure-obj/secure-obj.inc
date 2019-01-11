DESCRIPTION = "Secure Object"
LICENSE = "BSD"

DEPENDS = "openssl optee-os-qoriq optee-client-qoriq"
RDEPENDS_${PN} = "bash libcrypto libssl python"

DEPENDS += "python-pycrypto-native"

inherit pythonnative

LDFLAGS += "${TOOLCHAIN_OPTIONS}"

SRC_URI = "git://source.codeaurora.org/external/qoriq/qoriq-components/secure_obj;nobranch=1 \
"
SRCREV = "bc0553a3c7a02555eb6bf46803854f6b49227a26"

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
