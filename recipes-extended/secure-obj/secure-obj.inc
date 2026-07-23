DESCRIPTION = "OP-TEE trusted application, key management library and OpenSSL engine for storing and using cryptographic keys inside the QorIQ ARM TrustZone secure world"
HOMEPAGE = "https://github.com/nxp-qoriq/secure_obj"
LICENSE = "BSD-3-Clause"

# Canonical DEPENDS definition for the secure-obj recipes; oelint's
# dependsappend rule wants only appends in an included file.
# nooelint: oelint.vars.dependsappend
DEPENDS = "openssl optee-client-qoriq optee-os-qoriq"
DEPENDS += "python3-pycryptodomex-native"

inherit python3native

LDFLAGS += "${TOOLCHAIN_OPTIONS}"

SRC_URI = "git://github.com/nxp-qoriq/secure_obj;protocol=https;nobranch=1"
SRCREV = "4706a6534ec09f9a46bfe305c5e49badca32b4c6"

WRAP_TARGET_PREFIX ?= "${TARGET_PREFIX}"
export SECURE_STORAGE_PATH = "${S}/secure_storage_ta/ta/"
export OPTEE_CLIENT_EXPORT = "${RECIPE_SYSROOT}/usr"

EXTRA_OEMAKE = 'CC="${CC}" LD="${CC}"'

ALLOW_EMPTY:${PN} = "1"
INHIBIT_PACKAGE_STRIP = "1"
PARALLEL_MAKE = ""
# Build links against toolchain libs without the distro LDFLAGS QA can track.
# nooelint: oelint.vars.insaneskip
INSANE_SKIP:${PN} = "ldflags"
COMPATIBLE_MACHINE = "(qoriq-arm64)"
DEBUG_BUILD = "1"

RDEPENDS:${PN} = "bash libcrypto libssl"
