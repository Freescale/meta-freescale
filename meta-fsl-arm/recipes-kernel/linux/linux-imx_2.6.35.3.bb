# Copyright (C) 2011-2012 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

PR = "${INC_PR}.23"

include linux-imx.inc

COMPATIBLE_MACHINE = "(mx28|mx5)"

# Revision of imx_2.6.35_1.1.0 branch
SRCREV_mx28 = "914558e17e95a55ac1eac9a6c219d17d7825f715"
LOCALVERSION_mx28 = "-1.1.0+yocto"

# Revision of imx_2.6.35_11.09.01 branch
SRCREV_mx5 = "ed51f22bea310805bfdcc1be236cc56ff0832b3a"
LOCALVERSION_mx5 = "-11.09.01+yocto"

SRC_URI += "file://NFS-Fix-nfsroot-support.patch \
            file://NFS-allow-nfs-root-mount-to-use-alternate-rpc-ports.patch \
            file://perf-avoid-use-sysroot-headers.patch \
"

SRC_URI_append_mx28 = " \
           file://mxs-duart-use-ttyAMA-for-device-name.patch \
"
