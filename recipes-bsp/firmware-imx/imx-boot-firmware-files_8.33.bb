# Copyright (C) 2018-2026 NXP
SUMMARY = "Freescale i.MX Firmware files used for boot"
DESCRIPTION = "Prebuilt i.MX firmware blobs (HDMI, SECO and related) used during boot."

require firmware-imx-${PV}.inc

inherit deploy

do_configure[noexec] = "1"
do_compile[noexec] = "1"

INSTALL_FOR = ""
INSTALL_FOR:mx8-generic-bsp = "mx8"
INSTALL_FOR:mx8m-generic-bsp = "mx8m"
INSTALL_FOR:mx9-generic-bsp = "mx9"

install_for_mx8() {
    # Cadence HDMI
    install -m 0644 ${S}/firmware/hdmi/cadence/hdmitxfw.bin ${D}/firmware
    install -m 0644 ${S}/firmware/hdmi/cadence/hdmirxfw.bin ${D}/firmware
    install -m 0644 ${S}/firmware/hdmi/cadence/dpfw.bin ${D}/firmware
}
install_for_mx8[doc] = "Install the Cadence HDMI firmware used by the i.MX 8 boot container"

install_for_mx8m() {
    # Synopsys DDR
    for ddr_firmware in ${DDR_FIRMWARE_NAME}; do
        install -m 0644 ${S}/firmware/ddr/synopsys/${ddr_firmware} ${D}/firmware
    done

    # Cadence DP and HDMI
    install -m 0644 ${S}/firmware/hdmi/cadence/signed_dp_imx8m.bin ${D}/firmware
    install -m 0644 ${S}/firmware/hdmi/cadence/signed_hdmi_imx8m.bin ${D}/firmware
}
install_for_mx8m[doc] = "Install the Synopsys DDR and Cadence DP/HDMI firmware used by the i.MX 8M boot container"

install_for_mx9() {
    # Synopsys DDR
    for ddr_firmware in ${DDR_FIRMWARE_NAME}; do
        install -m 0644 ${S}/firmware/ddr/synopsys/${ddr_firmware} ${D}/firmware
    done
}
install_for_mx9[doc] = "Install the Synopsys DDR firmware used by the i.MX 9 boot container"

# do_install calls install_for_$soc through the shell, so BitBake cannot see the
# dependency on the function body by itself.
do_install[vardeps] += "${@' '.join('install_for_' + soc for soc in d.getVar('INSTALL_FOR').split())}"

do_install() {
    install -d ${D}/firmware
    for soc in ${INSTALL_FOR}; do
        install_for_$soc
    done
}

# The deploy directory copy is kept for the users that read the files from
# DEPLOY_DIR_IMAGE, for example IMAGE_BOOT_FILES in imx8qm-mek.conf.
do_deploy() {
    install -m 0644 -t ${DEPLOYDIR} ${D}/firmware/*
}
addtask deploy after do_install before do_build

# DDR_FIRMWARE_NAME is per board (its DDR type), not per SoC.
PACKAGE_ARCH = "${MACHINE_ARCH}"

FILES:${PN} += "/firmware"
SYSROOT_DIRS += "/firmware"

COMPATIBLE_MACHINE = "(mx8-generic-bsp|mx9-generic-bsp)"
COMPATIBLE_MACHINE:mx8x-generic-bsp = "(^$)"
