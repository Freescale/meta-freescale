# Copyright (C) 2018-2020 NXP
SUMMARY = "Freescale i.MX firmware for 8 family"
DESCRIPTION = "Freescale i.MX firmware for 8 family"

require firmware-imx-${PV}.inc

inherit deploy nopackages

do_install[noexec] = "1"

SOC_FAMILY                 = "INVALID"
SOC_FAMILY:mx8-generic-bsp = "mx8"
SOC_FAMILY:mx9-generic-bsp = "mx9"

deploy_mx8() {
    # Cadence HDMI
    install -m 0644 ${S}/firmware/hdmi/cadence/hdmitxfw.bin ${DEPLOYDIR}
    install -m 0644 ${S}/firmware/hdmi/cadence/hdmirxfw.bin ${DEPLOYDIR}
    install -m 0644 ${S}/firmware/hdmi/cadence/dpfw.bin ${DEPLOYDIR}
}

deploy_mx9() {
    # Synopsys DDR
    for ddr_firmware in ${DDR_FIRMWARE_NAME}; do
        install -m 0644 ${S}/firmware/ddr/synopsys/${ddr_firmware} ${DEPLOYDIR}
    done
}

do_deploy() {
    deploy_${SOC_FAMILY}
}

addtask deploy after do_install before do_build

PACKAGE_ARCH = "${MACHINE_SOCARCH}"

COMPATIBLE_MACHINE      = "(mx8-nxp-bsp|mx9-nxp-bsp)"
COMPATIBLE_MACHINE:mx8m-nxp-bsp = "(^$)"
COMPATIBLE_MACHINE:mx8x-nxp-bsp = "(^$)"
