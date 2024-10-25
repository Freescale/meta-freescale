# Only _mx8 machine do provide virtual/libgbm required for any drm* flavour
DRM-REMOVE:imxgpu = "drm-gl drm-gles2"
DRM-REMOVE:imxgpu:mx8-nxp-bsp = ""
DRM-REMOVE:imxgpu:mx95-nxp-bsp = ""
PACKAGECONFIG:remove = "${DRM-REMOVE}"
