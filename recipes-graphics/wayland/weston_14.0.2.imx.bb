# This recipe is for the i.MX fork of weston. For ease of
# maintenance, the top section is a verbatim copy of an OE-core
# recipe. The second section customizes the recipe for i.MX.

########### OE-core copy ##################
# Upstream hash: 8996690a79ac42a1dee6d041eeb1c1fe29fdac84

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

SRC_URI[sha256sum] = "a8150505b126a59df781fe8c30c8e6f87da7013e179039eb844a5bbbcc7c79b3"

UPSTREAM_CHECK_URI = "https://gitlab.freedesktop.org/wayland/weston/-/tags"
UPSTREAM_CHECK_REGEX = "releases/(?P<pver>\d+\.\d+\.(?!9\d+)\d+)"

inherit meson pkgconfig useradd

# depends on virtual/egl
#
require ${THISDIR}/required-distro-features.inc

DEPENDS = "libxkbcommon gdk-pixbuf pixman cairo glib-2.0"
DEPENDS += "wayland wayland-protocols libinput virtual/egl pango wayland-native libdisplay-info"

LDFLAGS += "${@bb.utils.contains('DISTRO_FEATURES', 'lto', '-Wl,-z,undefs', '', d)}"

WESTON_MAJOR_VERSION = "${@'.'.join(d.getVar('PV').split('.')[0:1])}"

EXTRA_OEMESON += "-Dpipewire=false -Dtests=false"

PACKAGECONFIG ??= "${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'kms wayland egl clients', '', d)} \
                   ${@bb.utils.contains('DISTRO_FEATURES', 'x11 wayland', 'xwayland', '', d)} \
                   ${@bb.utils.filter('DISTRO_FEATURES', 'systemd x11', d)} \
                   ${@bb.utils.contains_any('DISTRO_FEATURES', 'wayland x11', '', 'headless', d)} \
                   image-jpeg \
                   screenshare \
                   shell-desktop \
                   shell-fullscreen \
                   shell-ivi \
                   shell-kiosk \
                   "

# Can be 'damage', 'im', 'egl', 'shm', 'touch', 'dmabuf-feedback', 'dmabuf-v4l', 'dmabuf-egl' or 'all'
SIMPLECLIENTS ?= "all"

#
# Compositor choices
#
# Weston on KMS
PACKAGECONFIG[kms] = "-Dbackend-drm=true,-Dbackend-drm=false,drm udev seatd virtual/egl virtual/libgles2 virtual/libgbm mtdev"
# Weston on Wayland (nested Weston)
PACKAGECONFIG[wayland] = "-Dbackend-wayland=true,-Dbackend-wayland=false,virtual/egl virtual/libgles2"
# Weston on X11
PACKAGECONFIG[x11] = "-Dbackend-x11=true,-Dbackend-x11=false,virtual/libx11 libxcb libxcursor"
# Headless Weston
PACKAGECONFIG[headless] = "-Dbackend-headless=true,-Dbackend-headless=false"
# Weston on RDP
PACKAGECONFIG[rdp] = "-Dbackend-rdp=true,-Dbackend-rdp=false,freerdp,freerdp"
# VA-API desktop recorder
PACKAGECONFIG[vaapi] = "-Dbackend-drm-screencast-vaapi=true,-Dbackend-drm-screencast-vaapi=false,libva"
# Weston with EGL support
PACKAGECONFIG[egl] = "-Drenderer-gl=true,-Drenderer-gl=false,virtual/egl"
# Weston with lcms support
PACKAGECONFIG[lcms] = "-Dcolor-management-lcms=true,-Dcolor-management-lcms=false,lcms"
# Weston with webp support
PACKAGECONFIG[webp] = "-Dimage-webp=true,-Dimage-webp=false,libwebp"
# Weston with systemd support
PACKAGECONFIG[systemd] = "-Dsystemd=true,-Dsystemd=false,systemd dbus"
# Weston with Xwayland support (requires X11 and Wayland)
PACKAGECONFIG[xwayland] = "-Dxwayland=true,-Dxwayland=false,libxcb libxcursor xwayland"
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
# Kiosk shell
PACKAGECONFIG[shell-kiosk] = "-Dshell-kiosk=true,-Dshell-kiosk=false"
# JPEG image loading support
PACKAGECONFIG[image-jpeg] = "-Dimage-jpeg=true,-Dimage-jpeg=false, jpeg"
# screencasting via PipeWire
PACKAGECONFIG[pipewire] = "-Dbackend-pipewire=true,-Dbackend-pipewire=false,pipewire"
# VNC remote screensharing
PACKAGECONFIG[vnc] = "-Dbackend-vnc=true,-Dbackend-vnc=false,neatvnc"


