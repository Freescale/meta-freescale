PR = "r1"

require fsl-toolchain-bare.bb

TOOLCHAIN_OUTPUTNAME = "${SDK_NAME}-toolchain-${DISTRO_VERSION}"
TOOLCHAIN_TARGET_TASK += " \
	gtk+-dev \
	dtc \
	"

TOOLCHAIN_TARGET_TASK_append_e500mc = " usdpaa"

TOOLCHAIN_HOST_TASK += " \
	dtc-nativesdk \
	qemu-nativesdk \
	"
