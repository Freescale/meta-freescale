FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append_imxgpu3d = " \
    file://0002-MGS-1111-Add-GPU-VIV-suport-for-wayland-and-weston-1.patch \
    file://0003-MGS-1192-xwld-g2d-compositor-dose-not-work.patch \
    file://0004-MGS-1235-Fix-setenv-and-clear-environments.patch \
    file://0005-MGS-1252-Fix-for-Qt5_CinematicExperience-will-meet-s.patch \
    file://0006-MGS-1236-imx6qp-imx6dl-First-frame-distored-when-som.patch \
    file://0007-MGS-1236-1-imx6qp-imx6dl-First-frame-distored-when-s.patch \
    file://0009-MGS-1284-xwld-Re-implement-weston-2d-renderer-with-p.patch \
    file://0010-MGS-1284-1-xwld-Re-implement-weston-2d-renderer-with.patch \
    file://0011-MGS-1724-xwld-G2D-compositor-build-failed-in-slevk-b.patch \
    file://0012-MGS-1783-xwld-Add-clone-mode-support-for-multi-displ.patch \
    file://0013-MGS-1945-Use-common-API-to-support-G2d-compositor.patch    \
    file://0014-MGS-1987-Get-stride-from-the-FB-buffe.patch                \
    file://0015-MGS-2221-imx-171-Fix-weston-build-failed.patch             \
    file://0016-Link-compositor-to-egl.patch                               \
    file://0017-MGS-2343-ccc-XWLD-T3DStressTest_Wayland-displays-abn.patch \
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
