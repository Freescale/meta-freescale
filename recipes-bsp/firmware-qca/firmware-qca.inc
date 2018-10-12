# Copyright 2018 NXP

SUMMARY = "Qualcomm Wi-Fi and Bluetooth firmware"
SECTION = "base"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=5ab1a30d0cd181e3408077727ea5a2db \
                    file://QUALCOMM_ATHEROS_LICENSE_AGREEMENT.pdf;md5=e629669cf17f6a2a6872e067582986e3 \
"

SRC_URI = " ${FSL_MIRROR}/firmware-qca-${PV}.bin;fsl-eula=true"
SRC_URI[md5sum] = "87643bac74f09c474a4cbc8299921733"
SRC_URI[sha256sum] = "a5ff78b288ccd60bb22d88af5d057ed97e2e39a0f9ac2c40682f4a57850ac4a6"
S = "${WORKDIR}/firmware-qca-${PV}"

inherit fsl-eula-unpack
