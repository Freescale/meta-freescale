# libvulkan.so is loaded dynamically, so put it in the main package
SOLIBS          = ".so*"
FILES_SOLIBSDEV = ""
INSANE_SKIP:${PN} += "dev-so"

# Override default mesa drivers with i.MX GPU drivers
RRECOMMENDS:${PN}:imxvulkan = "libvulkan-imx"
# Override default mesa drivers with i.MX GPU drivers
RRECOMMENDS:${PN}:mx95-nxp-bsp = "mali-imx-libvulkan"
