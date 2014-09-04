include u-boot-fslc.inc

PV = "v2014.10+git${SRCPV}"

SRCREV = "5fd0b607d0ef44b8b93d64faace674955791dd7d"
SRCBRANCH = "patches-2014.10"

# Fix to allow booting with 3.10.31-1.1.0 Beta kernel
SRC_URI += "file://iMX6-Change-mmcroot-to-use-fixed-mmc-block-index-for.patch"
