# Copyright 2021-2026 NXP
SUMMARY = "NXP i.MX ELE firmware"
DESCRIPTION = "EdgeLock Secure Enclave firmware for i.MX series SoCs"
HOMEPAGE = "https://www.nxp.com/"
SECTION = "base"
LICENSE = "LicenseRef-Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=bc649096ad3928ec06a8713b8d787eac"

inherit fsl-eula-unpack use-imx-security-controller-firmware deploy

SRC_URI = "${FSL_MIRROR}/${BP}-${IMX_SRCREV_ABBREV}.bin;fsl-eula=true"
IMX_SRCREV_ABBREV = "c0b284c"
SRC_URI[sha256sum] = "ff42b838c42448616d3f6dc5a7f0d47c207bb0bae1ae00bea091103ed4012c28"

S = "${UNPACKDIR}/${BP}-${IMX_SRCREV_ABBREV}"

do_compile[noexec] = "1"

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware/imx/ele
    for fw in ${SECO_FIRMWARE_NAME} ${SECOEXT_FIRMWARE_NAME}; do
        install -m 0644 ${S}/$fw ${D}${nonarch_base_libdir}/firmware/imx/ele
    done
}

do_deploy () {
    # Deploy the related firmware to be packaged by imx-boot
    install -m 0644 ${S}/${SECO_FIRMWARE_NAME}  ${DEPLOYDIR}
}
addtask deploy after do_install before do_build

# Package the deployed ELE firmware blob explicitly (filesoverride).
# nooelint: oelint.var.filesoverride
FILES:${PN} = "${nonarch_base_libdir}/firmware"

RREPLACES:${PN} = "firmware-sentinel"
RPROVIDES:${PN} = "firmware-sentinel"

COMPATIBLE_MACHINE = "(mx8ulp-generic-bsp|mx9-generic-bsp)"
