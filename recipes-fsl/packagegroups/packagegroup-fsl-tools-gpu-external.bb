# Copyright (C) 2014, 2016 Freescale Semiconductor
# Copyright (C) 2015, 2016 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)
DESCRIPTION = "Package group used by FSL Community to provide graphic packages used \
to test the several hardware accelerated graphics APIs including packages not \
provided by Freescale."
SUMMARY = "FSL Community Package group - tools/gpu/external"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

SOC_TOOLS_GPU_X11 = ""
SOC_TOOLS_GPU_X11:imxgpu2d = " mesa-demos glmark2 gtkperf"

SOC_TOOLS_GPU_FB = ""

SOC_TOOLS_GPU_WAYLAND = ""

SOC_TOOLS_GPU_XWAYLAND = ""
SOC_TOOLS_GPU_XWAYLAND:imxgpu2d = "mesa-demos gtkperf"

RDEPENDS:${PN} = " \
    ${@bb.utils.contains("DISTRO_FEATURES", "x11 wayland", "${SOC_TOOLS_GPU_XWAYLAND}", \
       bb.utils.contains("DISTRO_FEATURES",     "wayland", "${SOC_TOOLS_GPU_WAYLAND}", \
       bb.utils.contains("DISTRO_FEATURES",         "x11", "${SOC_TOOLS_GPU_X11}", \
                                                           "${SOC_TOOLS_GPU_FB}", d), d), d)} \
"
