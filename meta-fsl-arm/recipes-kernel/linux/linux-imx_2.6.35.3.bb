# Copyright (C) 2011-2012 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

PR = "${INC_PR}.6"

include linux-imx.inc

COMPATIBLE_MACHINE = "(mxs|mx5)"

# Revision of imx_2.6.35_10.12.01 branch
SRCREV_mxs = "6a31a551bc302e74a17e9051bba356903e98273f"
LOCALVERSION = "-10.12.01+yocto-${DATE}"

# Revision of imx_2.6.35_11.09.01 branch
SRCREV_mx5 = "cb0c4573ddf2eb4ee6942929f265560740f5dad2"
LOCALVERSION = "-11.09.01+yocto-${DATE}"

SRC_URI += "file://NFS-Fix-nfsroot-support.patch \
            file://NFS-allow-nfs-root-mount-to-use-alternate-rpc-ports.patch \
            file://perf-tools-Fix-build-against-newer-glibc.patch \
            file://no-unaligned-access.patch \
            file://dm9601-support-for-usb-ethernet-0x0fe6-0x9700.patch \
"

SRC_URI_append_mxs = " \
           file://mx23-do-not-use-safe-write.patch \
           file://mx23-dont-mange-IRQ_VDDA_BRNOUT.patch \
           file://mx28-removecpufreq.patch \
           file://mxs-duart-use-ttyAMA-for-device-name.patch \
"

SRC_URI_append_mx5 = " \
           file://mx5-fix-hang-with-framebuffer.patch \
           file://egalax_ts-enable-single-event-support.patch \
           file://0001-mx53_loco-add-USR-led-support.patch \
           file://002_Return_ERESTARTSYS_from_IPU_GET_EVENT.patch \
           file://003_Sanitise_ipu_interrupt_return_value.patch \
"
