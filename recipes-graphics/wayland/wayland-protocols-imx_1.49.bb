SUMMARY = "Installs the i.MX Wayland protocols"
DESCRIPTION = "Installs a copy of wayland-protocols carrying the i.MX \
               protocols in ${datadir}/imx, so the recipes using them \
               build against it and wayland-protocols itself stays stock."
HOMEPAGE = "https://gitlab.freedesktop.org/wayland/wayland-protocols"
SECTION = "devel"
LICENSE = "MIT"
# The installed tree has no COPYING
LIC_FILES_CHKSUM = "file://stable/presentation-time/presentation-time.xml;endline=26;md5=4646cd7d9edc9fa55db941f2d3a7dc53"

INHIBIT_DEFAULT_DEPS = "1"
DEPENDS = "wayland-protocols"

# Patches the staged wayland-protocols tree: keep the version in step with it.
# From https://github.com/nxp-imx/wayland-protocols-imx, which is too old to
# build against directly.
SRC_URI = "\
    file://0001-unstable-Add-alpha-compositing-protocol.patch \
    file://0002-unstable-Add-hdr10-metadata-protocol.patch \
    file://0003-linux-dmabuf-support-passing-buffer-DTRC-meta-to-com.patch \
"

S = "${UNPACKDIR}/wayland-protocols"

do_stage_wayland_protocols() {
    rm -rf ${S}
    cp -R ${STAGING_DATADIR}/wayland-protocols ${S}
}
do_stage_wayland_protocols[doc] = "Copy the staged wayland-protocols tree into S for patching"
addtask stage_wayland_protocols after do_unpack do_prepare_recipe_sysroot before do_patch

do_configure[noexec] = "1"

do_compile[noexec] = "1"

do_install() {
    install -d ${D}${datadir}/imx/wayland-protocols
    cp -R --no-preserve=ownership ${S}/. ${D}${datadir}/imx/wayland-protocols
    # quilt state from do_patch
    rm -rf ${D}${datadir}/imx/wayland-protocols/.pc ${D}${datadir}/imx/wayland-protocols/patches

    install -d ${D}${datadir}/imx/pkgconfig
    sed -e '/^pkgdatadir=/s|/wayland-protocols$|/imx/wayland-protocols|' \
        ${STAGING_DATADIR}/pkgconfig/wayland-protocols.pc \
        > ${D}${datadir}/imx/pkgconfig/wayland-protocols.pc
    grep -q '^pkgdatadir=.*/imx/wayland-protocols$' ${D}${datadir}/imx/pkgconfig/wayland-protocols.pc || \
        bbfatal "pkgdatadir was not rewritten in wayland-protocols.pc"
}

PACKAGE_ARCH = "${MACHINE_SOCARCH}"

FILES:${PN} += "${datadir}/imx/wayland-protocols"
FILES:${PN}-dev += "${datadir}/imx/pkgconfig"

# Restrict this recipe to NXP BSP only, this recipe is not compatible
# with mainline BSP
COMPATIBLE_HOST = '(null)'
COMPATIBLE_HOST:use-nxp-bsp = '.*'
