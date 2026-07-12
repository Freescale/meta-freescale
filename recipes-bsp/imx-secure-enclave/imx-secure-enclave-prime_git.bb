require imx-secure-enclave.inc

SUMMARY += "PRIME"
DESCRIPTION += "PRIME"
HOMEPAGE = "https://github.com/NXP/imx-secure-enclave"

PLAT = "prime"

do_install:append() {
    # Remove common content that is to be installed by imx-secure-enclave
    rm ${D}${datadir}/se/README
}

COMPATIBLE_MACHINE = "(mx943-nxp-bsp|mx952-nxp-bsp)"
