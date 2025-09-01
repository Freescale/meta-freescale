# Copied from meta-imx/meta-imx-bsp/recipes-security/optee/optee-os-common-imx.inc.
# See: https://github.com/nxp-imx/imx-manifest/blob/imx-linux-walnascar/imx-6.12.20-2.0.0.xml#L37
require optee-os-fslc.inc

DEPENDS:append:arm = "u-boot-mkimage-native"

FILESEXTRAPATHS:prepend := "${THISDIR}/optee-os:"

SRC_URI:remove = "git://github.com/OP-TEE/optee_os.git;branch=master;protocol=https"
SRC_URI:prepend = "${OPTEE_OS_SRC};branch=${SRCBRANCH} "
SRC_URI:append = " file://0007-allow-setting-sysroot-for-clang.patch"
SRC_URI:remove = "file://0001-allow-setting-sysroot-for-libgcc-lookup.patch \
                  file://0002-optee-enable-clang-support.patch \
                  file://0003-core-link-add-no-warn-rwx-segments.patch"

OPTEE_OS_SRC ?= "git://github.com/nxp-imx/imx-optee-os.git;protocol=https"

inherit features_check

REQUIRED_MACHINE_FEATURES = "optee"

OPTEEMACHINE                   = "imx-${@d.getVar('MACHINE')[1:]}"
OPTEEMACHINE:imx6qpdlsolox     = "imx-mx6qsabresd"
OPTEEMACHINE:mx6ul-nxp-bsp     = "imx-mx6ulevk"
OPTEEMACHINE:mx6ull-nxp-bsp    = "imx-mx6ullevk"
OPTEEMACHINE:mx6ulz-nxp-bsp    = "imx-mx6ulzevk"
OPTEEMACHINE:mx8mq-nxp-bsp     = "imx-mx8mqevk"
OPTEEMACHINE:mx8mm-nxp-bsp     = "imx-mx8mmevk"
OPTEEMACHINE:mx8mn-nxp-bsp     = "imx-mx8mnevk"
OPTEEMACHINE:mx8mp-nxp-bsp     = "imx-mx8mpevk"
OPTEEMACHINE:mx8mpul-nxp-bsp   = "imx-mx8mpevk"
OPTEEMACHINE:mx8qm-nxp-bsp     = "imx-mx8qmmek"
OPTEEMACHINE:mx8qxp-nxp-bsp    = "imx-mx8qxpmek"
OPTEEMACHINE:mx8dx-nxp-bsp     = "imx-mx8dxmek"
OPTEEMACHINE:mx8dxl-nxp-bsp    = "imx-mx8dxlevk"
OPTEEMACHINE:mx8mnul-nxp-bsp   = "imx-mx8mnevk"
OPTEEMACHINE:mx8ulp-nxp-bsp    = "imx-mx8ulpevk"
OPTEEMACHINE:mx91-nxp-bsp      = "imx-mx91evk"
OPTEEMACHINE:mx93-nxp-bsp      = "imx-mx93evk"
OPTEEMACHINE:mx943-nxp-bsp     = "imx-mx943evk"
OPTEEMACHINE:mx95-nxp-bsp      = "imx-mx95evk"

# Strip the leading imx-
PLATFORM_FLAVOR = "${@d.getVar('OPTEEMACHINE')[4:]}"

EXTRA_OEMAKE:append = " \
    CFG_TEE_TA_LOG_LEVEL=0 \
    CFG_TEE_CORE_LOG_LEVEL=0 \
"

EXTRA_OEMAKE:append:imx8mq-lpddr4-wevk = " \
    CFG_CORE_LARGE_PHYS_ADDR=y \
    CFG_CORE_ARM64_PA_BITS=36 \
    CFG_DDR_SIZE=0x100000000 \
    CFG_TZDRAM_START=0xfe000000 \
"

EXTRA_OEMAKE:append:imx8dxlb0-fips-lpddr4-evk = " \
    CFG_NXP_CAAM=n \
"

COMPATIBLE_MACHINE = "(imx-nxp-bsp)"
