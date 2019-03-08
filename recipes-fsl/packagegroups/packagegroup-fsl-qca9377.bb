# Copyright 2019 NXP

SUMMARY = "Qualcomm Wi-Fi support"

inherit packagegroup

RDEPENDS_${PN} = " \
    kernel-module-qca9377 \
    firmware-qca9377 \
    qca-tools \
"
