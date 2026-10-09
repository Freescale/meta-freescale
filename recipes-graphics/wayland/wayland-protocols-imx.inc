# i.MX wayland protocols for the recipes that use them.
# Not a standalone recipe: required by the i.MX weston and
# gstreamer1.0-plugins-bad recipes. Both call the
# zwp_linux_buffer_params_v1.add_dtrc_meta request that upstream
# linux-dmabuf does not define, and weston 10.0.5.imx also implements
# the alpha-compositing and hdr10-metadata protocols.
#
# Patching wayland-protocols itself would make it, and through it every
# recipe that depends on it, differ per SoC. Instead each of these recipes
# builds against a private copy of the staged protocols carrying the
# request, and wayland-protocols stays stock (and allarch) for the rest.

# Lets every recipe requiring this file, wherever it lives, find the patch.
# nooelint: oelint.vars.fileextrapaths, oelint.vars.outofcontext
FILESEXTRAPATHS:prepend := "${THISDIR}/wayland-protocols-imx:"

IMX_WAYLAND_PROTOCOLS_PATCHES = "\
    0001-unstable-Add-alpha-compositing-protocol.patch \
    0002-unstable-Add-hdr10-metadata-protocol.patch \
    0003-linux-dmabuf-support-passing-buffer-DTRC-meta-to-com.patch \
"
SRC_URI += "${@' '.join('file://%s;apply=no' % p for p in d.getVar('IMX_WAYLAND_PROTOCOLS_PATCHES').split())}"

IMX_WAYLAND_PROTOCOLS_DIR = "${WORKDIR}/wayland-protocols-imx"

EXTRA_OEMESON += "-Dpkg_config_path=${IMX_WAYLAND_PROTOCOLS_DIR}/pkgconfig"

do_imx_wayland_protocols() {
    rm -rf ${IMX_WAYLAND_PROTOCOLS_DIR}
    # Not staged when the recipe builds without Wayland support
    if [ ! -d ${STAGING_DATADIR}/wayland-protocols ]; then
        return
    fi
    mkdir -p ${IMX_WAYLAND_PROTOCOLS_DIR}/pkgconfig
    cp -R --no-preserve=links ${STAGING_DATADIR}/wayland-protocols ${IMX_WAYLAND_PROTOCOLS_DIR}/share
    # A build directory that predates this file can still stage the old
    # patched wayland-protocols here, so apply each patch only when missing
    for p in ${IMX_WAYLAND_PROTOCOLS_PATCHES}; do
        patch=${UNPACKDIR}/$p
        if ! patch -d ${IMX_WAYLAND_PROTOCOLS_DIR}/share -p1 -R -s -f --dry-run < $patch >/dev/null; then
            patch -d ${IMX_WAYLAND_PROTOCOLS_DIR}/share -p1 --no-backup-if-mismatch < $patch
        fi
    done
    sed -e 's|^pkgdatadir=.*|pkgdatadir=${IMX_WAYLAND_PROTOCOLS_DIR}/share|' \
        ${STAGING_DATADIR}/pkgconfig/wayland-protocols.pc \
        > ${IMX_WAYLAND_PROTOCOLS_DIR}/pkgconfig/wayland-protocols.pc
}
do_imx_wayland_protocols[doc] = "Stage a private copy of wayland-protocols carrying the i.MX protocols"
do_imx_wayland_protocols[dirs] = "${WORKDIR}"
addtask imx_wayland_protocols after do_prepare_recipe_sysroot do_unpack before do_configure
