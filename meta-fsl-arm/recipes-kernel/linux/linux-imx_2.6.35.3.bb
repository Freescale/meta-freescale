# Copyright (C) 2011-2012 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

PR = "${INC_PR}.0"

include linux-imx.inc

COMPATIBLE_MACHINE = "(mxs|mx5)"

# Revision of imx_2.6.35_11.09.01 branch
SRCREV = "3e2396eddb362ff70ee6eac43fb1f27f217dc0d1"

SRC_URI += "file://0002-cgroupfs-create-sys-fs-cgroup-to-mount-cgroupfs-on.patch \
            file://egalax_ts-enable-single-event-support.patch \
            file://NFS-Fix-nfsroot-support.patch \
            file://NFS-allow-nfs-root-mount-to-use-alternate-rpc-ports.patch \
            file://no-unaligned-access.patch \
            file://mx28-removecpufreq.patch \
            file://mxs-duart-use-ttyAMA-for-device-name.patch \
            file://w1-fix-support-multiple-master.patch \
            file://smsc95xx-randomize-mac-once.patch \
            file://dm9601-support-for-usb-ethernet-0x0fe6-0x9700.patch \
            file://wire-up-sys_accept4-on-ARM.patch \
            file://0001-mx53_loco-add-USR-led-support.patch \
            file://002_Return_ERESTARTSYS_from_IPU_GET_EVENT.patch \
            file://003_Sanitise_ipu_interrupt_return_value.patch \
"
