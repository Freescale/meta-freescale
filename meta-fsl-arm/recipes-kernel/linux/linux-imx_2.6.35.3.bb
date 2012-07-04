# Copyright (C) 2011-2012 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Linux kernel for imx platforms"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"
PR = "r24"

inherit kernel
COMPATIBLE_MACHINE = "(mxs|mx5)"

# Revision of 'rel_imx_2.6.35_11.09.01' tag
SRCREV = "691c08adeed64d5153937a0e31aaf4c251924471"

SRC_URI = "git://git.freescale.com/imx/linux-2.6-imx.git \
     	   file://0002-cgroupfs-create-sys-fs-cgroup-to-mount-cgroupfs-on.patch \
           file://egalax_ts-enable-single-event-support.patch \
           file://NFS-Fix-nfsroot-support.patch \
           file://NFS-allow-nfs-root-mount-to-use-alternate-rpc-ports.patch \
           file://1130-ENGR00157473-MX5X-UART-disable-UART2-DMA-to-make-G.patch \
           file://1132-ENGR00155891-mx53_loco-enable-mc34708-s-WDI-functio.patch \
           file://1134-ENGR00159738-v4l2-correct-wrong-parameter-when-V4l2.patch \
           file://1136-ENGR00161215-1-arch-arm-Add-two-new-IOCTLs-in-mxc_v.patch \
           file://1137-ENGR00161215-2-vpu-Add-ioctls-for-querying-and-sett.patch \
           file://1139-ENGR00162464-update-pm4-microcode-pm4_microcode_r18.patch \
           file://1140-ENGR00162711-DA9053-Add-dummy-write-for-DA9053-I2C.patch \
           file://1141-ENGR00162708-MX5-Add-I2C-dummy-write-and-mask-nONKE.patch \
           file://1142-ENGR00163698-MX53-ARD-fix-typo-error-for-pwm1-pad-d.patch \
           file://1143-ENGR00162578-DMA-mx5-increase-DMA-Zone-size-to-112.patch \
           file://1144-ENGR00169603-MX53-ARD-FlexCAN-Set-lp_apm-as-clock.patch \
           file://1145-ENGR00170342-PWM-fix-pwm-output-can-t-be-set-to-100.patch \
           file://1146-ENGR00170244-1-ARM-AHCI-Enable-PDDQ-mode-when-no-d.patch \
           file://1147-ENGR00170244-2-ARM-AHCI-Enable-PDDQ-mode-when-no-d.patch \
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
           file://defconfig \
          "

# TODO: Work in progress to follow Freescale. The following patches are
# causing black screens when using VPU video playout.
# -- Leon Woestenberg <leon@sidebranch.com>
#
#           file://1131-ENGR00158480-IPUv3-Set-IDMAC-LOCK-for-SDC-display-ch.patch \
#           file://1133-ENGR00159010-IPUv3-Restore-IDMAC_CH_LOCK_EN_1-for-re.patch \
#           file://1138-ENGR00162195-IPUv3M-Clear-IDMAC_LOCK_EN_1-for-tough.patch \
#           file://1135-ENGR00160566-IPUv3-Improve-IDMAC_LOCK_EN-setting.patch \ 
#           file://devtmpfs-init-options-alignment.patch \
#EXTRA_OEMAKE += "V=1"

S = "${WORKDIR}/git"

# install nedded headers for imx-test compilation
do_install_append() {
	# bounds.h may be used by a module and is currently missing
	if [ -d include/generated ]; then
		cp include/generated/* $kerneldir/include/generated/
	fi
	# scripts/basic/fixdep is used in imx-test
	mkdir -p $kerneldir/scripts/basic/
	cp scripts/basic/fixdep $kerneldir/scripts/basic/
	# scripts/mod/modpost is used in imx-test
	mkdir -p $kerneldir/scripts/mod
	cp scripts/mod/modpost $kerneldir/scripts/mod
}
