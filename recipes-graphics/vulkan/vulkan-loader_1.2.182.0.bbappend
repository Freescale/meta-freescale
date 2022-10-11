# libvulkan.so is loaded dynamically, so put it in the main package
SOLIBS    = ".so*"
SOLIBSDEV = ""

# Override default mesa drivers with i.MX GPU drivers
RRECOMMENDS:${PN}:imxvulkan = "libvulkan-imx"
