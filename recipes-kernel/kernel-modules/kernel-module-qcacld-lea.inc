# Copyright 2018 NXP

SUMMARY = "Qualcomm WiFi driver for QCA module 9377 and 6174"
LICENSE = "BSD & GPLv2"
LIC_FILES_CHKSUM = "file://CORE/HDD/src/wlan_hdd_main.c;beginline=1;endline=20;md5=ec8d62116b13db773825ebf7cf91be1d;"

FILESEXTRAPATHS_prepend := "${THISDIR}/kernel-module-qcacld-lea:"
SRC_URI = " \
    git://source.codeaurora.org/external/wlan/qcacld-2.0;protocol=https;branch=CNSS.LEA.NRT_2.0; \
    file://0001-CORE-QCA9377-CLD-LEA-2.0-porting-to-support-Android-.patch \
    file://0002-Kbuild-Eliminate-errors-by-tool-chain-and-disable-de.patch \
    file://0003-dbglog_host-add-timeout-for-dbglog_block-fwlog-read.patch \
    file://0004-CORE-remove-the-debug-message-in-default.patch \
    file://0005-CORE-cannot-call-spin_lock_bh-in-irq-context.patch \
    file://0006-Kbuild-disable-QCA_CONFIG_SMP.patch \
    file://0007-LEA.NRT_2.0-fix-the-build-error.patch \
    file://0008-CORE-add-pcie-multi_if_name-support.patch \
    file://0009-CORE-add-timeout-when-BMI-request-response-transacti.patch \
    file://0010-CORE-HIF-PCIe-only-support-one-instance.patch \
    file://0011-CORE-BMI-RF-align-the-utf-firmware-bin-name.patch \
    file://0012-CORE-revert-two-patches-to-support-QCA-SMP.patch \
    file://0013-CORE-services-os-fix-issue-to-avoid-spin_lock_bh-in-.patch \
    file://0014-CLD-CORE-add-CONFIG_HDD_WLAN_WAIT_TIME-support-for-u.patch \
    file://0015-CORE-HIF-enable-pcie-MSI-feature.patch \
    file://0016-MLK-18490-CORE-HDD-add-ssid-len-check.patch \
    file://0017-MLK-18491-qcacld-2.0-avoid-overflow-of-bounce-buffer.patch \
    file://0018-MLK-18491-02-qcacld-2.0-fix-the-overflow-of-bounce-b.patch \
"
# version v4.5.23.1
SRCREV = "af6adc6662dd2e8f5ca25af2d184faef44465b45"
S = "${WORKDIR}/git"

inherit module

EXTRA_OEMAKE_QCA9377 += " \
    CONFIG_CLD_HL_SDIO_CORE=y \
    CONFIG_FEATURE_COEX_PTA_CONFIG_ENABLE=y \
    CONFIG_PER_VDEV_TX_DESC_POOL=1 \
    CONFIG_QCA_LL_TX_FLOW_CT=1 \
    CONFIG_QCA_SUPPORT_TXRX_DRIVER_TCP_DEL_ACK=y \
    CONFIG_WLAN_FEATURE_FILS=y \
    CONFIG_WLAN_WAPI_MODE_11AC_DISABLE=y \
    MODNAME=qca9377 \
    SAP_AUTH_OFFLOAD=1 \
"
EXTRA_OEMAKE_QCA6174 = " \
    CONFIG_ROME_IF=pci \
    CONFIG_WLAN_FEATURE_11W=y \
    CONFIG_WLAN_FEATURE_FILS=y \
    CONFIG_WLAN_WAPI_MODE_11AC_DISABLE=y \
    MODNAME=qca6174 \
"
EXTRA_OEMAKE += " \
    CONFIG_CFG80211_INTERNAL_REGDB=y \
    CONFIG_HDD_WLAN_WAIT_TIME=10000 \
    CONFIG_LINUX_QCMBR=y \
    CONFIG_NON_QC_PLATFORM=y \
    CONFIG_PMF_SUPPORT=y \
    TARGET_BUILD_VARIANT=user \
"
