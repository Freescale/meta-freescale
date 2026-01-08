FILESEXTRAPATHS:prepend:imx-nxp-bsp := "${THISDIR}/${DIRPATCHVULKAN}:"

DIRPATCHVULKAN = "vulkan-loader${@oe.utils.ifelse(bb.utils.vercmp_string_op('${PV}', '1.3.296', "<"), '-older-1-3-296', '')}"

SRC_URI:append:imx-nxp-bsp = " \
    file://0001-LF-11869-change-mali-wsi-layer-activating-order.patch \
"

PACKAGE_ARCH:imx-nxp-bsp = "${MACHINE_SOCARCH}"

# libvulkan.so is loaded dynamically, so put it in the main package
SOLIBS:imx-nxp-bsp          = ".so*"
FILES_SOLIBSDEV:imx-nxp-bsp = ""
INSANE_SKIP:${PN}:imx-nxp-bsp += "dev-so"
