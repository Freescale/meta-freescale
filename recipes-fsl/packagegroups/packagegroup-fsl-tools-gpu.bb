# Copyright (C) 2012-2014, 2016 Freescale Semiconductor
# Copyright (C) 2015, 2016 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Package group used by FSL Community to add the packages which provide GPU support."
SUMMARY = "FSL Community package group - tools/gpu"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

SOC_TOOLS_GPU = ""

# i.MX6 SoloLite does not support apitrace because of its dependency on gles2.
SOC_TOOLS_GPU:imxgpu2d = " \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', '', \
       bb.utils.contains('DISTRO_FEATURES',     'x11', 'xserver-xorg-extension-viv-autohdmi', \
                                                       '', d), d)} \
"

SOC_TOOLS_GPU:append:imxgpu3d = " \
    imx-gpu-apitrace \
"

SOC_TOOLS_GPU:append:imxgpu = " \
    imx-gpu-sdk \
    imx-gpu-viv-tools \
"

RDEPENDS:${PN} = " \
    ${SOC_TOOLS_GPU} \
"
