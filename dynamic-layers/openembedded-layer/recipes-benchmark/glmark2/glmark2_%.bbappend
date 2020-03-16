# Only _mx8 machine do provide virtual/libgbm required for any drm* flavour
DRM-REMOVE_imxgpu = "drm-gl drm-gles2"
DRM-REMOVE_imxgpu_mx8 = ""
PACKAGECONFIG_remove = "${DRM-REMOVE}"
