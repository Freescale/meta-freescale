# The oelint.vars.noncoreoverride suppressions below are the layer's
# machine-gated dispatch idiom: none of it can carry an override of its own,
# and all of it is inert off-target. Not parsed in this tree (the trigger
# layer for this dynamic-layers directory is not checked out), so argued from
# the metadata rather than measured.

# 6 and 7 Vivante do not provide virtual/libgbm required for any drm* flavour
# Fallback default for the machine overrides below.
# nooelint: oelint.vars.noncoreoverride
DRM-REMOVE = ""
DRM-REMOVE:imxgpu:mx6-nxp-bsp = "drm-gl drm-gles2"
DRM-REMOVE:imxgpu:mx7-nxp-bsp = "drm-gl drm-gles2"
# No-op off-target: the helper it consumes expands empty.
# nooelint: oelint.vars.noncoreoverride
PACKAGECONFIG:remove = "${DRM-REMOVE}"
