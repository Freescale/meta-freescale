# Copyright (C) 2011-2012 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

PR = "${INC_PR}.16"

include linux-imx.inc

COMPATIBLE_MACHINE = "(mxs|mx5)"

# Revision of imx_2.6.35_10.12.01 branch
SRCREV_mxs = "0ea8cb9453379388f870f9b8d13269fb9dc0761c"
LOCALVERSION_mxs = "-10.12.01+yocto"

# Revision of imx_2.6.35_11.09.01 branch
SRCREV_mx5 = "012a4b8a404f5c89c31e2d428d5e4c9eb3a70ec7"
LOCALVERSION_mx5 = "-11.09.01+yocto"

SRC_URI += "file://NFS-Fix-nfsroot-support.patch \
            file://NFS-allow-nfs-root-mount-to-use-alternate-rpc-ports.patch \
"

SRC_URI_append_mxs = " \
           file://mx28-removecpufreq.patch \
           file://mxs-duart-use-ttyAMA-for-device-name.patch \
"
