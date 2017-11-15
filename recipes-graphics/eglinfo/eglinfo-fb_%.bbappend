FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append_imxgpu3d = " \
    file://0001-Replace-vendor-specific-header-with-generic.patch \
"

# Set i.MX specific dependencies and device type
EGLINFO_DEVICE_imxgpu3d = "imx6"
