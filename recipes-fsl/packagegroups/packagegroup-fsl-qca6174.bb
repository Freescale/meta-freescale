# Copyright 2019 NXP

SUMMARY = "Qualcomm Wi-Fi support"

inherit packagegroup

RDEPENDS_${PN} = " \
    kernel-module-qca6174 \
    firmware-qca6174 \
"
RDEPENDS_${PN}_append_libc-glibc = " qca-tools"
