# Copyright (C) 2011-2012 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

PR = "${INC_PR}.9"

include linux-imx.inc

COMPATIBLE_MACHINE = "(mxs|mx5)"

# Revision of imx_2.6.35_10.12.01 branch
SRCREV_mxs = "38bf41cc376e24c384934fbeb10770d6712e9143"
LOCALVERSION = "-10.12.01+yocto-${DATE}"

# Revision of imx_2.6.35_11.09.01 branch
SRCREV_mx5 = "406cab2e8b3c030c0f86263282f846bb55a2faf6"
LOCALVERSION = "-11.09.01+yocto-${DATE}"

SRC_URI += "file://NFS-Fix-nfsroot-support.patch \
            file://NFS-allow-nfs-root-mount-to-use-alternate-rpc-ports.patch \
"

SRC_URI_append_mxs = " \
           file://mx28-removecpufreq.patch \
           file://mxs-duart-use-ttyAMA-for-device-name.patch \
"

SRC_URI_append_mx5 = " \
           file://egalax_ts-enable-single-event-support.patch \
           file://0001-mx53_loco-add-USR-led-support.patch \
"
