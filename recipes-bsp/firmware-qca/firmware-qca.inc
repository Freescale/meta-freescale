# Copyright 2018 NXP

SUMMARY = "Qualcomm Wi-Fi and Bluetooth firmware"
SECTION = "base"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=ab61cab9599935bfe9f700405ef00f28 \
                    file://QUALCOMM_ATHEROS_LICENSE_AGREEMENT.pdf;md5=e629669cf17f6a2a6872e067582986e3 \
"

SRC_URI = " ${FSL_MIRROR}/firmware-qca-${PV}.bin;fsl-eula=true"
SRC_URI[md5sum] = "d2b7766a0919c2084ba7c911b7ddc1d0"
SRC_URI[sha256sum] = "001eaaf7008d1354daeb7dbfbc86de1269cc4d6e5b49fa7f811eb82035602a42"
S = "${WORKDIR}/firmware-qca-${PV}"

inherit fsl-eula-unpack
