# This recipe is modified for i.MX. For ease of maintenance the recipe itself
# is a verbatim copy of a OE-core recipe, so it can be diffed against
# upstream directly; the i.MX customization lives in the required
# mesa-tools-native_26.2.0.imx.inc.
########### OE-core copy ##################
# Upstream hash: 2614dd44ed724844df13d44b7cb6f8971925b0a3

require recipes-graphics/mesa/mesa.inc
inherit_defer native

# Kept as in the upstream recipe at the header's Upstream hash.
# UPSTREAM-PARITY.
# nooelint: oelint.vars.inconspaces
SUMMARY += " (tools only)"

PACKAGECONFIG = "tools asahi imagination panfrost"
# llvm required for libclc
PACKAGECONFIG += "gallium-llvm"
# Doesn't compile without wayland-scanner if PLATFORMS has wayland in, and,
# doesn't compile at all if PLATFORMS is empty so add x11 and wayland
# to PACKAGECONFIG like in mesa.inc
PACKAGECONFIG += "${@bb.utils.filter('DISTRO_FEATURES', 'x11 wayland', d)}"

# Ordering comes from the upstream recipe at the header's Upstream hash.
# nooelint: oelint.var.order.DEPENDS,oelint.vars.dependsordered
DEPENDS += "mesa-libclc-native spirv-tools-native spirv-llvm-translator-native"

# nooelint: oelint.vars.inconspaces
EXTRA_OEMESON += " \
    -Dmesa-clc=enabled -Dinstall-mesa-clc=true -Dmesa-clc-bundle-headers=enabled \
    -Dprecomp-compiler=enabled -Dinstall-precomp-compiler=true \
"
########### End of OE-core copy ###########

require mesa-tools-native_26.2.0.imx.inc