do_install:append() {
	# Weston doesn't need the .la files to load modules, so wipe them
	rm -f ${D}/${libdir}/libweston-${WESTON_MAJOR_VERSION}/*.la

	# If X11, ship a desktop file to launch it
	if [ "${@bb.utils.filter('DISTRO_FEATURES', 'x11', d)}" ]; then
		install -d ${D}${datadir}/applications
		install ${UNPACKDIR}/weston.desktop ${D}${datadir}/applications

		install -d ${D}${datadir}/icons/hicolor/48x48/apps
		install ${UNPACKDIR}/weston.png ${D}${datadir}/icons/hicolor/48x48/apps
	fi

	if [ "${@bb.utils.contains('PACKAGECONFIG', 'xwayland', 'yes', 'no', d)}" = "yes" ]; then
		install -Dm 644 ${UNPACKDIR}/xwayland.weston-start ${D}${datadir}/weston-start/xwayland
	fi

	if [ "${@bb.utils.contains('PACKAGECONFIG', 'systemd', 'yes', 'no', d)}" = "yes" ]; then
		install -Dm 644 ${UNPACKDIR}/systemd-notify.weston-start ${D}${datadir}/weston-start/systemd-notify
	fi

	if [ "${@bb.utils.contains('PACKAGECONFIG', 'launch', 'yes', 'no', d)}" = "yes" ]; then
		chmod u+s ${D}${bindir}/weston-launch
	fi
}

PACKAGES += "${@bb.utils.contains('PACKAGECONFIG', 'xwayland', '${PN}-xwayland', '', d)} \
             libweston-${WESTON_MAJOR_VERSION} ${PN}-examples"

FILES:${PN}-dev += "${libdir}/${BPN}/libexec_weston.so"
FILES:${PN} = "${sysconfdir} ${bindir}/weston ${bindir}/weston-terminal ${bindir}/weston-info ${bindir}/weston-launch ${bindir}/wcap-decode ${libexecdir} ${libdir}/${BPN}/*.so* ${datadir}"

FILES:libweston-${WESTON_MAJOR_VERSION} = "${libdir}/lib*${SOLIBS} ${libdir}/libweston-${WESTON_MAJOR_VERSION}/*.so"
SUMMARY:libweston-${WESTON_MAJOR_VERSION} = "Helper library for implementing 'wayland window managers'."

FILES:${PN}-examples = "${bindir}/*"

FILES:${PN}-xwayland = "${libdir}/libweston-${WESTON_MAJOR_VERSION}/xwayland.so"
RDEPENDS:${PN}-xwayland += "xwayland"

RDEPENDS:${PN} += "xkeyboard-config"
RRECOMMENDS:${PN} = "weston-init liberation-fonts"
RDEPENDS:${PN}-dev += "wayland-protocols-dev"

USERADD_PACKAGES = "${PN}"
GROUPADD_PARAM:${PN} = "--system weston-launch"

########### End of OE-core copy ###########

########### i.MX overrides ################

SUMMARY = "Weston, a Wayland compositor, i.MX fork"
LIC_FILES_CHKSUM:remove = "file://COPYING;md5=d79ee9e66bb0f95d3386a7acae780b70"
LIC_FILES_CHKSUM +=       "file://LICENSE;md5=d79ee9e66bb0f95d3386a7acae780b70"

DEFAULT_PREFERENCE = "-1"

SRC_URI:remove = "https://gitlab.freedesktop.org/wayland/weston/-/releases/${PV}/downloads/${BPN}-${PV}.tar.xz"
SRC_URI:prepend = "${WESTON_SRC};branch=${SRCBRANCH} "
WESTON_SRC ?= "git://github.com/nxp-imx/weston-imx.git;protocol=https"
S = "${WORKDIR}/git"
SRCBRANCH = "weston-imx-14.0.2"
SRCREV = "c267ba8e6eed3a824e042a200bcc1b4c370ba88f"

PACKAGECONFIG:remove = "${PACKAGECONFIG_IMX_REMOVALS}"
PACKAGECONFIG_IMX_REMOVALS ?= "wayland x11"

PACKAGECONFIG:append = " ${PACKAGECONFIG_G2D}"
PACKAGECONFIG_G2D               ??= ""
PACKAGECONFIG_G2D:imxgpu2d      ??= "imxg2d"
PACKAGECONFIG_G2D:mx93-nxp-bsp  ??= "imxg2d"
PACKAGECONFIG_G2D:mx943-nxp-bsp ??= "imxg2d"

# Weston with i.MX G2D renderer
PACKAGECONFIG[imxg2d] = "-Drenderer-g2d=true,-Drenderer-g2d=false,virtual/libg2d"

# links with imx-gpu libs which are pre-built for glibc
# gcompat will address it during runtime
LDFLAGS:append:imxgpu:libc-musl = " -Wl,--allow-shlib-undefined"

PACKAGE_ARCH = "${MACHINE_SOCARCH}"
COMPATIBLE_MACHINE = "(imx-nxp-bsp)"

########### End of i.MX overrides #########
