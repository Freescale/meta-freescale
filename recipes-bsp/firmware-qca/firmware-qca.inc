# Copyright 2018-2019 NXP

SUMMARY = "Qualcomm Wi-Fi and Bluetooth firmware"
SECTION = "base"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=6c12031a11b81db21cdfe0be88cac4b3 \
                    file://QUALCOMM_ATHEROS_LICENSE_AGREEMENT.pdf;md5=e629669cf17f6a2a6872e067582986e3 \
"

SRC_URI = " ${FSL_MIRROR}/firmware-qca-${PV}.bin;fsl-eula=true"
SRC_URI[md5sum] = "3569cb11300758635d2502322c5bf206"
SRC_URI[sha256sum] = "ae75308d59390e926244ad2594248c3c835ee780e94e4a92ac536429cccd286b"
S = "${WORKDIR}/firmware-qca-${PV}"

inherit fsl-eula-unpack
