# Copyright (C) 2018-2020 NXP
SUMMARY = "Freescale i.MX firmware for 8M and 8M Mini family"
DESCRIPTION = "Freescale i.MX firmware for 8M and 8M Mini family"

require firmware-imx-${PV}.inc

inherit deploy

do_install[noexec] = "1"

do_deploy() {
    # Synopsys DDR
    for ddr_firmware in ${DDR_FIRMWARE_NAME}; do
        install -m 0644 ${S}/firmware/ddr/synopsys/${ddr_firmware} ${DEPLOYDIR}
    done
    # Cadence DP and HDMI
    install -m 0644 ${S}/firmware/hdmi/cadence/signed_dp_imx8m.bin ${DEPLOYDIR}
    install -m 0644 ${S}/firmware/hdmi/cadence/signed_hdmi_imx8m.bin ${DEPLOYDIR}
}
addtask deploy after do_install before do_build

PACKAGE_ARCH = "${MACHINE_SOCARCH}"

COMPATIBLE_MACHINE = "(mx8m-generic-bsp)"
