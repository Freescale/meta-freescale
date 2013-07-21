# Copyright (C) 2011-2013 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

include linux-imx.inc

PR = "${INC_PR}.14"

COMPATIBLE_MACHINE = "(mx6)"

# Revision of 4.0.0 branch
SRCREV = "3383d801188adc582b849c6fd1fe1c53be7dbaa9"
LOCALVERSION = "-4.0.0+yocto"

# GPU support patches
SRC_URI += "file://drm-vivante-Add-00-sufix-in-returned-bus-Id.patch \
            file://0001-ENGR00255688-4.6.9p11.1-gpu-GPU-Kernel-driver-integr.patch \
            file://0002-ENGR00265465-gpu-Add-global-value-for-minimum-3D-clo.patch \
            file://0003-ENGR00261814-4-gpu-use-new-PU-power-on-off-interface.patch \
            file://0004-ENGR00264288-1-GPU-Integrate-4.6.9p12-release-kernel.patch \
            file://0005-ENGR00264275-GPU-Correct-suspend-resume-calling-afte.patch \
            file://0006-ENGR00265130-gpu-Correct-section-mismatch-in-gpu-ker.patch"
