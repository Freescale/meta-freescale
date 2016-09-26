FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

include chromium-imx.inc

CHROMIUM_IMX_BRANCH = "master"
CHROMIUM_IMX_SRCREV = "4a2d15ab899b9944bb3adb2ddd250530da5b2e1a"
CHROMIUM_IMX_COMMON_PATCHES += "file://${PATCH_BASE_DIR}/common/0001-Enable-share-group-workaround-for-Vivante-GPUs.patch"

# Remove packages as Chromium is changed to statically link against ffmpeg.
PACKAGES_remove = "${PN}-codecs-ffmpeg ${PN}-plugin-pdf"
