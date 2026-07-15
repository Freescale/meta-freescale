SUMMARY = "FSL Community console image with full cmdline and QorIQ networking"
DESCRIPTION = "A console-only image that includes full cmdline and \
               Freescale's networking packages (QorIQ DPAA/DPAA2) when available."
SECTION = "images"

# This development image ships tools-profile and enables debug-tweaks
# intentionally to allow unauthenticated console access during testing.
# nooelint: oelint.var.badimagefeature.debug-tweaks
IMAGE_FEATURES += "\
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
