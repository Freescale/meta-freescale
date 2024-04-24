# This recipe is for the i.MX fork of weston. For ease of
# maintenance, the top section is a verbatim copy of an OE-core
# recipe. The second section customizes the recipe for i.MX.

########### OE-core copy ##################
# Upstream hash: 4b42fd87da290ddea098605aea3a5cce1fb432a7

SUMMARY = "Weston, a Wayland compositor"
DESCRIPTION = "Weston is the reference implementation of a Wayland compositor"
HOMEPAGE = "http://wayland.freedesktop.org"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=d79ee9e66bb0f95d3386a7acae780b70 \
                    file://libweston/compositor.c;endline=27;md5=eb6d5297798cabe2ddc65e2af519bcf0 \
                    "

SRC_URI = "https://gitlab.freedesktop.org/wayland/weston/-/releases/${PV}/downloads/${BPN}-${PV}.tar.xz \
           file://weston.png \
           file://weston.desktop \
           file://xwayland.weston-start \
           file://systemd-notify.weston-start \
           "

SRC_URI[sha256sum] = "89646ca0d9f8d413c2767e5c3828eaa3fa149c2a105b3729a6894fa7cf1549e7"

UPSTREAM_CHECK_URI = "https://wayland.freedesktop.org/releases.html"
UPSTREAM_CHECK_REGEX = "weston-(?P<pver>\d+\.\d+\.(?!9\d+)\d+)"

inherit meson pkgconfig useradd

# depends on virtual/egl
#
require ${THISDIR}/required-distro-features.inc

DEPENDS = "libxkbcommon gdk-pixbuf pixman cairo glib-2.0"
DEPENDS += "wayland wayland-protocols libinput virtual/egl pango wayland-native"

LDFLAGS += "${@bb.utils.contains('DISTRO_FEATURES', 'lto', '-Wl,-z,undefs', '', d)}"

WESTON_MAJOR_VERSION = "${@'.'.join(d.getVar('PV').split('.')[0:1])}"

EXTRA_OEMESON += "-Dpipewire=false"

PACKAGECONFIG ??= "${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'kms wayland egl clients', '', d)} \
                   ${@bb.utils.contains('DISTRO_FEATURES', 'x11 wayland', 'xwayland', '', d)} \
                   ${@bb.utils.filter('DISTRO_FEATURES', 'systemd x11', d)} \
                   ${@bb.utils.contains_any('DISTRO_FEATURES', 'wayland x11', '', 'headless', d)} \
                   ${@oe.utils.conditional('VIRTUAL-RUNTIME_init_manager', 'sysvinit', 'launcher-libseat', '', d)} \
                   image-jpeg \
                   screenshare \
                   shell-desktop \
                   shell-fullscreen \
                   shell-ivi"

# Can be 'damage', 'im', 'egl', 'shm', 'touch', 'dmabuf-feedback', 'dmabuf-v4l', 'dmabuf-egl' or 'all'
SIMPLECLIENTS ?= "all"

