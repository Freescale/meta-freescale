# Copyright (C) 2018-2026 NXP
SUMMARY = "Freescale i.MX Firmware files used for boot"
DESCRIPTION = "Prebuilt i.MX firmware blobs (HDMI, SECO and related) used during boot."

require firmware-imx-${PV}.inc

inherit deploy nopackages

do_install[noexec] = "1"

DEPLOY_FOR = ""
DEPLOY_FOR:mx8-generic-bsp = "mx8"
DEPLOY_FOR:mx8m-generic-bsp = "mx8m"
DEPLOY_FOR:mx9-generic-bsp = "mx9"

deploy_for_mx8() {
    # Cadence HDMI
    install -m 0644 ${S}/firmware/hdmi/cadence/hdmitxfw.bin ${DEPLOYDIR}
    install -m 0644 ${S}/firmware/hdmi/cadence/hdmirxfw.bin ${DEPLOYDIR}
    install -m 0644 ${S}/firmware/hdmi/cadence/dpfw.bin ${DEPLOYDIR}
}
deploy_for_mx8[doc] = "Deploy the Cadence HDMI firmware blobs for i.MX8"

deploy_for_mx8m() {
    # Synopsys DDR
    for ddr_firmware in ${DDR_FIRMWARE_NAME}; do
        install -m 0644 ${S}/firmware/ddr/synopsys/${ddr_firmware} ${DEPLOYDIR}
    done

    # Cadence DP and HDMI
    install -m 0644 ${S}/firmware/hdmi/cadence/signed_dp_imx8m.bin ${DEPLOYDIR}
    install -m 0644 ${S}/firmware/hdmi/cadence/signed_hdmi_imx8m.bin ${DEPLOYDIR}
}
deploy_for_mx8m[doc] = "Deploy the Synopsys DDR and Cadence DP/HDMI firmware blobs for i.MX8M"

deploy_for_mx9() {
    # Synopsys DDR
    for ddr_firmware in ${DDR_FIRMWARE_NAME}; do
        install -m 0644 ${S}/firmware/ddr/synopsys/${ddr_firmware} ${DEPLOYDIR}
    done
}
deploy_for_mx9[doc] = "Deploy the Synopsys DDR firmware blobs for i.MX9"

# injects the deploy_for_* helpers as do_deploy vardeps
# nooelint: oelint.task.noanonpython
python () {
    # Manually add the required functions as dependencies otherwise they won't be included in the
    # final run script.
    deploy_for = d.getVar('DEPLOY_FOR', True).split()
    for soc in deploy_for:
        d.appendVarFlag('do_deploy', 'vardeps', ' deploy_for_%s' % soc)
}

do_deploy () {
    for soc in ${DEPLOY_FOR}; do
        bbnote "Running deploy for $soc"
        deploy_for_$soc
    done
}

addtask deploy after do_install before do_build

PACKAGE_ARCH = "${MACHINE_SOCARCH}"

COMPATIBLE_MACHINE = "(mx8-generic-bsp|mx9-generic-bsp)"
COMPATIBLE_MACHINE:mx8x-generic-bsp = "(^$)"
