FILESEXTRAPATHS_prepend := "${THISDIR}/chromium:"

include chromium-imx.inc

CHROMIUM_IMX_BRANCH = "master"
CHROMIUM_IMX_SRCREV = "4a2d15ab899b9944bb3adb2ddd250530da5b2e1a"
CHROMIUM_IMX_COMMON_PATCHES += "file://${PATCH_BASE_DIR}/common/0001-Enable-share-group-workaround-for-Vivante-GPUs.patch"
CHROMIUM_ENABLE_WAYLAND = "0"

DEPENDS_append_imxvpu = " imx-vpuwrap"

VPU_PATCHES = "file://${PATCH_BASE_DIR}/common/0002-Add-VPU-video-decode-accelerator-to-Chromium-GPU-.patch"

CHROMIUM_IMX_VPU_PATCHES_imxvpu += "${VPU_PATCHES}"

CHROMIUM_IMX_WAYLAND_PATCHES += "file://${PATCH_BASE_DIR}/wayland/0001-Modify-eglwayland-versions-for-Vivante-GPUs.patch"

# Don't use X if it's running through Wayland
CHROMIUM_ENABLE_WAYLAND = "${@bb.utils.contains('DISTRO_FEATURES', 'wayland', '1', \
                      bb.utils.contains('DISTRO_FEATURES', 'x11', '0', \
                      '0', d),d)}"

SRC_URI += "\
        ${@oe.utils.conditional('CHROMIUM_ENABLE_WAYLAND', '1', 'git://github.com/01org/ozone-wayland.git;destsuffix=${OZONE_WAYLAND_GIT_DESTSUFFIX};branch=${OZONE_WAYLAND_GIT_BRANCH};rev=${OZONE_WAYLAND_GIT_SRCREV}', '', d)} \
        ${@oe.utils.conditional('CHROMIUM_ENABLE_WAYLAND', '1', ' file://chromium.patch', '', d)} \
"

# Remove packages as Chromium is changed to statically link against ffmpeg.
PACKAGES_remove = "${PN}-codecs-ffmpeg ${PN}-plugin-pdf"
