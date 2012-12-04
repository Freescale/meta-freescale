PR = "r1"

require fsl-toolchain-bare.bb

TOOLCHAIN_OUTPUTNAME = "${SDK_NAME}-toolchain-${DISTRO_VERSION}"
TOOLCHAIN_TARGET_TASK += " \
	glib-2.0 \
	glib-2.0-dev \
	dtc \
	libgomp \
	libgomp-dev \
	"

TOOLCHAIN_HOST_TASK += " \
	nativesdk-dtc \
	"
