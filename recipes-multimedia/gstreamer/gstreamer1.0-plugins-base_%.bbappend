FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

GST_IMX_PATCHES = " file://gstplaybin-remove-flag-deinterlace.patch \
                    file://0001-MMFMWK-7030-Linux_MX6QP_ARD-IMXCameraApp-When-Enable.patch \
                    file://0002-Remove-dependence-on-imx-plugin-git.patch \
"

GST_IMX_PATCHES_TO_APPEND = ""
GST_IMX_PATCHES_TO_APPEND_imxpxp = "${GST_IMX_PATCHES}"
GST_IMX_PATCHES_TO_APPEND_imxvpu = "${GST_IMX_PATCHES}"

SRC_URI_append = "${GST_IMX_PATCHES_TO_APPEND}"

PACKAGE_ARCH_imxpxp = "${MACHINE_SOCARCH}"
PACKAGE_ARCH_imxvpu = "${MACHINE_SOCARCH}"
