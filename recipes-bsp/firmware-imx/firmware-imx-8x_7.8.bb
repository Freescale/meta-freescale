# Copyright 2018 NXP
SUMMARY = "Freescale i.MX firmware for 8X family"
DESCRIPTION = "Freescale i.MX firmware for 8X family"

require firmware-imx-${PV}.inc

inherit deploy

do_install[noexec] = "1"

do_deploy() {
    # SECO
    install -m 0644 ${S}/firmware/seco/mx8qx-ahab-container.img ${DEPLOYDIR}
}

addtask deploy after do_install before do_build

PACKAGE_ARCH = "${MACHINE_SOCARCH}"

COMPATIBLE_MACHINE = "(mx8x)"
