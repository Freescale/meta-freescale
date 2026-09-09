# i.MX customization for libcamera_0.7.1.imx.bb.
# Not a standalone recipe: required by that .bb, and must stay beside
# it. Keeping it separate lets the .bb remain a verbatim copy of the
# meta-openembedded original.

SRC_URI:remove = "git://git.libcamera.org/libcamera/libcamera.git;protocol=https;branch=master;tag=v${PV}"
SRC_URI:prepend = "${LIBCAMERA_SRC};branch=${SRCBRANCH} "
LIBCAMERA_SRC ?= "git://github.com/nxp-imx/libcamera.git;protocol=https"
SRCBRANCH = "lf-6.18.20_2.0.0"
SRCREV = "e2e7c015cee997b9f992376fd2c29fa2d8813e1b"

PACKAGECONFIG = "gst pycamera dng"

ARM_PIPELINES .= ",nxp/neo"

EXTRA_OEMESON += "\
    --python.platlibdir=${PYTHON_SITEPACKAGES_DIR} \
"

# Qt installs native tools to /usr/libexec, but this is not in PATH
PATH:prepend = "${@bb.utils.contains('DISTRO_FEATURES', 'qt', '${STAGING_LIBEXECDIR_NATIVE}:', '', d)}"

COMPATIBLE_MACHINE = "(mx95-nxp-bsp|mx8mm-nxp-bsp|mx8ulp-nxp-bsp|mx8mq-nxp-bsp)"
