# Copyright (C) 2011-2012 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

PR = "${INC_PR}.12"

include linux-imx.inc

COMPATIBLE_MACHINE = "(mxs|mx5)"

# Revision of imx_2.6.35_10.12.01 branch
SRCREV_mxs = "5910faa40398a445e6b76c2edd1793aa852f738a"
LOCALVERSION = "-10.12.01+yocto-${DATE}"

# Revision of imx_2.6.35_11.09.01 branch
SRCREV_mx5 = "7bd2ccd2539dba25eee4107122cbe0df70544d7f"
LOCALVERSION = "-11.09.01+yocto-${DATE}"

SRC_URI += "file://NFS-Fix-nfsroot-support.patch \
            file://NFS-allow-nfs-root-mount-to-use-alternate-rpc-ports.patch \
            file://perf-tools-Allow-building-with-gcc-4.6.patch \
"

SRC_URI_append_mxs = " \
           file://mx28-removecpufreq.patch \
           file://mxs-duart-use-ttyAMA-for-device-name.patch \
"
