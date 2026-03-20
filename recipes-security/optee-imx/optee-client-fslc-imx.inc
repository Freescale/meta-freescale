# Copied from meta-imx/meta-imx-bsp/recipes-security/optee/optee-client-imx.inc.
# See:https://github.com/nxp-imx/imx-manifest/blob/imx-linux-whinlatter/imx-6.18.2-1.0.0.xml#L39

require optee-client-fslc.inc

DEPENDS += "util-linux-libuuid"

FILESEXTRAPATHS:prepend := "${THISDIR}/optee-client:"
SRC_URI:remove = "git://github.com/OP-TEE/optee_client.git;branch=master;protocol=https"
SRC_URI:prepend = "${OPTEE_CLIENT_SRC};branch=${SRCBRANCH} "
OPTEE_CLIENT_SRC ?= "git://github.com/nxp-imx/imx-optee-client.git;protocol=https"

inherit pkgconfig

EXTRA_OECMAKE += "-DCFG_TEE_CLIENT_LOAD_PATH=${nonarch_base_libdir}"
