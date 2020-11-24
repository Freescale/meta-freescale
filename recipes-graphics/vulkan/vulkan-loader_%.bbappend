# The i.MX implementation is dynamically loaded, so it requires an
# explicit runtime dependency.
RRECOMMENDS_${PN}_append_imxgpu = " libvulkan-imx"
