# The i.MX BSP uses pipewire for Bluetooth audio, so bluealsa's own service is
# redundant here.
SYSTEMD_AUTO_ENABLE:${PN}:imx-generic-bsp = "disable"
