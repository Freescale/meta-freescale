# Copyright 2018 NXP

SUMMARY = "Qualcomm Wi-Fi and Bluetooth firmware"
SECTION = "base"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=6dfb32a488e5fd6bae52fbf6c7ebb086 \
                    file://QUALCOMM_ATHEROS_LICENSE_AGREEMENT.pdf;md5=e629669cf17f6a2a6872e067582986e3 \
"

SRC_URI = " ${FSL_MIRROR}/firmware-qca-${PV}.bin;fsl-eula=true"
SRC_URI[md5sum] = "29e949d1037a464dcb7e71b45a97280e"
SRC_URI[sha256sum] = "d61220c97aa1e5a875234a95934957b02274cb87c2be74e119e4f093907cf68f"
S = "${WORKDIR}/firmware-qca-${PV}"

inherit fsl-eula-unpack
