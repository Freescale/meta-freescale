# Copyright 2021-2025 NXP
SUMMARY = "NXP i.MX ELE firmware"
DESCRIPTION = "EdgeLock Secure Enclave firmware for i.MX series SoCs"
SECTION = "base"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=a93b654673e1bc8398ed1f30e0813359"

inherit fsl-eula-unpack use-imx-security-controller-firmware deploy

SRC_URI = "${FSL_MIRROR}/${BP}-${IMX_SRCREV_ABBREV}.bin;fsl-eula=true"
IMX_SRCREV_ABBREV = "89161a8"
SRC_URI[sha256sum] = "2d29f0a4de3662ba15f6a7d9069702d4eaed415d96a17f29d5b127f2c6fdd634"

S = "${UNPACKDIR}/${BP}-${IMX_SRCREV_ABBREV}"

do_compile[noexec] = "1"

do_install() {
    install -d ${D}${nonarch_base_libdir}/firmware/imx/ele
    install -m 0644 ${S}/${SECO_FIRMWARE_NAME} ${D}${nonarch_base_libdir}/firmware/imx/ele
    if [ -e ${S}/${SECOEXT_FIRMWARE_NAME} ]; then
        install -m 0644 ${S}/${SECOEXT_FIRMWARE_NAME} ${D}${nonarch_base_libdir}/firmware/imx/ele
    fi
}

do_deploy () {
    # Deploy the related firmware to be packaged by imx-boot
    install -m 0644 ${S}/${SECO_FIRMWARE_NAME}  ${DEPLOYDIR}
}
addtask deploy after do_install before do_build

PACKAGES += "${PN}-ext"

ALLOW_EMPTY:${PN}-ext = "1"

FILES:${PN} += "${nonarch_base_libdir}/firmware/imx/ele/${SECO_FIRMWARE_NAME}"
FILES:${PN}-ext += "${nonarch_base_libdir}/firmware/imx/ele/${SECOEXT_FIRMWARE_NAME}"

RREPLACES:${PN} = "firmware-sentinel"
RPROVIDES:${PN} = "firmware-sentinel"

COMPATIBLE_MACHINE = "(mx8ulp-generic-bsp|mx9-generic-bsp)"
