# Copyright (C) 2011, 2012 Freescale
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "X.Org X server -- Freescale iMx framebuffer driver"
LICENSE = "MIT-X"
DEPENDS = "virtual/kernel virtual/xserver virtual/libx11 xproto randrproto util-macros amd-gpu-x11-bin-mx51 libz160"
LIC_FILES_CHKSUM = "file://COPYING;md5=f7bdc0c63080175d1667091b864cb12c"

PR = "r12"

SRC_URI = "${FSL_MIRROR}/xserver-xorg-video-imx-${PV}.tar.gz \
           file://xf86-video-imxfb-fix-m4-hardcodded-paths.patch \
           file://Make-video-API-forward-and-backward-compatible.patch \
           file://ext-Update-to-newer-swap-macros.patch \
           file://Fix-error-unknown-type-name-uint.patch \
           file://xserver-1.14-compat.patch \
"
SRC_URI[md5sum] = "d19148399b5d1c4dab90d0cc6f2c4789"
SRC_URI[sha256sum] = "d7d85e9f13c6dd58addab89847f3a8a67f6382a54135c7978c9a95368af024d4"

inherit autotools pkgconfig

FILES_${PN} += " ${libdir}/xorg/modules/drivers/*.so"
FILES_${PN}-dbg += " ${libdir}/xorg/modules/drivers/.debug"
EXTRA_OECONF_armv7a = " --enable-neon "
CFLAGS += " -I${STAGING_INCDIR}/xorg -I${STAGING_KERNEL_DIR}/include"

S = "${WORKDIR}/xserver-xorg-video-imx-${PV}"

do_install_append () {
    # driver's la files are not packaged
    rm -f ${D}${libdir}/xorg/modules/drivers/*.la
}

RDEPENDS_${PN} += "xserver-xorg-module-exa"

INSANE_SKIP_${PN} = "ldflags"
INSANE_SKIP_${PN}-dbg = "ldflags"
