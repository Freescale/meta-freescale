require imx-secure-enclave.inc

SUMMARY += "SECO"
DESCRIPTION += "SECO"

PLAT = "seco"

EXTRA_OEMAKE:append:mx8x-nxp-bsp = " COMPATIBLE_MACHINE=mx8dxl-nxp-bsp"

do_install:append:mx9-nxp-bsp() {
    # Remove common content that is to be installed by imx-secure-enclave
    for i in common hsm nvm.h; do
        rm -rf ${D}${includedir}/$i
    done
    rm ${D}${datadir}/se/README
    rm ${D}${bindir}/nvmd_conf_setup.sh
}

COMPATIBLE_MACHINE = "(mx8x-nxp-bsp|mx943-nxp-bsp|mx95-nxp-bsp)"
