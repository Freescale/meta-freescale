# Copyright (C) 2011-2013 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

PR = "${INC_PR}.24"

include linux-imx.inc

COMPATIBLE_MACHINE = "(mx28|mx5)"

# Revision of imx_2.6.35_maintain branch
SRCREV = "2796ca60635ad1170e9a2d7ad0d0aa49107e7102"
LOCALVERSION = "-maintain+yocto"

SRC_URI += "file://NFS-Fix-nfsroot-support.patch \
            file://NFS-allow-nfs-root-mount-to-use-alternate-rpc-ports.patch \
            file://perf-avoid-use-sysroot-headers.patch \
            file://2.6.35-maintain-1-2-mx53-fix-ssi3-addresses.patch \
            file://2.6.35-maintain-2-2-SD2_DATA1__AUDMUX_AUD4_TXFS-fix-pin-defintion.patch \
"

SRC_URI_append_mx28 = " \
           file://mxs-duart-use-ttyAMA-for-device-name.patch \
"
