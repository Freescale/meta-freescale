# NXP mesa-imx source, shared by mesa_26.2.0.imx.bb and
# mesa-tools-native_26.2.0.imx.bb so both build the same tree.
# Not a standalone recipe: required by their .inc files.

# Also set by OE-core's mesa.inc, which oelint cannot read; restated here
# for the NXP fork.
DESCRIPTION = "Mesa with the NXP changes for the i.MX GPUs, mainly the Mali \
GPU of the i.MX 95 family, driven by Panfrost and PanVK."
HOMEPAGE = "https://github.com/nxp-imx/mesa-imx"
LICENSE = "MIT"

PV = "26.2.0.imx"

# The OE-core patches still apply to the mesa-imx tree, so only the release
# tarball is replaced, and the patches are taken from OE-core.
# The recipes are not bbappends, but they do extend an OE-core recipe.
# nooelint: oelint.vars.fileextrapaths,oelint.vars.outofcontext
FILESEXTRAPATHS:append := "${COREBASE}/meta/recipes-graphics/mesa/files:"
# Removes the OE-core tarball, which carries its checksum in mesa.inc.
# nooelint: oelint.vars.srcurichecksum
SRC_URI:remove = "https://archive.mesa3d.org/mesa-${PV}.tar.xz"
SRC_URI:prepend = "${MESA_IMX_SRC};branch=${SRCBRANCH} "
MESA_IMX_SRC ?= "git://github.com/nxp-imx/mesa-imx.git;protocol=https"
SRCBRANCH = "imx-26.2.0"
SRCREV = "d8cf103b34a45bb2a038e1d3a6f13c6b3212b297"

S = "${UNPACKDIR}/${BP}"
