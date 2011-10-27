PR = "r1"

require fsl-toolchain-bare.bb

TOOLCHAIN_OUTPUTNAME = "${SDK_NAME}-toolchain-${DISTRO_VERSION}"
TOOLCHAIN_TARGET_TASK += ""
TOOLCHAIN_HOST_TASK += "dtc-nativesdk qemu-nativesdk"

