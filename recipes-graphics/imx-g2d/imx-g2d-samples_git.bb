SUMMARY = "i.MX G2D Samples"
DESCRIPTION = "Set of sample applications for i.MX G2D"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://COPYING;md5=0858ec9c7a80c4a2cf16e4f825a2cc91"

DEPENDS = "virtual/libg2d cairo"

PV = "2.1+git${SRCPV}"

SRC_URI = "${GPU_G2D_SAMPLES_SRC};branch=${SRCBRANCH}"
GPU_G2D_SAMPLES_SRC ?= "git://github.com/nxpmicro/g2d-samples.git;protocol=https"
SRCBRANCH ?= "imx_2.1"
SRCREV = "3268f291a15b2d8d5f650f627764a52eb0a8fc15"

S = "${WORKDIR}/git"

inherit pkgconfig

PACKAGECONFIG ??= "${@bb.utils.filter('DISTRO_FEATURES', 'wayland', d)}"

PACKAGECONFIG[wayland] = "USE_WAYLAND=true,USE_WAYLAND=false,wayland-native wayland-protocols"

EXTRA_OEMAKE += " \
    SDKTARGETSYSROOT=${STAGING_DIR_HOST} \
    ${PACKAGECONFIG_CONFARGS} \
"

do_install() {
    oe_runmake install DESTDIR=${D}
}

FILES:${PN} += "/opt"

COMPATIBLE_MACHINE = "(imxgpu2d)"
