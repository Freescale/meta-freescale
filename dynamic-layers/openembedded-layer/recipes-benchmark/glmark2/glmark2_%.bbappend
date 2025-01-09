# 6 and 7 Vivante do not provide virtual/libgbm required for any drm* flavour
DRM-REMOVE                    = ""
DRM-REMOVE:imxgpu:mx6-nxp-bsp = "drm-gl drm-gles2"
DRM-REMOVE:imxgpu:mx7-nxp-bsp = "drm-gl drm-gles2"
PACKAGECONFIG:remove = "${DRM-REMOVE}"
