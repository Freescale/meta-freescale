require kernel-module-qcacld-lea.inc

SUMMARY = "Qualcomm WiFi driver for QCA module 6174"

SRC_URI += "file://0001-MLK-18491-02-qcacld-2.0-fix-the-overflow-of-bounce-b.patch"

EXTRA_OEMAKE += " \
    CONFIG_ROME_IF=pci \
    CONFIG_WLAN_FEATURE_11W=y \
    CONFIG_WLAN_FEATURE_FILS=y \
    CONFIG_WLAN_WAPI_MODE_11AC_DISABLE=y \
    MODNAME=qca6174 \
"

RDEPENDS_${PN} += "firmware-qca6174"
