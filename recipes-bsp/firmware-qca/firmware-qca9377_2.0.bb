# Copyright 2018 NXP

require firmware-qca.inc

SUMMARY = "Qualcomm Wi-Fi and Bluetooth firmware"
DESCRIPTION = "Qualcomm Wi-Fi and Bluetooth firmware for modules such as QCA9377-3"
SECTION = "base"
LICENSE = "Proprietary"

inherit allarch

do_install () {
    # Install firmware.conf for QCA modules
    install -d ${D}${sysconfdir}/bluetooth
    install -m 644 ${S}/1PJ_QCA9377-3_LEA_2.0/etc/bluetooth/firmware.conf ${D}${sysconfdir}/bluetooth

    # Install firmware files
    install -d ${D}${base_libdir}
    cp -r ${S}/1PJ_QCA9377-3_LEA_2.0/lib/firmware ${D}${base_libdir}
}

FILES_${PN} = " \
    ${sysconfdir}/bluetooth/firmware.conf \
    ${base_libdir}/firmware/qca \
    ${base_libdir}/firmware/qca9377 \
    ${base_libdir}/firmware/wlan \
"
