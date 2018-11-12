# Copyright 2018 NXP
SUMMARY = "Freescale i.MX firmware for 8X family"
DESCRIPTION = "Freescale i.MX firmware such as for the VPU"

require firmware-imx-${PV}.inc

inherit deploy

do_deploy() {
    # SECO
    install -m 0644 ${S}/firmware/seco/mx8qx-ahab-container.img ${DEPLOYDIR}
}
addtask after do_install deploy before do_build

PACKAGE_ARCH = "${MACHINE_SOCARCH}"

COMPATIBLE_MACHINE = "(mx8x)"
