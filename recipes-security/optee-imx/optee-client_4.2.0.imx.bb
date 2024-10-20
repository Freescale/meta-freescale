require optee-client-fslc-imx.inc

SRCBRANCH = "lf-6.6.36_2.1.0"
SRCREV = "3eac340a781c00ccd61b151b0e9c22a8c6e9f9f0" 

DEPENDS += "util-linux"
EXTRA_OEMAKE += "PKG_CONFIG=pkg-config"
