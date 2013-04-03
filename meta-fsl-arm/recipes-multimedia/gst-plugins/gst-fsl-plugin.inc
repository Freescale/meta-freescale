# Copyright (C) 2011, 2012 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Gstreamer freescale plugins"
LICENSE = "GPLv2 & LGPLv2 & LGPLv2.1"
SECTION = "multimedia"
DEPENDS = "gstreamer gst-plugins-base imx-lib"
INC_PR = "r9"

LIC_FILES_CHKSUM = "file://COPYING;md5=59530bdf33659b29e73d4adb9f9f6552 \
                    file://COPYING-LGPL-2;md5=5f30f0716dfdd0d91eb439ebec522ec2 \
                    file://COPYING-LGPL-2.1;md5=fbc093901857fcd118f065f900982c24"

PACKAGE_NAME ?= "${PN}"

S = "${WORKDIR}/${PACKAGE_NAME}-${PV}"

SRC_URI = "${FSL_MIRROR}/${PACKAGE_NAME}-${PV}.tar.gz"

inherit autotools pkgconfig

PLATFORM_mx5 = "MX51"
PLATFORM_mx6 = "MX6"
PLATFORM_mx28 = "MX28"

# Todo add a mechanism to map posible build targets
INCLUDE_DIR = "-I${STAGING_KERNEL_DIR}/include"
EXTRA_OEMAKE = "INCLUDES=${INCLUDE_DIR}"
EXTRA_OECONF = "PLATFORM=${PLATFORM} --disable-valgrind --disable-examples --disable-debug"

PACKAGES += "${PN}-libmfwsconf ${PN}-libgplaycore ${PN}-libgstfsl \
             ${PN}-libme ${PN}-libgstbufmeta ${PN}-libmfwba \
             ${PN}-libfwvss ${PN}-gplay"

FILES_${PN} = "${libdir}/gstreamer-0.10/*.so ${datadir}"
FILES_${PN}-dbg += "${libdir}/gstreamer-0.10/.debug"
FILES_${PN}-dev += "${libdir}/gstreamer-0.10/*.la ${libdir}/pkgconfig/*.pc"
FILES_${PN}-staticdev += "${libdir}/gstreamer-0.10/*.a"

FILES_${PN}-gplay = "${bindir}/gplay"
FILES_${PN}-libmfwsconf = "${libdir}/libmfwsconf${SOLIBS}"
FILES_${PN}-libgplaycore = "${libdir}/libgplaycore${SOLIBS}"
FILES_${PN}-libgstfsl = "${libdir}/libgstfsl-0.10${SOLIBS}"
FILES_${PN}-libme = "${libdir}/libme${SOLIBS}"
FILES_${PN}-libgstbufmeta = "${libdir}/libgstbufmeta${SOLIBS}"
FILES_${PN}-libmfwba =  "${libdir}/libmfwba${SOLIBS}"
FILES_${PN}-libfwvss = "${libdir}/libmfwvss${SOLIBS}"

PACKAGE_ARCH = "${MACHINE_ARCH}"
