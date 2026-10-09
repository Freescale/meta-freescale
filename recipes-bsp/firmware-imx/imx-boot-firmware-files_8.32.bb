# Copyright (C) 2018-2026 NXP
SUMMARY = "Freescale i.MX Firmware files used for boot"
DESCRIPTION = "Prebuilt i.MX firmware blobs (HDMI, SECO and related) used during boot."

require firmware-imx-${PV}.inc

inherit deploy

FIRMWARE_BASE_DIR ?= "/firmware"
FIRMWARE_DIR ?= "${FIRMWARE_BASE_DIR}/${PN}"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

DEPLOY_FOR = ""
DEPLOY_FOR:mx8-generic-bsp = "mx8"
DEPLOY_FOR:mx8m-generic-bsp = "mx8m"
DEPLOY_FOR:mx9-generic-bsp = "mx9"

install_for_mx8() {
    install -d -m 755 ${D}${FIRMWARE_DIR}

    # Cadence HDMI
    install -m 0644 ${S}/firmware/hdmi/cadence/hdmitxfw.bin ${D}${FIRMWARE_DIR}
    install -m 0644 ${S}/firmware/hdmi/cadence/hdmirxfw.bin ${D}${FIRMWARE_DIR}
    install -m 0644 ${S}/firmware/hdmi/cadence/dpfw.bin ${D}${FIRMWARE_DIR}
}
install_for_mx8[doc] = "Install the Cadence HDMI firmware used by the i.MX 8 boot container"

install_for_mx8m() {
    install -d -m 755 ${D}${FIRMWARE_DIR}

    # Synopsys DDR
    for ddr_firmware in ${DDR_FIRMWARE_NAME}; do
        install -m 0644 ${S}/firmware/ddr/synopsys/${ddr_firmware} ${D}${FIRMWARE_DIR}
    done

    # Cadence DP and HDMI
    install -m 0644 ${S}/firmware/hdmi/cadence/signed_dp_imx8m.bin ${D}${FIRMWARE_DIR}
    install -m 0644 ${S}/firmware/hdmi/cadence/signed_hdmi_imx8m.bin ${D}${FIRMWARE_DIR}
}
install_for_mx8m[doc] = "Install the Synopsys DDR and Cadence DP/HDMI firmware used by the i.MX 8M boot container"

install_for_mx9() {
    install -d -m 755 ${D}${FIRMWARE_DIR}

    # Synopsys DDR
    for ddr_firmware in ${DDR_FIRMWARE_NAME}; do
        install -m 0644 ${S}/firmware/ddr/synopsys/${ddr_firmware} ${D}${FIRMWARE_DIR}
    done
}
install_for_mx9[doc] = "Install the Synopsys DDR firmware used by the i.MX 9 boot container"

# do_install calls install_for_$soc through the shell, so BitBake cannot see the
# dependency on the function body by itself.
do_install[vardeps] += "${@' '.join('install_for_' + soc for soc in d.getVar('DEPLOY_FOR').split())}"

do_install () {
    install -d -m 755 ${D}${FIRMWARE_DIR}

    for soc in ${DEPLOY_FOR}; do
        bbnote "Running install for $soc"
        install_for_$soc
    done
}

do_deploy () {
    bbnote "Running deploy for ${DEPLOY_FOR}"
    install -m 0644 -t ${DEPLOYDIR} ${D}${FIRMWARE_DIR}/*
}

addtask deploy after do_install before do_build

# DDR_FIRMWARE_NAME is per board (its DDR type), not per SoC.
PACKAGE_ARCH = "${MACHINE_ARCH}"

FILES:${PN} += "${FIRMWARE_DIR}"
SYSROOT_DIRS += "${FIRMWARE_BASE_DIR}"

COMPATIBLE_MACHINE = "(mx8-generic-bsp|mx9-generic-bsp)"
COMPATIBLE_MACHINE:mx8x-generic-bsp = "(^$)"
