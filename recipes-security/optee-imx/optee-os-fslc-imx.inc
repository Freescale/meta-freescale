require optee-os-fslc.inc

SRC_URI = "git://github.com/nxp-imx/imx-optee-os.git;protocol=https;branch=${SRCBRANCH}"

# The platform flavor corresponds to the Yocto machine without the leading 'i'.
PLATFORM_FLAVOR                   = "${@d.getVar('MACHINE')[1:]}"
PLATFORM_FLAVOR:imx6qdlsabresd    = "mx6qsabresd"
PLATFORM_FLAVOR:imx6qdlsabreauto  = "mx6qsabreauto"
PLATFORM_FLAVOR:imx6qpdlsolox     = "mx6qsabresd"
PLATFORM_FLAVOR:mx6ul-nxp-bsp     = "mx6ulevk"
PLATFORM_FLAVOR:mx6ull-nxp-bsp    = "mx6ullevk"
PLATFORM_FLAVOR:mx6ulz-nxp-bsp    = "mx6ulzevk"
PLATFORM_FLAVOR:mx8mq-nxp-bsp     = "mx8mqevk"
PLATFORM_FLAVOR:mx8mm-nxp-bsp     = "mx8mmevk"
PLATFORM_FLAVOR:mx8mn-nxp-bsp     = "mx8mnevk"
PLATFORM_FLAVOR:mx8mnul-nxp-bsp   = "mx8mnevk"
PLATFORM_FLAVOR:mx8mp-nxp-bsp     = "mx8mpevk"
PLATFORM_FLAVOR:mx8mpul-nxp-bsp   = "mx8mpevk"
PLATFORM_FLAVOR:mx8qm-nxp-bsp     = "mx8qmmek"
PLATFORM_FLAVOR:mx8qxp-nxp-bsp    = "mx8qxpmek"
PLATFORM_FLAVOR:mx8dx-nxp-bsp     = "mx8dxmek"
PLATFORM_FLAVOR:mx8dxl-nxp-bsp    = "mx8dxlevk"
PLATFORM_FLAVOR:mx8ulp-nxp-bsp    = "mx8ulpevk"
PLATFORM_FLAVOR:mx93-nxp-bsp      = "mx93evk"

COMPATIBLE_MACHINE = "(imx-nxp-bsp)"
