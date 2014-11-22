DEPENDS_append_mx6q = " libfslvpuwrap"
DEPENDS_append_mx6dl = " libfslvpuwrap"

# Additional imx code and patches are included in the chromium-imx git repository.
# The code below fetches this repository, copies the extra source over to the main
# chromium source directory, and applies the patches.

CHROMIUM_IMX_BRANCH ?= "master"
CHROMIUM_IMX_SRCREV ?= "HEAD"
CHROMIUM_IMX_DESTSUFFIX ?= "chromium-imx-git"

PATCH_BASE_DIR = "${WORKDIR}/${CHROMIUM_IMX_DESTSUFFIX}/patches"

CHROMIUM_IMX_COMMON_PATCHES ?= " "
CHROMIUM_IMX_VPU_PATCHES ?= " "
CHROMIUM_IMX_WAYLAND_PATCHES ?= " "

SRC_URI += "git://github.com/Freescale/chromium-imx.git;destsuffix=${CHROMIUM_IMX_DESTSUFFIX};branch=${CHROMIUM_IMX_BRANCH};rev=${CHROMIUM_IMX_SRCREV}"

do_unpack[postfuncs] += "copy_chromium_imx_files"
# using =+ instead of += to make sure add_chromium_imx_patches is
# executed before add_ozone_wayland_patches in the main recipe;
# this is necessary because add_chromium_imx_patches appends
# patches to the OZONE_WAYLAND_EXTRA_PATCHES variable
do_patch[prefuncs] =+ "add_chromium_imx_patches"

# * Lost context problems are not known to happen with Vivante GPUs,
#   so it is safe to use ignore-lost-context
# * EGL is on by default due to the GPU on the i.MX6
PACKAGECONFIG_append = " use-egl ignore-lost-context"

copy_chromium_imx_files() {
	# sources in src/ are already organized in a manner
	# that matches the subdirectories in the chromium
	# source directory; just copy over the files in src/
	cp -r ${WORKDIR}/chromium-imx-git/src/* ${S}/
}

python add_chromium_imx_patches() {
    d.appendVar('SRC_URI', ' ' + d.getVar('CHROMIUM_IMX_COMMON_PATCHES', 1))
    d.appendVar('SRC_URI', ' ' + d.getVar('CHROMIUM_IMX_VPU_PATCHES', 1))
    d.appendVar('OZONE_WAYLAND_EXTRA_PATCHES', ' ' + d.getVar('CHROMIUM_IMX_WAYLAND_PATCHES', 1))
}

# Necessary flags to enable support for h.264 and MP4 in Chromium
# (Parsing is done by ffmpeg)
EXTRA_OEGYP += "-Dproprietary_codecs=1 -Dffmpeg_branding=Chrome"

COMPATIBLE_MACHINE = "(mx6)"
