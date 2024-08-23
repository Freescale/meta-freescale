DEPENDS:append:imxgpu = " virtual/egl"

do_install:append:imxgpu() {
    # imx-gpu-viv and mali-imx both provide /usr/include/KHR, so drop the mesa-gl one
    rm -rf ${D}${includedir}/KHR
}

COMPATIBLE_MACHINE:imxgpu = "(imxgpu)"
