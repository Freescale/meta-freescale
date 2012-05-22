require recipes-bsp/u-boot/u-boot.inc

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=1707d6db1d42237583f50183a5651ecb"
COMPATIBLE_MACHINE = "(imx28evk|mx5|mx6)"

DEPENDS_mxs += "elftosb-native"

SRCREV = "415d386877df49eb051b85ef74fa59a16dc17c7d"

PV = "v2012.04.01"

SRC_URI = "git://git.denx.de/u-boot.git;branch=master;protocol=git \
           file://0001-MX5-Add-definitions-for-SATA-controller.patch \
           file://0002-SATA-check-for-return-value-from-sata-functions.patch \
           file://0003-MX53-add-function-to-set-SATA-clock-to-internal.patch \
           file://0004-SATA-add-driver-for-MX5-MX6-SOCs.patch \
           file://0005-MX53-Add-support-to-ESG-ima3-board.patch \
           file://0006-MX53-mx53loco-Add-SATA-support.patch \
           file://0007-pmic-Add-support-for-the-Dialog-DA9053-PMIC.patch \
           file://0008-mx6qsabrelite-No-need-to-set-the-direction-for-GPIO3.patch \
           file://0009-mx28evk-Allow-to-booting-a-dt-kernel.patch \
           file://0010-m28evk-Allow-to-booting-a-dt-kernel.patch \
           file://0011-mx28evk-Allow-booting-a-zImage-kernel.patch \
           file://0012-mx6qsabrelite-Allow-booting-a-zImage-kernel.patch \
           file://0013-mx6qarm2-Allow-booting-a-zImage-kernel.patch \
           file://0014-mx31pdk-Allow-booting-a-zImage-kernel.patch \
           file://0015-i.MX6Q-mx6qsabrelite-Add-keypress-support-to-alter-b.patch \
           file://0016-imx-common-Factor-out-get_ahb_clk.patch \
           file://0017-mx5-Add-clock-config-interface.patch \
           file://0018-mx53loco-Allow-to-print-CPU-information-at-a-later-s.patch \
           file://0019-mx53loco-Add-support-for-1GHz-operation-for-DA9053-b.patch \
           file://0020-M28-Enable-FDT-support.patch \
           file://0021-Revert-i.MX28-Enable-additional-DRAM-address-bits.patch \
           file://0022-M28-Scan-only-first-512-MB-of-DRAM-to-avoid-memory-w.patch \
           file://0023-USB-ehci-mx6-Fix-broken-IO-access.patch \
           file://0024-mx28evk-add-NAND-support.patch \
           file://0025-i.MX6-Add-ANATOP-regulator-init.patch \
           file://0026-i.MX6-add-enable_sata_clock.patch \
           file://0027-i.MX6-mx6q_sabrelite-add-SATA-bindings.patch \
           file://0028-i.MX25-esdhc-Add-mxc_get_clock-infrastructure.patch \
           file://0029-i.MX25-This-architecture-has-a-GPIO4-too.patch \
           file://0030-imx-nand-Support-flash-based-BBT.patch \
           file://0031-i.MX25-usb-Set-PORTSCx-register.patch \
           file://0032-imx-usb-There-is-no-such-register.patch \
           file://0033-i.MX2-Include-asm-types.h-in-arch-mx25-imx-regs.h.patch \
           file://0034-imx-Add-u-boot.imx-as-target-for-ARM9-i.MX-SOCs.patch \
           file://0035-pmic-dialog-Avoid-name-conflicts.patch \
           file://0036-mx53loco-Add-mc34708-support-and-set-mx53-frequency-.patch \
           file://0037-mx53loco-Turn-on-VUSB-regulator.patch \
           file://0038-mx53loco-Add-CONFIG_REVISION_TAG.patch \
           file://0039-mx53loco-Remove-unneeded-gpio_set_value.patch \
           file://0040-spi-mxs-Introduce-spi_cs_is_valid.patch \
           file://0041-spi-mxs-Allow-other-chip-selects-to-work.patch \
           file://0042-i.MX28-Add-delay-after-CPU-bypass-is-cleared.patch \
           file://0043-MX5-PAD_CTL_DRV_VOT_LOW-and-PAD_CTL_DRV_VOT_HIGH-exc.patch \
           file://0044-M28EVK-Implement-support-for-new-board-V2.0.patch \
           file://0045-M28EVK-Add-SD-update-command.patch \
           file://0046-i.MX28-Improve-passing-of-data-from-SPL-to-U-Boot.patch \
           file://0047-i.MX28-Implement-boot-pads-sampling-and-reporting.patch \
           file://0048-i.MX28-Add-LCDIF-register-definitions.patch \
           file://0049-i.MX28-Shut-down-the-LCD-controller-before-reset.patch \
           file://0050-i.MX28-Add-LRADC-register-definitions.patch \
           file://0051-i.MX28-Add-LRADC-init-to-i.MX28-SPL.patch \
           file://0052-i.MX28-Reorder-battery-status-functions-in-SPL.patch \
           file://0053-i.MX28-Add-battery-boot-components-to-SPL.patch \
           file://0054-i.MX28-Check-if-WP-detection-is-implemented-at-all.patch \
           file://0055-i.MX28-Avoid-redefining-serial_put-cs.patch \
           file://0056-mx28evk-Scan-only-first-128MB-of-DRAM-to-avoid-memor.patch \
"

S = "${WORKDIR}/git"

PACKAGE_ARCH = "${MACHINE_ARCH}"

do_compile_prepend() {
	if [ "${@base_contains('DISTRO_FEATURES', 'ld-is-gold', 'ld-is-gold', '', d)}" = "ld-is-gold" ] ; then
		sed -i 's/$(CROSS_COMPILE)ld/$(CROSS_COMPILE)ld.bfd/g' config.mk
	fi
}
