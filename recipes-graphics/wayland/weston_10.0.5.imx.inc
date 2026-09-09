# i.MX customization for weston_10.0.5.imx.bb.
# Not a standalone recipe: required by that .bb, and must stay beside
# it. Keeping it separate lets the .bb remain a verbatim copy of the
# OE-core original.

SUMMARY = "Weston, a Wayland compositor, i.MX fork"

LIC_FILES_CHKSUM:remove = "file://COPYING;md5=d79ee9e66bb0f95d3386a7acae780b70"
LIC_FILES_CHKSUM:append = " file://LICENSE;md5=d79ee9e66bb0f95d3386a7acae780b70"

DEFAULT_PREFERENCE = "-1"

SRC_URI:remove = "https://gitlab.freedesktop.org/wayland/weston/-/releases/${PV}/downloads/${BPN}-${PV}.tar.xz"
SRC_URI:prepend = "${WESTON_SRC};branch=${SRCBRANCH} "
WESTON_SRC ?= "git://github.com/nxp-imx/weston-imx.git;protocol=https"
SRC_URI += "file://0001-Revert-protocol-no-found-wayland-scanner-with-Yocto-.patch \
            file://0001-g2d-renderer.c-Include-sys-stat.h.patch"
SRCBRANCH = "weston-imx-10.0.5"
SRCREV = "5223a3c86177709d25f86a96622c0829da955a0e"

# Disable OpenGL for parts with GPU support for 2D but not 3D
REQUIRED_DISTRO_FEATURES = "opengl"
REQUIRED_DISTRO_FEATURES:imxgpu2d = ""
REQUIRED_DISTRO_FEATURES:imxgpu3d = "opengl"
PACKAGECONFIG_OPENGL = "opengl"
PACKAGECONFIG_OPENGL:imxgpu2d = ""
PACKAGECONFIG_OPENGL:imxgpu3d = "opengl"

PACKAGECONFIG_IMX_REMOVALS ?= "wayland x11"
PACKAGECONFIG:remove = "${PACKAGECONFIG_IMX_REMOVALS}"
PACKAGECONFIG:append = " ${@bb.utils.filter('DISTRO_FEATURES', '${PACKAGECONFIG_OPENGL}', d)}"

PACKAGECONFIG:remove:imxfbdev = "kms"
PACKAGECONFIG:append:imxfbdev = " fbdev clients"
PACKAGECONFIG:append:imxgpu = " imxgpu"
PACKAGECONFIG:append:imxgpu2d = " imxg2d"

SIMPLECLIENTS:imxfbdev = "damage,im,egl,shm,touch,dmabuf-v4l"

# Override
PACKAGECONFIG[xwayland] = "-Dxwayland=true,-Dxwayland=false,libxcursor xwayland"
# Weston with i.MX GPU support
PACKAGECONFIG[imxgpu] = "-Dimxgpu=true,-Dimxgpu=false,virtual/egl"
# Weston with i.MX G2D renderer
PACKAGECONFIG[imxg2d] = "-Drenderer-g2d=true,-Drenderer-g2d=false,virtual/libg2d"
# Weston with OpenGL support
PACKAGECONFIG[opengl] = "-Dopengl=true,-Dopengl=false"

PACKAGECONFIG[fbdev] = "-Dbackend-fbdev=true,-Dbackend-fbdev=false,udev mtdev libdrm"
EXTRA_OEMESON:append:imxfbdev = " -Dbackend-default=fbdev"

EXTRA_OEMESON += "-Ddeprecated-wl-shell=true"

# links with imx-gpu libs which are pre-built for glibc
# gcompat will address it during runtime
LDFLAGS:append:imxgpu:libc-musl = " -Wl,--allow-shlib-undefined"

PACKAGE_ARCH = "${MACHINE_SOCARCH}"
COMPATIBLE_MACHINE = "(imx-nxp-bsp)"
