# Copyright 2018 NXP
SUMMARY = "Freescale i.MX firmware for 8M and 8M Mini family"
DESCRIPTION = "Freescale i.MX firmware such as for the VPU"

require firmware-imx-${PV}.inc

inherit deploy

do_deploy() {
    # Synopsys DDR
    for ddr_firmware in ${DDR_FIRMWARE_NAME}; do
        install -m 0644 ${S}/firmware/ddr/synopsys/${ddr_firmware} ${DEPLOYDIR}
    done
    # Cadence HDMI
    install -m 0644 ${S}/firmware/hdmi/cadence/signed_dp_imx8m.bin ${DEPLOYDIR}
    install -m 0644 ${S}/firmware/hdmi/cadence/signed_hdmi_imx8m.bin ${DEPLOYDIR}
}
addtask deploy after do_install before do_build

PACKAGE_ARCH = "${MACHINE_SOCARCH}"

COMPATIBLE_MACHINE = "(mx8m)"
