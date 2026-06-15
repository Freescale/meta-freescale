DESCRIPTION = "A console-only image that includes full cmdline and \
Freescale's networking packages (QorIQ DPAA/DPAA2) when available."

IMAGE_FEATURES += " \
    debug-tweaks \
    tools-profile \
"

LICENSE = "MIT"

CORE_IMAGE_EXTRA_INSTALL:append = "\
    packagegroup-core-boot \
    packagegroup-core-full-cmdline \
    packagegroup-fsl-network \
"

# SPI Nor-Flash
NETWORK_TOOLS:append:fsl-lsch3 = "\
    mtd-utils \
"

inherit core-image
