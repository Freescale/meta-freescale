PR = "r1"

TOOLCHAIN_OUTPUTNAME ?= "${SDK_NAME}-toolchain-${DISTRO_VERSION}"
require recipes-core/meta/meta-toolchain.bb

TOOLCHAIN_HOST_TASK = "dtc-nativesdk"

TOOLCHAIN_NEED_CONFIGSITE_CACHE += "zlib"
