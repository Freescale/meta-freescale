require imx-secure-enclave.inc

DEPENDS:append:mx95-nxp-bsp = " imx-secure-enclave-seco"

inherit systemd

SYSTEMD_AUTO_ENABLE = "disable"
SYSTEMD_SERVICE:${PN} = "nvm_daemon.service"

PLAT = "ele"

COMPATIBLE_MACHINE = "(mx8ulp-nxp-bsp|mx9-nxp-bsp)"