#
# Compositor choices
#
# Weston on KMS
PACKAGECONFIG[kms] = "-Dbackend-drm=true,-Dbackend-drm=false,drm udev virtual/egl virtual/libgles2 virtual/libgbm mtdev"
# Weston on Wayland (nested Weston)
PACKAGECONFIG[wayland] = "-Dbackend-wayland=true,-Dbackend-wayland=false,virtual/egl virtual/libgles2"
# Weston on X11
PACKAGECONFIG[x11] = "-Dbackend-x11=true,-Dbackend-x11=false,virtual/libx11 libxcb libxcb libxcursor cairo"
# Headless Weston
PACKAGECONFIG[headless] = "-Dbackend-headless=true,-Dbackend-headless=false"
# Weston on framebuffer
PACKAGECONFIG[fbdev] = "-Ddeprecated-backend-fbdev=true,-Ddeprecated-backend-fbdev=false,udev mtdev"
# Weston on RDP
PACKAGECONFIG[rdp] = "-Dbackend-rdp=true,-Dbackend-rdp=false,freerdp"
# weston-launch
PACKAGECONFIG[launch] = "-Ddeprecated-weston-launch=true,-Ddeprecated-weston-launch=false,drm"
# VA-API desktop recorder
PACKAGECONFIG[vaapi] = "-Dbackend-drm-screencast-vaapi=true,-Dbackend-drm-screencast-vaapi=false,libva"
# Weston with EGL support
PACKAGECONFIG[egl] = "-Drenderer-gl=true,-Drenderer-gl=false,virtual/egl"
# Weston with lcms support
PACKAGECONFIG[lcms] = "-Dcolor-management-lcms=true,-Dcolor-management-lcms=false,lcms"
# Weston with webp support
PACKAGECONFIG[webp] = "-Dimage-webp=true,-Dimage-webp=false,libwebp"
# Weston with systemd-login support
PACKAGECONFIG[systemd] = "-Dsystemd=true -Dlauncher-logind=true,-Dsystemd=false -Dlauncher-logind=false,systemd dbus"
# Weston with Xwayland support (requires X11 and Wayland)
PACKAGECONFIG[xwayland] = "-Dxwayland=true,-Dxwayland=false"
# colord CMS support
PACKAGECONFIG[colord] = "-Dcolor-management-colord=true,-Dcolor-management-colord=false,colord"
# Clients support
PACKAGECONFIG[clients] = "-Dsimple-clients=${SIMPLECLIENTS} -Ddemo-clients=true,-Dsimple-clients= -Ddemo-clients=false"
# Virtual remote output with GStreamer on DRM backend
PACKAGECONFIG[remoting] = "-Dremoting=true,-Dremoting=false,gstreamer1.0 gstreamer1.0-plugins-base"
# Weston with screen-share support
PACKAGECONFIG[screenshare] = "-Dscreenshare=true,-Dscreenshare=false"
# Traditional desktop shell
PACKAGECONFIG[shell-desktop] = "-Dshell-desktop=true,-Dshell-desktop=false"
# Fullscreen shell
PACKAGECONFIG[shell-fullscreen] = "-Dshell-fullscreen=true,-Dshell-fullscreen=false"
# In-Vehicle Infotainment (IVI) shell
PACKAGECONFIG[shell-ivi] = "-Dshell-ivi=true,-Dshell-ivi=false"
# JPEG image loading support
PACKAGECONFIG[image-jpeg] = "-Dimage-jpeg=true,-Dimage-jpeg=false, jpeg"
# support libseat based launch
PACKAGECONFIG[launcher-libseat] = "-Dlauncher-libseat=true,-Dlauncher-libseat=false,seatd"

