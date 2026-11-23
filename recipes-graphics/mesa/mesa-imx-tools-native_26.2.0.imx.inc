# i.MX customization for mesa-imx-tools-native_26.2.0.imx.bb.
# Not a standalone recipe: required by that .bb, and must stay beside
# it. Keeping it separate lets the .bb remain a verbatim copy of the
# OE-core original, mesa-tools-native.bb.
#
# A separate PN rather than a version of mesa-tools-native: native output
# is shared by every machine, so it cannot switch to the fork on the Mali
# machines only. mesa_26.2.0.imx depends on this recipe instead, and
# mesa-tools-native stays the OE-core one everywhere.

require mesa-26.2.0.imx-source.inc
