require optee-client-fslc-imx.inc

SRCBRANCH = "lf-6.6.3_1.0.0"
SRCREV = "acb0885c117e73cb6c5c9b1dd9054cb3f93507ee"

DEPENDS += "util-linux"
EXTRA_OEMAKE += "PKG_CONFIG=pkg-config"
