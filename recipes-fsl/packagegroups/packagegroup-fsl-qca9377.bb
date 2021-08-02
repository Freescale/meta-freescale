# Copyright 2019 NXP

SUMMARY = "Qualcomm Wi-Fi support"

inherit packagegroup

RDEPENDS:${PN} = " \
    kernel-module-qca9377 \
    firmware-qca9377 \
"
COMPATIBLE_HOST = '(aarch64|arm).*-linux'
COMPATIBLE_HOST:libc-musl = 'null'
