FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append_imxgpu3d = " \
    file://0001-MGS-2352-ccc-Add-GPU-VIV-support-for-weston-1.11.patch     \
    file://0002-MGS-2521-ccc-Enable-g2d-renderer-for-weston-1.11.patch     \
    file://0003-MGS-1783-xwld-Add-clone-mode-support-for-multi-displ.patch \
    file://0004-MGS-1668-xwld-System-can-not-boot-up-to-desktop.patch      \
    file://0005-MGS-1724-xwld-G2D-compositor-build-failed-in-slevk-b.patch \
    file://0006-Link-compositor-to-egl.patch                               \
"

SRC_URI_append = " \
    file://0007-xwayland-Fix-crash-when-run-with-no-input-device.patch     \
"

# The 'egl' configuration of weston requires gles support, and consideration
# must be taken for the different SoC capabilities:
# - For SoCs with 3d support, imx-gpu-viv provides hardware-accelerated
#   egl and gles, so weston egl configuration is enabled.
# - For SoCs with VG2D, like i.MX 6SoloLite, imx-gpu-viv provides
#   hardware-accelerated egl but does not provide a compatible software
#   version of gles, so weston egl configuration is disabled.
# - For SoCs with no GPU, mesa provides software implementations of egl
#   and gles, so weston egl configuration is enabled.
PACKAGECONFIG_IMX_TO_APPEND = ""
PACKAGECONFIG_IMX_TO_APPEND_imxgpu3d = "cairo-glesv2"
PACKAGECONFIG_IMX_TO_REMOVE = ""
PACKAGECONFIG_IMX_TO_REMOVE_imxgpu2d = "egl"
PACKAGECONFIG_IMX_TO_REMOVE_imxgpu3d = ""

PACKAGECONFIG_append = " ${PACKAGECONFIG_IMX_TO_APPEND}"
PACKAGECONFIG_remove = " ${PACKAGECONFIG_IMX_TO_REMOVE}"


EXTRA_OECONF_IMX_COMMON = " \
    --disable-libunwind \
    --disable-xwayland-test \
    WESTON_NATIVE_BACKEND=fbdev-backend.so \
"
EXTRA_OECONF_IMX          = ""
EXTRA_OECONF_IMX_imxpxp   = "${EXTRA_OECONF_IMX_COMMON}"
EXTRA_OECONF_IMX_imxgpu2d = "${EXTRA_OECONF_IMX_COMMON}"

EXTRA_OECONF_append = " ${EXTRA_OECONF_IMX}"
