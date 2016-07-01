FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append_imxgpu2d = " \
    file://0002-MGS-1111-Add-GPU-VIV-suport-for-wayland-and-weston-1.patch \
    file://0003-MGS-1192-xwld-g2d-compositor-dose-not-work.patch \
    file://0004-MGS-1235-Fix-setenv-and-clear-environments.patch \
    file://0005-MGS-1252-Fix-for-Qt5_CinematicExperience-will-meet-s.patch \
    file://0006-MGS-1236-imx6qp-imx6dl-First-frame-distored-when-som.patch \
    file://0007-MGS-1236-1-imx6qp-imx6dl-First-frame-distored-when-s.patch \
"

PACKAGECONFIG_IMX_TO_APPEND = ""
PACKAGECONFIG_IMX_TO_APPEND_imxgpu3d = "cairo-glesv2"
PACKAGECONFIG_IMX_TO_REMOVE = ""
PACKAGECONFIG_IMX_TO_REMOVE_imxpxp   = "egl"
PACKAGECONFIG_IMX_TO_REMOVE_imxgpu2d = ""

PACKAGECONFIG_append = " ${PACKAGECONFIG_IMX_TO_APPEND}"
PACKAGECONFIG_remove = " ${PACKAGECONFIG_IMX_TO_REMOVE}"


EXTRA_OECONF_IMX = " \
    --disable-libunwind \
    --disable-xwayland-test \
    WESTON_NATIVE_BACKEND=fbdev-backend.so \
"

EXTRA_OECONF_append_imxpxp   = " ${EXTRA_OECONF_IMX}"
EXTRA_OECONF_append_imxgpu2d = " ${EXTRA_OECONF_IMX}"

# Use a intermediate step to configure the linking flags
IMXGPU_LIBS = ""
IMXGPU_LIBS_imxgpu2d = " \
    COMPOSITOR_LIBS="-lEGL -lGAL -lwayland-server -lxkbcommon -lpixman-1" \
    FB_COMPOSITOR_LIBS="-lEGL -lwayland-server -lxkbcommon" \
"
IMXGPU_LIBS_imxgpu3d = " \
    COMPOSITOR_LIBS="-lGLESv2 -lEGL -lGAL -lwayland-server -lxkbcommon -lpixman-1" \
    FB_COMPOSITOR_LIBS="-lGLESv2 -lEGL -lwayland-server -lxkbcommon" \
"

# Use the linking flags according to the GPU support
EXTRA_OEMAKE_append_imxgpu2d = " \
    COMPOSITOR_CFLAGS="-I ${STAGING_INCDIR}/pixman-1 -DLINUX=1 -DEGL_API_FB -DEGL_API_WL" \
    FB_COMPOSITOR_CFLAGS="-DLINUX=1 -DEGL_API_FB -DEGL_API_WL" \
    SIMPLE_EGL_CLIENT_CFLAGS="-DLINUX -DEGL_API_FB -DEGL_API_WL" \
    EGL_TESTS_CFLAGS="-DLINUX -DEGL_API_FB -DEGL_API_WL" \
    CLIENT_CFLAGS="-I ${STAGING_INCDIR}/cairo -I ${STAGING_INCDIR}/pixman-1 -DLINUX -DEGL_API_FB -DEGL_API_WL" \
    \
    ${IMXGPU_LIBS} \
"
