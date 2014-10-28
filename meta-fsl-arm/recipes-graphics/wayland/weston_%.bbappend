FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append_mx6 = " file://0001-ENGR00314805-1-Add-Vivante-EGL-support.patch \
             file://0002-ENGR00314805-2-Add-Vivante-GAL2D-support.patch \
             file://0003-Distorted-line-and-shadow-if-use-2d-com.patch \
             file://0004-Desktop-shell-Don-t-assume-there-is-a-pointer.patch \
             file://0005-Enable-GAL2D-compositor-in-SoloLite.patch \
             file://0006-Change-GAL2D-compositor-to-be-default-i.patch \
             "
PACKAGECONFIG_append_mx6q = " cairo-glesv2"
PACKAGECONFIG_append_mx6dl = " cairo-glesv2"
PACKAGECONFIG_remove_mx6sl = "egl"

EXTRA_OECONF_append_mx6 = " \
    --disable-libunwind \
    --disable-xwayland-test \
    WESTON_NATIVE_BACKEND=fbdev-backend.so \
"
EXTRA_OEMAKE_append_mx6 = " \
    COMPOSITOR_CFLAGS="-I ${STAGING_INCDIR}/pixman-1 -DLINUX=1 -DEGL_API_FB -DEGL_API_WL" \
    FB_COMPOSITOR_CFLAGS="-DLINUX=1 -DEGL_API_FB -DEGL_API_WL" \
    SIMPLE_EGL_CLIENT_CFLAGS="-DLINUX -DEGL_API_FB -DEGL_API_WL" \
    EGL_TESTS_CFLAGS="-DLINUX -DEGL_API_FB -DEGL_API_WL" \
    CLIENT_CFLAGS="-I ${STAGING_INCDIR}/cairo -I ${STAGING_INCDIR}/pixman-1 -DLINUX -DEGL_API_FB -DEGL_API_WL" \
"
EXTRA_OEMAKE_append_mx6q = " \
    COMPOSITOR_LIBS="-lGLESv2 -lEGL -lGAL -lwayland-server -lxkbcommon -lpixman-1" \
    FB_COMPOSITOR_LIBS="-lGLESv2 -lEGL -lwayland-server -lxkbcommon" \
"
EXTRA_OEMAKE_append_mx6dl = " \
    COMPOSITOR_LIBS="-lGLESv2 -lEGL -lGAL -lwayland-server -lxkbcommon -lpixman-1" \
    FB_COMPOSITOR_LIBS="-lGLESv2 -lEGL -lwayland-server -lxkbcommon" \
"
EXTRA_OEMAKE_append_mx6sl = " \
    COMPOSITOR_LIBS="-lEGL -lGAL -lwayland-server -lxkbcommon -lpixman-1" \
    FB_COMPOSITOR_LIBS="-lEGL -lwayland-server -lxkbcommon" \
"
