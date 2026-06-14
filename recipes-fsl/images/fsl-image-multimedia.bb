DESCRIPTION = "A console-only image that includes gstreamer packages and \
Freescale's multimedia packages (VPU and GPU) when available for the specific \
machine."

IMAGE_FEATURES += "\
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'weston', \
       bb.utils.contains('DISTRO_FEATURES',     'x11', 'x11-base', \
                                                       '', d), d)} \
"

LICENSE = "MIT"

inherit core-image

CORE_IMAGE_EXTRA_INSTALL += " \
    packagegroup-fsl-tools-gpu \
    packagegroup-fsl-gstreamer1.0 \
    packagegroup-imx-tools-audio \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', \
                         'weston weston-init weston-examples \
                          gtk+3-demo', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'x11 wayland', \
                         'weston-xwayland xterm', '', d)} \
"

PACKAGE_IMX_TO_REMOVE = ""
PACKAGE_IMX_TO_REMOVE:imxgpu2d = "gtk+3-demo"
PACKAGE_IMX_TO_REMOVE:imxgpu3d = ""

CORE_IMAGE_EXTRA_INSTALL:remove = "${PACKAGE_IMX_TO_REMOVE}"
