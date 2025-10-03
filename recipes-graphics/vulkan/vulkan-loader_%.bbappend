SRC_URI:append:imx-nxp-bsp = " \
    file://0001-LF-11869-change-mali-wsi-layer-activating-order.patch \
"

PACKAGE_ARCH:imx-nxp-bsp = "${MACHINE_SOCARCH}"

# libvulkan.so is loaded dynamically, so put it in the main package
SOLIBS:imx-nxp-bsp          = ".so*"
FILES_SOLIBSDEV:imx-nxp-bsp = ""
INSANE_SKIP:${PN}:imx-nxp-bsp += "dev-so"
