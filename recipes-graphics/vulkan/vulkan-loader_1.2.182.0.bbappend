# The i.MX implementation is dynamically loaded, so it requires an
# explicit runtime dependency.
RRECOMMENDS:${PN}:append:imxvulkan = " libvulkan-imx"
