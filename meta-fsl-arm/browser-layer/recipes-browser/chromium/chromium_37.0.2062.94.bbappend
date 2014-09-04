include chromium-imx.inc

CHROMIUM_IMX_COMMON_PATCHES += "file://${PATCH_BASE_DIR}/common/0001-Enable-share-group-workaround-for-Vivante-GPUs.patch \
                                file://${PATCH_BASE_DIR}/common/0002-Add-VPU-video-decode-accelerator-to-Chromium-37-GPU-.patch"
CHROMIUM_IMX_WAYLAND_PATCHES += "file://${PATCH_BASE_DIR}/wayland/0001-Modify-eglwayland-versions-for-Vivante-GPUs.patch"
