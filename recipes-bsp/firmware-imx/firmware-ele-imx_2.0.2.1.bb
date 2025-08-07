# Copyright 2021-2024 NXP
SUMMARY = "NXP i.MX ELE firmware"
DESCRIPTION = "EdgeLock Secure Enclave firmware for i.MX series SoCs"
SECTION = "base"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=a93b654673e1bc8398ed1f30e0813359"

inherit fsl-eula-unpack use-imx-security-controller-firmware deploy

SRC_URI = "${FSL_MIRROR}/${BP}-${IMX_SRCREV_ABBREV}.bin;fsl-eula=true"
IMX_SRCREV_ABBREV = "d30b14a"
SRC_URI[sha256sum] = "293a0e7957ca2a24c6c2edbc87b237141a4b7ef19b35257edce65a026d7cbc0a"

S = "${WORKDIR}/${BP}-${IMX_SRCREV_ABBREV}"

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

FILES:${PN} = "${nonarch_base_libdir}/firmware"

RREPLACES:${PN} = "firmware-sentinel"
RPROVIDES:${PN} = "firmware-sentinel"

COMPATIBLE_MACHINE = "(mx8ulp-generic-bsp|mx9-generic-bsp)"
