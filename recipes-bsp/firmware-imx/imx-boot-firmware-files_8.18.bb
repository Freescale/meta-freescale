# Copyright (C) 2018-2020 NXP
SUMMARY = "Freescale i.MX firmware for imx-boot container"
DESCRIPTION = "Freescale i.MX firmware for imx-boot container"

require firmware-imx-${PV}.inc

inherit deploy nopackages

do_install[noexec] = "1"

CADENCE_FIRMWARE                  = ""
CADENCE_FIRMWARE:mx8-generic-bsp  = "dpfw.bin hdmitxfw.bin hdmirxfw.bin"
CADENCE_FIRMWARE:mx8m-generic-bsp = "signed_dp_imx8m.bin signed_hdmi_imx8m.bin"

DDR_FIRMWARE                  = ""
DDR_FIRMWARE:mx8m-generic-bsp = "${DDR_FIRMWARE_NAME}"
DDR_FIRMWARE:mx9-generic-bsp  = "${DDR_FIRMWARE_NAME}"

do_deploy () {
    for cadence_firmware in ${CADENCE_FIRMWARE}; do
        install -m 0644 ${S}/firmware/hdmi/cadence/$cadence_firmware ${DEPLOYDIR}
    done
    for ddr_firmware in ${DDR_FIRMWARE}; do
        install -m 0644 ${S}/firmware/ddr/synopsys/$ddr_firmware ${DEPLOYDIR}
    done
}

addtask deploy after do_install before do_build

PACKAGE_ARCH = "${MACHINE_SOCARCH}"

COMPATIBLE_MACHINE                  = "(mx8-generic-bsp|mx9-generic-bsp)"
COMPATIBLE_MACHINE:mx8x-generic-bsp = "(^$)"
