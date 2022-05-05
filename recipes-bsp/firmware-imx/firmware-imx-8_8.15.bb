# Copyright (C) 2018-2020 NXP
SUMMARY = "Freescale i.MX firmware for 8 family"
DESCRIPTION = "Freescale i.MX firmware for 8 family"

require firmware-imx-${PV}.inc

inherit deploy

do_install[noexec] = "1"

do_deploy() {
    # Cadence HDMI
    install -m 0644 ${S}/firmware/hdmi/cadence/hdmitxfw.bin ${DEPLOYDIR}
    install -m 0644 ${S}/firmware/hdmi/cadence/hdmirxfw.bin ${DEPLOYDIR}
    install -m 0644 ${S}/firmware/hdmi/cadence/dpfw.bin ${DEPLOYDIR}
}
addtask deploy after do_install before do_build

PACKAGE_ARCH = "${MACHINE_SOCARCH}"

COMPATIBLE_MACHINE      = "(mx8-nxp-bsp)"
COMPATIBLE_MACHINE:mx8m-nxp-bsp = "(^$)"
COMPATIBLE_MACHINE:mx8x-nxp-bsp = "(^$)"
