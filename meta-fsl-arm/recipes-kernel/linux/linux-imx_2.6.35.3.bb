# Copyright (C) 2011-2012 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

PR = "${INC_PR}.14"

include linux-imx.inc

COMPATIBLE_MACHINE = "(mxs|mx5)"

# Revision of imx_2.6.35_10.12.01 branch
SRCREV_mxs = "0ea8cb9453379388f870f9b8d13269fb9dc0761c"
LOCALVERSION = "-10.12.01+yocto-${DATE}"

# Revision of imx_2.6.35_11.09.01 branch
SRCREV_mx5 = "b279c81dbdc42d22e7e77a59956e894f8f9f81aa"
LOCALVERSION = "-11.09.01+yocto-${DATE}"

SRC_URI += "file://NFS-Fix-nfsroot-support.patch \
            file://NFS-allow-nfs-root-mount-to-use-alternate-rpc-ports.patch \
"

SRC_URI_append_mxs = " \
           file://mx28-removecpufreq.patch \
           file://mxs-duart-use-ttyAMA-for-device-name.patch \
"
