EXTRA_OECONF_IMX          = ""

# what vivante driver does libsdl2 mean? Anyway it fails with missing functions as
# VIVANTE_Create VIVANTE_GLES_GetProcAddress VIVANTE_GLES_UnloadLibrary ...
EXTRA_OECONF_IMX_imxgpu2d = "--disable-video-vivante"

EXTRA_OECONF_append       = " ${EXTRA_OECONF_IMX}"
