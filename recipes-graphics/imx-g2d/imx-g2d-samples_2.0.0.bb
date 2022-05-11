SUMMARY = "i.MX G2D Samples"
DESCRIPTION = "Set of sample applications for i.MX G2D"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://COPYING;md5=0858ec9c7a80c4a2cf16e4f825a2cc91"

DEPENDS = "virtual/libg2d"

GPU_G2D_SAMPLES_SRC ?= "git://github.com/nxpmicro/g2d-samples.git;protocol=https"
SRCBRANCH ?= "imx_2.0"
SRC_URI = "${GPU_G2D_SAMPLES_SRC};branch=${SRCBRANCH}"
SRCREV = "267af142881d350218c2c2cead8f304a28bbee07"

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
