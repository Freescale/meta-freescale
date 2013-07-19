include imx-lib.inc

PR = "r5.0"
PE = "1"

SRC_URI = "${FSL_MIRROR}/imx-lib-${PV}.bin;fsl-eula=true"

SRC_URI[md5sum] = "f0f9b0a7a7d558edfe624190a8860122"
SRC_URI[sha256sum] = "90caafc7a8898fc3126779eacec14fac2453afdaa45ddc7063ccd059dede97ce"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mx6)"
