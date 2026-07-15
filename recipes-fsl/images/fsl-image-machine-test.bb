SUMMARY = "FSL Community console test image with multimedia, GPU and benchmark tools"
DESCRIPTION = "A console-only image that includes gstreamer packages, \
               Freescale's multimedia packages (VPU and GPU) when available, and \
               test and benchmark applications."
SECTION = "images"

# This is a development and test image (it ships tools-testapps, tools-profile
# and benchmark applications), so debug-tweaks is intentional to allow
# unauthenticated console access during testing.
# nooelint: oelint.var.badimagefeature.debug-tweaks
IMAGE_FEATURES += "\
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', '', \
       bb.utils.contains('DISTRO_FEATURES',     'x11', 'x11-base', \
                                                       '', d), d)} \
    debug-tweaks \
    tools-testapps \
    tools-profile \
"

LICENSE = "MIT"

inherit core-image

CORE_IMAGE_EXTRA_INSTALL += "\
    packagegroup-fsl-gstreamer1.0 \
    packagegroup-fsl-gstreamer1.0-full \
    packagegroup-fsl-tools-gpu \
    packagegroup-fsl-tools-gpu-external \
    ${@bb.utils.contains('BBFILE_COLLECTIONS', 'openembedded-layer', \
                         'packagegroup-fsl-tools-testapps', '', d)} \
    ${@bb.utils.contains('BBFILE_COLLECTIONS', 'openembedded-layer', \
                         'packagegroup-fsl-tools-benchmark', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', \
                         'firmwared', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', \
                         'weston weston-init', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'x11 wayland', \
                         'weston-xwayland xterm', '', d)} \
"
