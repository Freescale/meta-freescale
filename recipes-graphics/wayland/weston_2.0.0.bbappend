SUMMARY_append = " (with i.MX support)"

DEPENDS_append_imxgpu2d = " virtual/libg2d"

# Use i.MX fork of weston for customizations.
SRC_URI_remove_imxgpu2d = "https://wayland.freedesktop.org/releases/${BPN}-${PV}.tar.xz"
SRC_URI_prepend_imxgpu2d = "git://source.codeaurora.org/external/imx/weston-imx.git;protocol=https;branch=weston-imx-2.0 "
SRCREV_imxgpu2d = "506dc2d69b7cf7b7e7d1ea94ce29c8203215a67e"
S_imxgpu2d = "${WORKDIR}/git"

# Define RECIPE_SYSROOT since it doesn't exist in morty
# for this backported recipe
RECIPE_SYSROOT = "${STAGING_DIR}/${MACHINE}"

EXTRA_OECONF_IMX_COMMON = "WESTON_NATIVE_BACKEND=fbdev-backend.so"
EXTRA_OECONF_IMX          = ""
EXTRA_OECONF_IMX_imxpxp   = "${EXTRA_OECONF_IMX_COMMON}"
EXTRA_OECONF_IMX_imxgpu2d = "${EXTRA_OECONF_IMX_COMMON}"
EXTRA_OECONF_append = " ${EXTRA_OECONF_IMX}"

# Disable OpenGL for parts with GPU support for 2D but not 3D
IMX_REQUIRED_DISTRO_FEATURES_REMOVE          = ""
IMX_REQUIRED_DISTRO_FEATURES_REMOVE_imxgpu2d = "opengl"
IMX_REQUIRED_DISTRO_FEATURES_REMOVE_imxgpu3d = ""
REQUIRED_DISTRO_FEATURES_remove = "${IMX_REQUIRED_DISTRO_FEATURES_REMOVE}"
IMX_EXTRA_OECONF_OPENGL          = ""
IMX_EXTRA_OECONF_OPENGL_imxgpu2d = " --disable-opengl"
IMX_EXTRA_OECONF_OPENGL_imxgpu3d = ""
EXTRA_OECONF_append = "${IMX_EXTRA_OECONF_OPENGL}"

PACKAGECONFIG_append_imxgpu3d = " cairo-glesv2"

# Append EGL_CFLAGS to CFLAGS for imxgpu2d
CFLAGS_append_imxgpu2d = "-DLINUX -DEGL_API_FB -DWL_EGL_PLATFORM"

RDEPENDS_${PN} += "${@bb.utils.contains('DISTRO_FEATURES', 'pam', 'pam-plugin-loginuid', '', d)}"
