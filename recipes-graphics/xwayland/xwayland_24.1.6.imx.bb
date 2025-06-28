# This recipe copy helps keep meta-freescale master branch compatible
# with Yocto walnascar.
# (From OE-Core rev: 737e612ca36bbdf415a911644eb7592cf9389846847b47fa46dc705bd754d2d7)

SUMMARY = "XWayland is an X Server that runs under Wayland."
DESCRIPTION = "XWayland is an X Server running as a Wayland client, \
and thus is capable of displaying native X11 client applications in a \
Wayland compositor environment. The goal of XWayland is to facilitate \
the transition from X Window System to Wayland environments, providing \
a way to run unported applications in the meantime."
HOMEPAGE = "https://fedoraproject.org/wiki/Changes/XwaylandStandalone"

DEFAULT_PREFERENCE = "-1"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=5df87950af51ac2c5822094553ea1880"

SRC_URI = "https://www.x.org/archive/individual/xserver/${BP_ORIGINAL}.tar.xz"
BP_ORIGINAL = "${BPN}-24.1.6"
SRC_URI[sha256sum] = "737e612ca36bbdf415a911644eb7592cf9389846847b47fa46dc705bd754d2d7"
S = "${UNPACKDIR}/${BP_ORIGINAL}"

UPSTREAM_CHECK_REGEX = "xwayland-(?P<pver>\d+(\.(?!90\d)\d+)+)\.tar"

inherit meson features_check pkgconfig
REQUIRED_DISTRO_FEATURES = "x11 opengl"

DEPENDS += "xorgproto xtrans pixman libxkbfile libxfont2 wayland wayland-native wayland-protocols libdrm libepoxy libxcvt libtirpc"

OPENGL_PKGCONFIGS = "glx glamor dri3"
PACKAGECONFIG ??= "${XORG_CRYPTO} ${XWAYLAND_EI} \
                   ${@bb.utils.contains('DISTRO_FEATURES', 'opengl', '${OPENGL_PKGCONFIGS}', '', d)} \
"
PACKAGECONFIG[dri3] = "-Ddri3=true,-Ddri3=false,libxshmfence"
PACKAGECONFIG[libdecor] = "-Dlibdecor=true,-Dlibdecor=false,libdecor"
PACKAGECONFIG[glx] = "-Dglx=true,-Dglx=false,virtual/libgl virtual/libx11"
PACKAGECONFIG[glamor] = "-Dglamor=true,-Dglamor=false,libepoxy virtual/libgbm,libegl"
PACKAGECONFIG[unwind] = "-Dlibunwind=true,-Dlibunwind=false,libunwind"
PACKAGECONFIG[xinerama] = "-Dxinerama=true,-Dxinerama=false"

# Xorg requires a SHA1 implementation, pick one
XORG_CRYPTO ??= "openssl"
PACKAGECONFIG[openssl] = "-Dsha1=libcrypto,,openssl"
PACKAGECONFIG[nettle] = "-Dsha1=libnettle,,nettle"
PACKAGECONFIG[gcrypt] = "-Dsha1=libgcrypt,,libgcrypt"
XWAYLAND_EI ??= "xwayland_ei_false"
PACKAGECONFIG[xwayland_ei_false] = "-Dxwayland_ei=false"
PACKAGECONFIG[xwayland_ei_portal] = "-Dxwayland_ei=portal,,libei"
PACKAGECONFIG[xwayland_ei_socket] = "-Dxwayland_ei=socket,,libei"

do_install:append() {
    # remove files not needed and clashing with xserver-xorg
    rm -rf ${D}/${libdir}/xorg/
}

FILES:${PN} += "${libdir}/xorg/protocol.txt"

RDEPENDS:${PN} += "xkbcomp"

CVE_STATUS_GROUPS = "CVE_STATUS_REDHAT"
CVE_STATUS_REDHAT = "CVE-2025-26594 CVE-2025-26595 CVE-2025-26596 CVE-2025-26597 CVE-2025-26598 CVE-2025-26599 CVE-2025-26600 CVE-2025-26601"
CVE_STATUS_REDHAT[status] = "fixed-version: these are tracked as versionless redhat CVEs in NVD DB, fixed in 24.1.6"
