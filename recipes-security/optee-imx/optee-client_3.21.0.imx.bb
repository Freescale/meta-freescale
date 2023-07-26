require optee-client-fslc-imx.inc

SRCBRANCH = "lf-6.1.22_2.0.0"
SRCREV = "8533e0e6329840ee96cf81b6453f257204227e6c"

DEPENDS += "util-linux"
EXTRA_OEMAKE += "PKG_CONFIG=pkg-config"
