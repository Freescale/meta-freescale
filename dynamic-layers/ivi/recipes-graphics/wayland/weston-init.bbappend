# Scoped, unlike the layer's other FILESEXTRAPATHS lines, because this
# directory holds a top-level weston.ini and oe-core's weston-init.bb fetches
# "file://weston.ini" unconditionally -- unscoped, the NXP IVI config replaced
# oe-core's on every machine in any build that also has meta-ivi.
FILESEXTRAPATHS:prepend:imx-generic-bsp := "${THISDIR}/${PN}:"
