# Copyright 2021-2026 NXP
SUMMARY = "NXP i.MX ELE firmware"
DESCRIPTION = "EdgeLock Secure Enclave firmware for i.MX series SoCs"
HOMEPAGE = "https://www.nxp.com/"
SECTION = "base"
LICENSE = "LicenseRef-Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=ef4f968b9dae8ba406390cd617c2301d"

inherit fsl-eula-unpack use-imx-security-controller-firmware deploy

SRC_URI = "${FSL_MIRROR}/${BP}-${IMX_SRCREV_ABBREV}.bin;fsl-eula=true"
IMX_SRCREV_ABBREV = "0265375"
SRC_URI[sha256sum] = "a24f0e6be783a09281d44b0c2fb26ed719038387441e7ef325b9d4c514592b1d"

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
