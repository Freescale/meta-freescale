include chromium-imx.inc

CHROMIUM_IMX_BRANCH = "chromium-40"
CHROMIUM_IMX_SRCREV = "af13a3dbdfdb4b162a9f9692761216e5d6204e57"
CHROMIUM_IMX_COMMON_PATCHES += "file://${PATCH_BASE_DIR}/common/0001-Enable-share-group-workaround-for-Vivante-GPUs.patch"

VPU_PATCHES = "file://${PATCH_BASE_DIR}/common/0002-Add-VPU-video-decode-accelerator-to-Chromium-GPU-.patch"

CHROMIUM_IMX_VPU_PATCHES_mx6q += "${VPU_PATCHES}"
CHROMIUM_IMX_VPU_PATCHES_mx6dl += "${VPU_PATCHES}"

CHROMIUM_IMX_WAYLAND_PATCHES += "file://${PATCH_BASE_DIR}/wayland/0001-Modify-eglwayland-versions-for-Vivante-GPUs.patch"
