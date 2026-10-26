# i.MX customization for libcamera_0.7.2.imx.bb.
# Not a standalone recipe: required by that .bb, and must stay beside
# it. Keeping it separate lets the .bb remain a verbatim copy of the
# meta-openembedded original.

# libyuv is only used by the virtual pipeline handler and the Android
# adaptation layer, neither of which is built, and it comes from
# meta-multimedia, which this layer does not depend on.
DEPENDS:remove = "libyuv"

SRC_URI:remove = "git://git.libcamera.org/libcamera/libcamera.git;protocol=https;branch=master;tag=v${PV}"
SRC_URI:prepend = "${LIBCAMERA_SRC};branch=${SRCBRANCH} "
LIBCAMERA_SRC ?= "git://github.com/nxp-imx/libcamera.git;protocol=https"
SRCBRANCH = "lf-6.18.37_2.1.0"
SRCREV = "84d2508ac0ef25c2d9554d13ad2503e6cc480ec4"

# The python (pycamera) bindings need python3-pybind11, from meta-python.
PACKAGECONFIG = "dng gst kms ${@bb.utils.contains('BBFILE_COLLECTIONS', 'meta-python', 'python', '', d)}"

ARM_PIPELINES .= ",nxp/neo"

EXTRA_OEMESON += "\
    --python.platlibdir=${PYTHON_SITEPACKAGES_DIR} \
"

# Qt installs native tools to /usr/libexec, but this is not in PATH
PATH:prepend = "${@bb.utils.contains('DISTRO_FEATURES', 'qt', '${STAGING_LIBEXECDIR_NATIVE}:', '', d)}"

# Documented here rather than beside the function, which sits in the verbatim
# .bb copy. Not an addtask'd task: do_package:append runs it via exec_func.
do_package_recalculate_ipa_signatures[doc] = "Re-sign the IPA modules after packaging"

COMPATIBLE_MACHINE = "(mx95-nxp-bsp|mx8mm-nxp-bsp|mx8ulp-nxp-bsp|mx8mq-nxp-bsp)"
