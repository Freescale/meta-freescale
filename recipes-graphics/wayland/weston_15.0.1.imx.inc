# i.MX customization for weston_15.0.1.imx.bb.
# Not a standalone recipe: required by that .bb, and must stay beside
# it. Keeping it separate lets the .bb remain a verbatim copy of the
# OE-core original.

SUMMARY = "Weston, a Wayland compositor, i.MX fork"
LIC_FILES_CHKSUM:remove = "file://COPYING;md5=d79ee9e66bb0f95d3386a7acae780b70"
LIC_FILES_CHKSUM += "file://LICENSE;md5=d79ee9e66bb0f95d3386a7acae780b70"

DEFAULT_PREFERENCE = "-1"

SRC_URI:remove = "https://gitlab.freedesktop.org/wayland/weston/-/releases/${PV}/downloads/${BPN}-${PV}.tar.xz"
SRC_URI:prepend = "${WESTON_SRC};branch=${SRCBRANCH} "
WESTON_SRC ?= "git://github.com/nxp-imx/weston-imx.git;protocol=https"
SRCBRANCH = "weston-imx-15.0.1"
SRCREV = "82cf0ec8f3853e2cb754637da957dcd537c08be9"

PACKAGECONFIG:remove = "${PACKAGECONFIG_IMX_REMOVALS}"
PACKAGECONFIG_IMX_REMOVALS ?= "vulkan wayland x11"

PACKAGECONFIG:append = " ${PACKAGECONFIG_G2D} ${PACKAGECONFIG_PIPEWIRE}"
PACKAGECONFIG_G2D ??= ""
PACKAGECONFIG_G2D:imxgpu2d ??= "imxg2d"
PACKAGECONFIG_G2D:mx93-nxp-bsp ??= "imxg2d"
PACKAGECONFIG_G2D:mx943-nxp-bsp ??= "imxg2d"

PACKAGECONFIG_PIPEWIRE ??= ""
PACKAGECONFIG_PIPEWIRE:mx8-nxp-bsp ??= "pipewire"
PACKAGECONFIG_PIPEWIRE:mx9-nxp-bsp ??= "pipewire"

# Weston with i.MX G2D renderer
PACKAGECONFIG[imxg2d] = "-Drenderer-g2d=true,-Drenderer-g2d=false,virtual/libg2d"

# links with imx-gpu libs which are pre-built for glibc
# gcompat will address it during runtime
LDFLAGS:append:imxgpu:libc-musl = " -Wl,--allow-shlib-undefined"

PACKAGE_ARCH = "${MACHINE_SOCARCH}"
COMPATIBLE_MACHINE = "(imx-nxp-bsp)"
