require imx-secure-enclave.inc

inherit systemd

SYSTEMD_AUTO_ENABLE = "disable"
SYSTEMD_SERVICE:${PN} = "nvm_daemon.service"

PLAT = "ele"

PACKAGES =+ "${PN}-crrm"

RDEPENDS:${PN} = "${@bb.utils.contains('UBOOT_CONFIG', 'crrm', '${PN}-crrm', '', d)}"

FILES:${PN}-crrm = " \
    ${bindir}/ele_crrm_test \
    ${libdir}/lib*crrm${SOLIBS}"

COMPATIBLE_MACHINE = "(mx8ulp-nxp-bsp|mx9-nxp-bsp)"
