SUMMARY = "Userspace interface to kernel DRM services"
SECTION = "x11/base"
LICENSE = "GPLv2 & BSD"

LIC_FILES_CHKSUM = "file://libdrm_lists.h;beginline=1;endline=25;md5=2af1df1026ba1d24c8e7c370e68ed198"

DEPENDS = "libdrm"

SRCREV = "6b461c163b0bd02c76b65d94cc2fb3767167bda8"
PV = "0.1+git${SRCPV}"
SRC_URI = "git://git.armlinux.org.uk/~rmk/libdrm-armada.git"

inherit autotools pkgconfig

S = "${WORKDIR}/git"