do_install:append() {
	# Weston doesn't need the .la files to load modules, so wipe them
	rm -f ${D}/${libdir}/libweston-${WESTON_MAJOR_VERSION}/*.la

	# If X11, ship a desktop file to launch it
	if [ "${@bb.utils.filter('DISTRO_FEATURES', 'x11', d)}" ]; then
		install -d ${D}${datadir}/applications
		install ${WORKDIR}/weston.desktop ${D}${datadir}/applications

		install -d ${D}${datadir}/icons/hicolor/48x48/apps
		install ${WORKDIR}/weston.png ${D}${datadir}/icons/hicolor/48x48/apps
	fi

	if [ "${@bb.utils.contains('PACKAGECONFIG', 'xwayland', 'yes', 'no', d)}" = "yes" ]; then
		install -Dm 644 ${WORKDIR}/xwayland.weston-start ${D}${datadir}/weston-start/xwayland
	fi

	if [ "${@bb.utils.contains('PACKAGECONFIG', 'systemd', 'yes', 'no', d)}" = "yes" ]; then
		install -Dm 644 ${WORKDIR}/systemd-notify.weston-start ${D}${datadir}/weston-start/systemd-notify
	fi

	if [ "${@bb.utils.contains('PACKAGECONFIG', 'launch', 'yes', 'no', d)}" = "yes" ]; then
		chmod u+s ${D}${bindir}/weston-launch
	fi
}

PACKAGES += "${@bb.utils.contains('PACKAGECONFIG', 'xwayland', '${PN}-xwayland', '', d)} \
             libweston-${WESTON_MAJOR_VERSION} ${PN}-examples"

FILES:${PN}-dev += "${libdir}/${BPN}/libexec_weston.so"
FILES:${PN} = "${bindir}/weston ${bindir}/weston-terminal ${bindir}/weston-info ${bindir}/weston-launch ${bindir}/wcap-decode ${libexecdir} ${libdir}/${BPN}/*.so* ${datadir}"

FILES:libweston-${WESTON_MAJOR_VERSION} = "${libdir}/lib*${SOLIBS} ${libdir}/libweston-${WESTON_MAJOR_VERSION}/*.so"
SUMMARY:libweston-${WESTON_MAJOR_VERSION} = "Helper library for implementing 'wayland window managers'."

FILES:${PN}-examples = "${bindir}/*"

FILES:${PN}-xwayland = "${libdir}/libweston-${WESTON_MAJOR_VERSION}/xwayland.so"
RDEPENDS:${PN}-xwayland += "xwayland"

RDEPENDS:${PN} += "xkeyboard-config"
RRECOMMENDS:${PN} = "weston-init liberation-fonts"
RRECOMMENDS:${PN}-dev += "wayland-protocols"

USERADD_PACKAGES = "${PN}"
GROUPADD_PARAM:${PN} = "--system weston-launch"

########### End of OE-core copy ###########

########### i.MX overrides ################

SUMMARY = "Weston, a Wayland compositor, i.MX fork"

LIC_FILES_CHKSUM:remove = "file://COPYING;md5=d79ee9e66bb0f95d3386a7acae780b70"
LIC_FILES_CHKSUM:append = "file://LICENSE;md5=d79ee9e66bb0f95d3386a7acae780b70"

DEFAULT_PREFERENCE = "-1"

SRC_URI:remove = "https://gitlab.freedesktop.org/wayland/weston/-/releases/${PV}/downloads/${BPN}-${PV}.tar.xz"
SRC_URI:prepend = "git://github.com/nxp-imx/weston-imx.git;protocol=https;branch=${SRCBRANCH} "
SRC_URI += "file://0001-Revert-protocol-no-found-wayland-scanner-with-Yocto-.patch \
            file://0001-g2d-renderer.c-Include-sys-stat.h.patch"
SRCBRANCH = "weston-imx-10.0.5"
SRCREV = "0cc822a1e5a8faea6835a4e9259887d8792b86b4"
S = "${WORKDIR}/git"

# Disable OpenGL for parts with GPU support for 2D but not 3D
REQUIRED_DISTRO_FEATURES          = "opengl"
REQUIRED_DISTRO_FEATURES:imxgpu2d = ""
REQUIRED_DISTRO_FEATURES:imxgpu3d = "opengl"
PACKAGECONFIG_OPENGL              = "opengl"
PACKAGECONFIG_OPENGL:imxgpu2d     = ""
PACKAGECONFIG_OPENGL:imxgpu3d     = "opengl"

PACKAGECONFIG_IMX_REMOVALS ?= "wayland x11"
PACKAGECONFIG:remove = "${PACKAGECONFIG_IMX_REMOVALS}"
PACKAGECONFIG:append = " ${@bb.utils.filter('DISTRO_FEATURES', '${PACKAGECONFIG_OPENGL}', d)}"

PACKAGECONFIG:remove:imxfbdev = "kms"
PACKAGECONFIG:append:imxfbdev = " fbdev clients"
PACKAGECONFIG:append:imxgpu   = " imxgpu"
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
COMPATIBLE_MACHINE = "(imxfbdev|imxgpu)"

########### End of i.MX overrides #########
